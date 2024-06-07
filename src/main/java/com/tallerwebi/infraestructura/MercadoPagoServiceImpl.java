package com.tallerwebi.infraestructura;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.Carrito;
import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.MercadoPagoService;
import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class MercadoPagoServiceImpl implements MercadoPagoService {
    public MercadoPagoServiceImpl() {
        MercadoPagoConfig.setAccessToken("APP_USR-7544106560468957-060313-b384a8aa74089431a6bfe64a7417d696-1811981688");
    }

    public Preference createPreference(Usuario usuario, Carrito carrito) {
        Set<Libro> compraLibros = carrito.getLibros();
        List<PreferenceItemRequest> items = new ArrayList<>();

        for (Libro libro : compraLibros) {
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id(libro.getId().toString())
                    .title(libro.getTitulo())
                    .currencyId("ARS")
                    .pictureUrl(libro.getRuta())
                    .description(libro.getDescripcion())
                    .categoryId("books")
                    .quantity(1)
                    .unitPrice(BigDecimal.valueOf(libro.getPrecio()))
                    .build();
            items.add(itemRequest);
        }

        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .name(usuario.getNombre())
                .surname(usuario.getApellido())
                .email(usuario.getEmail())
                .build();

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:8080/spring/payment/feedback")
                .failure("http://localhost:8080/spring/payment/feedback")
                .pending("http://localhost:8080/spring/payment/feedback")
                .build();

        PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder()
                .excludedPaymentMethods(new ArrayList<>())
                .excludedPaymentTypes(new ArrayList<>())
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .payer(payer)
                .backUrls(backUrls)
                .autoReturn("all")
                .paymentMethods(paymentMethods)
                .statementDescriptor("Taller Web I")
                .externalReference("Reference_1234")
                .expires(true)
                .build();

        try {
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);
            System.out.println("Preference created with ID: " + preference.getId());
            return preference;
        } catch (MPException | MPApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Payment getPayment(String paymentId) {
        try {
            System.out.println("Fetching payment with ID: " + paymentId);
            PaymentClient paymentClient = new PaymentClient();
            Payment payment = paymentClient.get(Long.valueOf(paymentId));
            System.out.println("Payment retrieved: " + (payment != null ? payment.toString() : "null"));
            return payment;
        } catch (MPApiException e) {
            System.out.println("MPApiException occurred while fetching payment: " + e.getApiResponse().getContent());
            e.printStackTrace();
            return null;
        } catch (MPException e) {
            System.out.println("MPException occurred while fetching payment: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}