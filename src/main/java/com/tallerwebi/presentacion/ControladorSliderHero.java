package com.tallerwebi.presentacion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.ServicioSliderHero;
import com.tallerwebi.dominio.SliderHero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ControladorSliderHero {

    private final ServicioSliderHero servicioSliderHero;

    @Autowired
    public ControladorSliderHero(ServicioSliderHero servicioSliderHero) {
        this.servicioSliderHero = servicioSliderHero;
    }

    @PostMapping("/select")
    public void selectSliderHero(@RequestBody String imageNameJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(imageNameJson);
        String imageName = jsonNode.get("imageName").asText();
        servicioSliderHero.save(new SliderHero(imageName));
    }

    @DeleteMapping("/remove")
    public void removeSliderHero(@RequestBody String imageName) {
        servicioSliderHero.remove(new SliderHero(imageName));
    }

    @GetMapping("/all")
    public List<SliderHero> getAllSliderHeroes() {
        return servicioSliderHero.getAllSliderHeroes();
    }
}
