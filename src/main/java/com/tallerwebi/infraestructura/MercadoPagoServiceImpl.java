package com.tallerwebi.infraestructura;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.MercadoPagoService;
import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MercadoPagoServiceImpl implements MercadoPagoService {
    public MercadoPagoServiceImpl() {
        MercadoPagoConfig.setAccessToken("APP_USR-7544106560468957-060313-b384a8aa74089431a6bfe64a7417d696-1811981688");
    }

    public Preference createPreference(Usuario usuario, Double total) {

        List<PreferenceItemRequest> items = getPreferenceItemRequest(total);

        PreferencePayerRequest payer = getPayer(usuario);


        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:8080/spring/payment/feedback")
                .failure("http://localhost:8080/spring/payment/feedback")
                .pending("http://localhost:8080/spring/payment/feedback")
                .build();

        return getPreference(items, payer, backUrls);
    }

    public Preference createPreferencePlan(Usuario usuario, Double total) {

        List<PreferenceItemRequest> items = getPreferenceItemRequest(total);

        PreferencePayerRequest payer = getPayer(usuario);

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:8080/spring/payment/feedbackPlan")
                .failure("http://localhost:8080/spring/payment/feedbackPlan")
                .pending("http://localhost:8080/spring/payment/feedbackPlan")
                .build();

        return getPreference(items, payer, backUrls);
    }

    private PreferencePayerRequest getPayer(Usuario usuario) {
        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .name(usuario.getNombre())
                .surname(usuario.getApellido())
                .email(usuario.getEmail())
                .build();
        return payer;
    }

    private List<PreferenceItemRequest> getPreferenceItemRequest(Double total) {

        List<PreferenceItemRequest> items = new ArrayList<>();
        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .id("Compra")
                .title("Compra")
                .currencyId("ARS")
                .categoryId("books")
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(total))
                .build();
        items.add(itemRequest);
        return items;
    }

    private Preference getPreference(List<PreferenceItemRequest> items, PreferencePayerRequest payer, PreferenceBackUrlsRequest backUrls) {
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
            return preference;
        } catch (MPException | MPApiException e) {
            e.printStackTrace();
            return null;
        }
    }

}