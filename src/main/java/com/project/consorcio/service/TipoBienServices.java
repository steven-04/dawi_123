package com.project.consorcio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.consorcio.dao.TipoBienRepository;
import com.project.consorcio.entity.TipoBien;

@Service
public class TipoBienServices {
	@Autowired
	private TipoBienRepository repo;
	
	public List<TipoBien> listar(){
		return repo.findAll();
	}
	
	
}
