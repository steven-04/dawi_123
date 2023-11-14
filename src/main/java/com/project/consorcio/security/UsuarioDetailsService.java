package com.project.consorcio.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.consorcio.entity.Usuario;
import com.project.consorcio.service.UsuarioServices;

@Service
public class UsuarioDetailsService implements UserDetailsService{
	//atributo de la clase UsuarioServices
	@Autowired
	private UsuarioServices servicioUsu;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails bean=null;
		//invocar al método sesionUsuario
		Usuario u=servicioUsu.sesionUsuario(username);
		//rol del usuario
		Set<GrantedAuthority> rol= new HashSet<GrantedAuthority>();
		//adicionar rol del usario
		rol.add(new SimpleGrantedAuthority(u.getRol().getDescripcion()));
		//crear objeto bean y enviar los dos del usuario "u"
		bean=new User(u.getLogin(), u.getClave(), rol);
		return bean;
	}

}




