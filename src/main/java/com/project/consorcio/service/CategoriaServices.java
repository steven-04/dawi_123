package com.project.consorcio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.consorcio.dao.CategoriaRepository;
import com.project.consorcio.entity.Categoria;

@Service
public class CategoriaServices {

	@Autowired
	private CategoriaRepository repo;
	
	public List<Categoria> listarTodos(){
		return repo.findAll();
	}
}
