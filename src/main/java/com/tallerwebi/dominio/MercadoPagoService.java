package com.tallerwebi.dominio;


import com.mercadopago.resources.preference.Preference;

public interface MercadoPagoService {
    Preference createPreference(Usuario usuario, Double total);

    Preference createPreferencePlan(Usuario usuario, Double precio);
}
