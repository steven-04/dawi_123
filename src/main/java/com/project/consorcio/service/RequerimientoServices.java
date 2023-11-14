package com.project.consorcio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.consorcio.dao.BienRequerimientoRepository;
import com.project.consorcio.dao.RequerimientoRepository;
import com.project.consorcio.entity.BienRequerimiento;
import com.project.consorcio.entity.BienRequerimientoPK;
import com.project.consorcio.entity.Requerimiento;

@Service
public class RequerimientoServices {
	@Autowired
	private RequerimientoRepository repoReque;
	
	@Autowired
	private BienRequerimientoRepository repoDeta;
	
	@Transactional
	public void registrarRequerimiento(Requerimiento obj) {
		try {
			//grabar Requerimiento
			repoReque.save(obj);
			//bucle
			for(BienRequerimiento det:obj.getListaDetalleRequerimiento()) {
				//crear objeto de la clase 	BienRequerimientoPK
				BienRequerimientoPK id=new BienRequerimientoPK();
				//setear
				id.setCod_reque(obj.getCodigo());
				id.setCod_bien(det.getBien().getCodigo());
				//setear PK
				det.setPk(id);
				//grabar detalle Requerimiento
				repoDeta.save(det);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}












