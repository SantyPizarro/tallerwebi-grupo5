package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioSliderHero {

    private final RepositorioSliderHero repositorioSliderHero;

    public ServicioSliderHero(RepositorioSliderHero repositorioSliderHero) {
        this.repositorioSliderHero = repositorioSliderHero;
    }

    public List<SliderHero> getAllSliderHeroes() {
        return repositorioSliderHero.findAll();
    }

    public void save (SliderHero sliderHero){
        repositorioSliderHero.save(sliderHero);
    }

    public void remove (SliderHero sliderHero){
        repositorioSliderHero.delete(sliderHero);
    }

}

