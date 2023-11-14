package com.project.consorcio.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.consorcio.dto.Detalle;
import com.project.consorcio.entity.Bien;
import com.project.consorcio.entity.BienRequerimiento;
import com.project.consorcio.entity.Requerimiento;
import com.project.consorcio.entity.Usuario;
import com.project.consorcio.service.BienServices;
import com.project.consorcio.service.RequerimientoServices;
import com.project.consorcio.service.TipoBienServices;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/requerimiento")
public class RequerimientoController {
	@Autowired
	private TipoBienServices servicioTipo;
	@Autowired
	private BienServices servicioBien;
	@Autowired
	private RequerimientoServices servicioReque;
	
	
	@RequestMapping("/lista")
	public String lista(Model model){
		model.addAttribute("tipos",servicioTipo.listar());
		return "requerimiento";
	}
	
	@RequestMapping("/consultaTipo")
	@ResponseBody
	public List<Bien>consultaTipo(@RequestParam("codigo") int cod){
		return servicioBien.findAllBienPorTipo(cod);
	}
	
	@RequestMapping("/adicionar")
	@ResponseBody
	public List<Detalle> adicionar(@RequestParam("codigo") int cod,
						    @RequestParam("nombre") String nom,
						    @RequestParam("cantidad") int can,
						    HttpServletRequest request) {
		//declarar arreglo de la clase Detalle
		List<Detalle> lista=null;
		//validar si existe el atributo de tipo sesión "datos"
		if(request.getSession().getAttribute("datos")==null)//no existe
			//crear el arreglo lista
			lista=new ArrayList<Detalle>();
		else//existe atributo "datos"
			//recuperar el valor del atributo "datos" y enviarlo a lista 
			lista=(List<Detalle>) request.getSession().getAttribute("datos");
		
		//crear objeto de la clase Detalle
		Detalle det=new Detalle();
		//setear atributos 
		det.setCodigo(cod);
		det.setNombre(nom);
		det.setCantidad(can);
		//adicionar "det" en el arreglo "lista"
		lista.add(det);
		//crear atributo datos
		request.getSession().setAttribute("datos",lista);
		return lista;
	}
	
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("nombre") String nom,@RequestParam("fecha") String fec,
						HttpServletRequest request,RedirectAttributes redirect){
		try {
			//crear objeto de la entidad Requerimiento
			Requerimiento bean=new Requerimiento();
			bean.setNumero("RE-0000001");
			bean.setNombre(nom);
			bean.setFecha(LocalDate.parse(fec));
			bean.setEstado("Creado");
			//crear objeto de la clase Usuario
			Usuario usu=new Usuario();
			//obtener atributo CODIGOUSUARIO
			int codUsu=(int) request.getSession().getAttribute("CODIGOUSUARIO");
			usu.setCodigo(codUsu);
			bean.setUsuario(usu);
			//recuperar valores del atributo de tipo sesión "datos"
			List<Detalle> lista=(List<Detalle>) request.getSession().getAttribute("datos");
			//crear arreglo de objetos de la entidad BienRequerimiento
			List<BienRequerimiento> detalles=new ArrayList<BienRequerimiento>();
			//bucle
			for(Detalle de:lista) {
				//crear objeto de la ebtidad BienRequerimiento
				BienRequerimiento br=new BienRequerimiento();
				//crear objeto de la entidad Bien
				Bien b=new Bien();
				b.setCodigo(de.getCodigo());
				//enviar objeto "b" dentro del objeto "br"
				br.setBien(b);
				br.setCantidad(de.getCantidad());
				//adicionar objeto "br" dentro de "detalles"
				detalles.add(br);
			}
			//enviar arreglo "detalles" dentro de "bean"
			bean.setListaDetalleRequerimiento(detalles);
			//
			servicioReque.registrarRequerimiento(bean);
			//
			lista.clear();
			request.getSession().setAttribute("datos",lista);
			redirect.addFlashAttribute("MENSAJE","Requerimiento registrado");
		} catch (Exception e) {
			redirect.addFlashAttribute("MENSAJE","Error en el registro");
			e.printStackTrace();
		}
		return "redirect:/requerimiento/lista";
	}
	
}






