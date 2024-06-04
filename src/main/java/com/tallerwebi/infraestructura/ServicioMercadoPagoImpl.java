package com.tallerwebi.infraestructura;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.Carrito;
import com.tallerwebi.dominio.Libro;
import com.tallerwebi.dominio.ServicioMercadoPago;
import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioMercadoPagoImpl implements ServicioMercadoPago {


    public ServicioMercadoPagoImpl() {
        MercadoPagoConfig.setAccessToken("APP_USR-7544106560468957-060313-b384a8aa74089431a6bfe64a7417d696-1811981688");
    }

    public Preference createPreference(Usuario usuario, Carrito carrito) {

        List<Libro> compraLibros = carrito.getLibros();
        List<PreferenceItemRequest> items = new ArrayList<>();

        for(Libro libro : compraLibros) {

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

        // Crea el pagador
        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .name(usuario.getNombre())
                .surname(usuario.getApellido())
                .email(usuario.getEmail())
                .build();


        // Crea las URLs de retorno
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("https://www.instagram.com/")
                .failure("https://www.youtube.com/")
                .pending("https://www.facebook.com/")
                .build();

        // Configura los métodos de pago permitidos y excluidos
        PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder()
                .excludedPaymentMethods(new ArrayList<>())
                .excludedPaymentTypes(new ArrayList<>())
                .build();

        // Crea la preferencia de pago
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .payer(payer)
                .backUrls(backUrls)
                .autoReturn("approved")
                .paymentMethods(paymentMethods)
                .notificationUrl("https://www.your-site.com/ipn")
                .statementDescriptor("Taller Web I")
                .externalReference("Reference_1234")
                .expires(true)
                .build();

        try {
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);
            return preference;
        } catch (MPException | MPApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}
