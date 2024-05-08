package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.DatosRegistro;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.NoCoincideContrasenia;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/*
@Controller
public class ControladorRegistro {

    @Autowired
    private ServicioLogin servicioLogin;

    @PostMapping(path = "/registrarme")
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario, @ModelAttribute("datosRegistro") DatosRegistro datosRegistro) {
        ModelMap model = new ModelMap();
        try{
            servicioLogin.validarContrasenia(datosRegistro);
            servicioLogin.registrar(usuario);
        } catch (UsuarioExistente e){
            model.put("error", "El usuario ya existe");
            return new ModelAndView("nuevo-usuario", model);
        } catch (NoCoincideContrasenia e){
            model.put("error", "Las contrasenias no coinciden");
            return new ModelAndView("nuevo-usuario", model);
        }catch (Exception e){
            model.put("error", "Error al registrar el nuevo usuario");
        }
        return new ModelAndView("redirect:/login");
    }
}*/
