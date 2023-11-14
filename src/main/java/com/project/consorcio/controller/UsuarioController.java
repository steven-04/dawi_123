package com.project.consorcio.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.project.consorcio.entity.Enlace;
import com.project.consorcio.entity.Usuario;
import com.project.consorcio.security.UsuarioDetailsService;
import com.project.consorcio.service.UsuarioServices;

@Controller
@RequestMapping("/sesion")
@SessionAttributes({"ENLACES","CODIGOUSUARIO","DATOSUSUARIO","ROLUSUARIO"})
public class UsuarioController {
	@Autowired
	private UsuarioServices servicioUsu;
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	@RequestMapping("/intranet")
	public String intranet(Authentication auth,Model model) {
		String nomRol=auth.getAuthorities().stream()
			      .map(GrantedAuthority::getAuthority)
			      .collect(Collectors.joining(","));
		List<Enlace> enlaces=servicioUsu.enlacesDelUsuario(nomRol);
		//usuario actual
		Usuario usu=servicioUsu.sesionUsuario(auth.getName());
		//atributos de tipo sesión
		model.addAttribute("ENLACES",enlaces);
		model.addAttribute("CODIGOUSUARIO",usu.getCodigo());
		model.addAttribute("DATOSUSUARIO",usu.getApellido()+" "+usu.getNombre());
		model.addAttribute("ROLUSUARIO",nomRol);
		return "intranet";
	}
}



