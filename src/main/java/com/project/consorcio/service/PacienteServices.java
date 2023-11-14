package com.project.consorcio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.consorcio.dao.PacienteRepository;
import com.project.consorcio.entity.Paciente;

@Service
public class PacienteServices {

	@Autowired
	private PacienteRepository repo;
	
	
	public List<Paciente> listarTodos(){
		return repo.findAll();
	}
	
	public void Registrar(Paciente paciente) {
		 repo.save(paciente);
	}
	
	public void Actualizar(Paciente paciente){
		repo.save(paciente);
	}
	
	public void Eliminar (int codigo) {
		repo.deleteById(codigo);
	}
	
	public Paciente BuscarporId(int codigo) {
		return repo.findById(codigo).orElse(null);
	}
	
}
