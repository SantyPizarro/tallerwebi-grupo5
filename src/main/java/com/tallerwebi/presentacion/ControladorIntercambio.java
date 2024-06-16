package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorIntercambio {

    private PerfilService perfilService;
    private ServicioLibro servicioLibro;
    private IntercambioService intercambioService;
    private UsuarioService usuarioService;

    @Autowired
    public ControladorIntercambio(PerfilService perfilService, ServicioLibro servicioLibro,IntercambioService intercambioService, UsuarioService usuarioService) {
        this.perfilService = perfilService;
        this.servicioLibro = servicioLibro;
        this.intercambioService = intercambioService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/intercambiar-libro")
    public ModelAndView intercambiarLibro(HttpServletRequest request, @RequestParam("usuario") Long idUsuario, @RequestParam("libroADar") Long idLibroADar,@RequestParam("usuarioAmigo") Long idAmigo, @RequestParam("libroPedido") Long idLibroPedido) {
        Usuario usuario = perfilService.buscarUsuarioPorId(idUsuario);
        Usuario amigo = perfilService.buscarUsuarioPorId(idAmigo);
        Libro libroADar = servicioLibro.buscarLibroPorId(idLibroADar);
        Libro libroPedido = servicioLibro.buscarLibroPorId(idLibroPedido);

        if (libroADar != null && libroPedido != null && usuario!= null && amigo != null) {
            intercambioService.enviarOfertaIntercambio(usuario, libroADar, amigo, libroPedido);
        }

        return new ModelAndView("redirect:/perfil");
    }


}
