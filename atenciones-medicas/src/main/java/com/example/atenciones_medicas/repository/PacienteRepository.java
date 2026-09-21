package com.example.atenciones_medicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.atenciones_medicas.model.Paciente;

@Repository
public interface PacienteRepository
        extends JpaRepository<Paciente, Integer> {
}