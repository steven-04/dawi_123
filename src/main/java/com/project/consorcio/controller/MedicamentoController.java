package com.project.consorcio.controller;

import java.io.File;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.consorcio.entity.Medicamento;
import com.project.consorcio.entity.TipoMedicamento;
import com.project.consorcio.service.MedicamentoServices;
import com.project.consorcio.service.TipoMedicamentoServices;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
//ruta o direccion URL para acceder a la clase
@RequestMapping("/medicamento")
public class MedicamentoController {
	@Autowired
	private MedicamentoServices servicioMed;
	
	@Autowired
	private TipoMedicamentoServices servicioTipo;
	
	
	//ruta o direccion URL para invocar al método lista
	@RequestMapping("/lista")
	public String lista(Model model) {
		//la interfaz Model permite guardar un atributo que luego sera enviada a una vista
		model.addAttribute("medicamentos",servicioMed.listarTodos());
		model.addAttribute("tipos",servicioTipo.listarTodos());
		
		return "medicamento";
	}
	
	//ruta o direccion URL para invocar al método grabar
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("codigo") Integer codi,
						 @RequestParam("nombre") String nom,
						 @RequestParam("descripcion") String des,
						 @RequestParam("stock") int sto,
						 @RequestParam("precio") double pre,
						 @RequestParam("fecha") String fec,
						 @RequestParam("tipo") int codTipo,
						 RedirectAttributes redirect) {		
		try {
			//crear objeto de la entidad Medicamento
			Medicamento med=new Medicamento();
			//setear atributos del objeto "med" usando los parámetros
			med.setNombre(nom);
			med.setDescripcion(des);
			med.setStock(sto);
			med.setPrecio(pre);
			med.setFecha(LocalDate.parse(fec));
			//crear objeto de le entidad TipoMedicamento
			TipoMedicamento tp=new TipoMedicamento();
			//setear atributo codigo
			tp.setCodigo(codTipo);
			//enviar objeto "tp" al objeto "med"
			med.setTipo(tp);
			//validar codi
			if(codi==0) {
				//invocar al método registrar
				servicioMed.registrar(med);
				//mensaje +
				redirect.addFlashAttribute("MENSAJE","Medicamento registrado");
			}
			else {
				//setear atributo codigo
				med.setCodigo(codi);
				servicioMed.actualizar(med);
				//mensaje +
				redirect.addFlashAttribute("MENSAJE","Medicamento actualizado");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/medicamento/lista";
	}
	
	
	//crear dirección URL para buscar Medicamento por código
	@RequestMapping("/consultaPorID")
	@ResponseBody
	public Medicamento consultaPorID(@RequestParam("codigo") Integer cod){
		return servicioMed.buscarPorID(cod);
	}
	
	@RequestMapping("/eliminarPorID")
	public String eliminar(@RequestParam("codigo") Integer cod, RedirectAttributes redirect) {
		 
		servicioMed.eliminar(cod);
		redirect.addFlashAttribute("MENSAJE", "Medicamento eliminado") ;
		 return "redirect:/medicamento/lista";
	}
	
	
	/* @RequestMapping("/reporteBoletaPorNumero")
		public void reporteBoletaPorNumero(HttpServletResponse response,@RequestParam("numero") int num) {
			try {
				//invocar al método listarTodos
				List<MedicamentoHasBoleta> lista=boletaService.findDetalleBoletaPorNumero(num);
				//acceder al reporte "reporteMedicamento.jrxml"
				File file=ResourceUtils.getFile("classpath:reporte_boleta_por_numero.jrxml");
				//crear objeto de la clase JasperReport y manipular el objeto file
				JasperReport jasper=JasperCompileManager.compileReport(file.getAbsolutePath());
				//origen de datos "manipular lista"
				JRBeanCollectionDataSource origen=new JRBeanCollectionDataSource(lista);
				//crear reporte
				JasperPrint jasperPrint=JasperFillManager.fillReport(jasper,null,origen);
				//salida del reporte en formato PDF
				response.setContentType("application/pdf");
				//
				OutputStream salida=response.getOutputStream();
				//exportar a pdf
				JasperExportManager.exportReportToPdfStream(jasperPrint,salida);		
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	*/
	
	@RequestMapping("/reporteMedicamentos")
	public void medicamentos(HttpServletResponse response) {
		try {
			//invocar al método listarTodos
			List<Medicamento> lista=servicioMed.listarTodos();
			//acceder al reporte "reporteMedicamento.jrxml"
			File file=ResourceUtils.getFile("classpath:medicamento.jrxml");
			//crear objeto de la clase JasperReport y manipular el objeto file
			JasperReport jasper=JasperCompileManager.compileReport(file.getAbsolutePath());
			//origen de datos "manipular lista"
			JRBeanCollectionDataSource origen=new JRBeanCollectionDataSource(lista);
			//crear reporte
			JasperPrint jasperPrint=JasperFillManager.fillReport(jasper,null,origen);
			//salida del reporte en formato PDF
			response.setContentType("application/pdf");
			//
			OutputStream salida=response.getOutputStream();
			//exportar a pdf
			JasperExportManager.exportReportToPdfStream(jasperPrint,salida);		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
