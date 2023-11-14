package com.project.consorcio.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.consorcio.entity.Requerimiento;

public interface RequerimientoRepository 
	extends JpaRepository<Requerimiento, Integer>{

}
