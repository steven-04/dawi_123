package com.project.consorcio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.consorcio.dao.BienRepository;
import com.project.consorcio.entity.Bien;

@Service
public class BienServices {
	@Autowired
	private BienRepository repo;
	
	public List<Bien> findAllBienPorTipo(int codTipo){
		return repo.listarBienPorTipo(codTipo);
	}
	
	
}
