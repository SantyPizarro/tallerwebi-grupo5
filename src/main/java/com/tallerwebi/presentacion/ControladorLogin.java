package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.NoCoincideContrasenia;
import com.tallerwebi.dominio.excepcion.TokenInvalido;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;


    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping("/login")
    public ModelAndView irALogin() {

        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail());
        String rol = usuarioBuscado.getRol();

        if (rol.equals("user")) {
            if (usuarioBuscado.getEmail().equals(datosLogin.getEmail()) && usuarioBuscado.getPassword().equals(datosLogin.getPassword()) && usuarioBuscado.getEmailVerificado()) {
                HttpSession sesion = request.getSession();
                Carrito carrito = new Carrito();
                sesion.setAttribute("CARRITO", carrito);
                sesion.setAttribute("USUARIO", usuarioBuscado);
                sesion.setAttribute("nombreUsuario", usuarioBuscado.getNombreDeUsuario());

                return new ModelAndView("redirect:/home");
            } else {
                if (!usuarioBuscado.getEmail().equals(datosLogin.getEmail()) && !usuarioBuscado.getPassword().equals(datosLogin.getPassword())) {
                    model.put("error", "Usuario o clave incorrecta");
                } else {
                    model.put("error", "El email no ha sido verificado");
                    return new ModelAndView("codigoDeVerificacion");
                }
            }
            return new ModelAndView("login", model);
        } else {
            if (rol.equals("ADMIN")) {
                if (usuarioBuscado.getEmail().equals(datosLogin.getEmail()) && usuarioBuscado.getPassword().equals(datosLogin.getPassword())) {
                    HttpSession sesion = request.getSession();
                    Carrito carrito = new Carrito();
                    sesion.setAttribute("CARRITO", carrito);
                    sesion.setAttribute("USUARIO", usuarioBuscado);
                    sesion.setAttribute("nombreUsuario", usuarioBuscado.getNombreDeUsuario());
                    return new ModelAndView("redirect:/perfilAdmin");
                }else {
                    model.put("error", "Usuario o clave incorrecta");
                }
            }
        }
        return new ModelAndView("login", model);
    }

    @RequestMapping(path = "/cerrar-sesion")
    public ModelAndView cerrarSesion(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        sesion.removeAttribute("CARRITO");
        sesion.invalidate();
        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("login", modelo);

    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("datosRegistro") DatosRegistro datosRegistro) {
        ModelMap model = new ModelMap();
        try {
            servicioLogin.registrar(datosRegistro);
        } catch (UsuarioExistente e) {
            model.put("error", "El usuario ya existe");
            return new ModelAndView("nuevo-usuario", model);
        } catch (NoCoincideContrasenia e) {
            model.put("error", "Las contraseñas no coinciden");
            return new ModelAndView("nuevo-usuario", model);
        } catch (Exception e) {
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("nuevo-usuario", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario() {
        ModelMap model = new ModelMap();
        model.put("datosRegistro", new DatosRegistro());
        return new ModelAndView("nuevo-usuario", model);
    }

    @RequestMapping(path = "/validar-token", method = RequestMethod.POST)
    public ModelAndView validarToken(@RequestParam("token") String token) {
        ModelMap model = new ModelMap();
        try {
            servicioLogin.validarToken(token);
        } catch (TokenInvalido e) {
            model.put("error", "El token ingresado es inválido");
            return new ModelAndView("codigoDeVerificacion", model);
        } catch (Exception e) {
            model.put("error", "Error al validar el token");
            return new ModelAndView("codigoDeVerificacion", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/login");
    }
}

