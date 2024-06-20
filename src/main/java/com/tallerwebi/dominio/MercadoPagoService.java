package com.tallerwebi.dominio;

import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

public interface MercadoPagoService {
    Preference createPreference(Usuario usuario, Double total);

    Payment getPayment(String paymentId);
}
