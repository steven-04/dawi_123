package com.project.consorcio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.consorcio.entity.Categoria;
import com.project.consorcio.entity.Paciente;
import com.project.consorcio.service.CategoriaServices;
import com.project.consorcio.service.PacienteServices;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

	@Autowired
	private PacienteServices servicioPaciente ;
	
	@Autowired
	private CategoriaServices servicioCategoria;
	
	@RequestMapping("/lista")
	public String lista(Model model) {
		model.addAttribute("pacientes", servicioPaciente.listarTodos());
		model.addAttribute("categorias", servicioCategoria.listarTodos());
		return "paciente";
	}
	
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("codigo") Integer cod,
						 @RequestParam("nombre") String nom,
						 @RequestParam("apellido") String ape,
						 @RequestParam("sexo") String sex,
						 @RequestParam("categoria") int cat,
						 RedirectAttributes redirect) {
		try {
			
			Paciente p=new Paciente();
			p.setCodigo(cod);
			p.setNombre(nom);
			p.setApellido(ape);
			p.setSexo(sex);
			
			Categoria c=new Categoria();
			c.setCodigo(cat);
			p.setCategoria(c);
			
			if(cod == 0) {
				servicioPaciente.Registrar(p);
				redirect.addFlashAttribute("MENSAJE","Paciente registrado exitosamente");

			}else {
				servicioPaciente.Actualizar(p);
				redirect.addFlashAttribute("MENSAJE","Paciente actualizado exitosamente");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "redirect:/paciente/lista";
		
		
	}
	@RequestMapping("/BuscarPorId")
	@ResponseBody
	public Paciente buscarPorId(@RequestParam ("codigo") int cod  ) {
		return servicioPaciente.BuscarporId(cod);
	}
	
	@RequestMapping("/eliminarPorId")
	public String eliminar(@RequestParam ("codigo") int cod ,
			RedirectAttributes redirect) {
		
		servicioPaciente.Eliminar(cod);
		redirect.addFlashAttribute("MENSAJE", "Paciente eliminado exitosamente");
		return "redirect:/paciente/lista";
	}
	
}
