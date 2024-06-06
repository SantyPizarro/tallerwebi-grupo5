package com.tallerwebi.dominio;

import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

public interface ServicioMercadoPago {
    Preference createPreference(Usuario usuario,Carrito carrito);

    Payment getPayment(String paymentId);
}
