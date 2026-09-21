package com.example.atenciones_medicas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.atenciones_medicas.model.Atencion;

@Repository
public interface AtencionRepository
        extends JpaRepository<Atencion, Integer> {

    List<Atencion> findByPacienteId(Integer pacienteId);
}