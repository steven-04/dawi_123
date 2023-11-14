package com.project.consorcio;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.consorcio.entity.Enlace;
import com.project.consorcio.entity.Usuario;
import com.project.consorcio.service.UsuarioServices;

@SpringBootTest
class SpringClinicaGrupo1ApplicationTests {
	@Autowired
	private UsuarioServices servicio;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Test
	void contextLoads() {
		
		//System.out.println("CLAVE  : "+encoder.encode("123"));
		
		Usuario bean=servicio.sesionUsuario("ana");
		if (bean==null)
			System.out.println("noooooooo");
		else {
			System.out.println(bean.getNombre());
			List<Enlace> data=servicio.enlacesDelUsuario(bean.getRol().getDescripcion());
			data.forEach(e->{
				System.out.println(e.getCodigo()+" "+e.getDescripcion()+" "+e.getRuta());
			});
			
		}
	}

}




