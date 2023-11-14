package com.project.consorcio.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.consorcio.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

}
