package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioSliderHero;
import com.tallerwebi.dominio.SliderHero;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositorioSliderHeroImpl implements RepositorioSliderHero {

    private final List<SliderHero> sliderHeroes = new ArrayList<>();

    @Override
    public List<SliderHero> findAll() {
        return sliderHeroes;
    }


    @Override
    public void save(SliderHero sliderHero) {
        sliderHeroes.add(sliderHero);
    }

    @Override
    public void delete(SliderHero sliderHero) {
        sliderHeroes.removeIf(hero -> hero.getImageName().equals(sliderHero.getImageName()));
    }
}