package com.tallerwebi.dominio;

import org.springframework.stereotype.Repository;

import java.util.List;

public interface RepositorioSliderHero {
    List<SliderHero> findAll();
    void save(SliderHero sliderHero);
    void delete(SliderHero sliderHero);
}