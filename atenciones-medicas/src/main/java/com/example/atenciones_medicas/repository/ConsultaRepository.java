package com.example.atenciones_medicas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.atenciones_medicas.model.Consulta;

@Repository
public interface ConsultaRepository
        extends JpaRepository<Consulta, Integer> {

    List<Consulta> findByPacienteId(Integer pacienteId);
}