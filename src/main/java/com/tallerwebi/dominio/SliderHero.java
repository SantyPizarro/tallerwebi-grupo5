package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.Id;


public class SliderHero {
    private String imageName;

    public SliderHero() {
    }

    public SliderHero(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

}

