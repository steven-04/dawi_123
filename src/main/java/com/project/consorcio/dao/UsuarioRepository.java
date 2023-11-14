package com.project.consorcio.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.consorcio.entity.Enlace;
import com.project.consorcio.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	//método para iniciar sesión
	//select *from tb_usuario where login='anita' and clave='la huerfanita'
	//select u from Usuario u where u.login=?1
	@Query("select u from Usuario u where u.login=?1")
	public Usuario iniciarSesion(String vLogin);
	
	//método para traer los enlaces según el código de rol del usuario
	//select e.idenlace,e.descripcion,e.ruta from tb_enlace e join tb_rol_enlace re on 
	//e.idenlace=re.idenlace where re.idrol=1;
	
	@Query("select e from RolEnlace re join re.enlace e where re.rol.descripcion=?1")
	public List<Enlace> traerEnlacesDelUsuario(String desRol);
	
	
	
	
	
}
