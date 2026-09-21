package com.example.atenciones_medicas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.atenciones_medicas.model.Atencion;
import com.example.atenciones_medicas.model.Consulta;
import com.example.atenciones_medicas.model.Paciente;
import com.example.atenciones_medicas.repository.AtencionRepository;
import com.example.atenciones_medicas.repository.ConsultaRepository;
import com.example.atenciones_medicas.repository.PacienteRepository;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;
    private final AtencionRepository atencionRepository;

    public PacienteService(
            PacienteRepository pacienteRepository,
            ConsultaRepository consultaRepository,
            AtencionRepository atencionRepository) {

        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
        this.atencionRepository = atencionRepository;
    }

    // PACIENTES

    public List<Paciente> obtenerPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente obtenerPacientePorId(int id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    public Paciente crearPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente actualizarPaciente(int id, Paciente datos) {

        Paciente paciente = pacienteRepository.findById(id).orElse(null);

        if (paciente == null) {
            return null;
        }

        paciente.setNombre(datos.getNombre());
        paciente.setApellido(datos.getApellido());
        paciente.setRut(datos.getRut());
        paciente.setEdad(datos.getEdad());
        paciente.setTelefono(datos.getTelefono());

        return pacienteRepository.save(paciente);
    }

    public boolean eliminarPaciente(int id) {

        if (!pacienteRepository.existsById(id)) {
            return false;
        }

        pacienteRepository.deleteById(id);
        return true;
    }

    // CONSULTAS

    public List<Consulta> obtenerConsultas() {
        return consultaRepository.findAll();
    }

    public Consulta obtenerConsultaPorId(int id) {
        return consultaRepository.findById(id).orElse(null);
    }

    public List<Consulta> obtenerConsultasPaciente(int id) {
        return consultaRepository.findByPacienteId(id);
    }

    public Consulta crearConsulta(Consulta consulta) {

        if (consulta.getPaciente() == null) {
            throw new RuntimeException("Debe indicar un paciente");
        }

        Paciente paciente = pacienteRepository
                .findById(consulta.getPaciente().getId())
                .orElseThrow(() ->
                        new RuntimeException("Paciente no encontrado"));

        consulta.setPaciente(paciente);

        return consultaRepository.save(consulta);
    }

    public Consulta actualizarConsulta(int id, Consulta datos) {

        Consulta consulta = consultaRepository.findById(id).orElse(null);

        if (consulta == null) {
            return null;
        }

        consulta.setFecha(datos.getFecha());
        consulta.setMotivo(datos.getMotivo());
        consulta.setDiagnostico(datos.getDiagnostico());

        if (datos.getPaciente() != null) {

            Paciente paciente = pacienteRepository
                    .findById(datos.getPaciente().getId())
                    .orElseThrow(() ->
                            new RuntimeException("Paciente no encontrado"));

            consulta.setPaciente(paciente);
        }

        return consultaRepository.save(consulta);
    }

    public boolean eliminarConsulta(int id) {

        if (!consultaRepository.existsById(id)) {
            return false;
        }

        consultaRepository.deleteById(id);
        return true;
    }

    // ATENCIONES

    public List<Atencion> obtenerAtenciones() {
        return atencionRepository.findAll();
    }

    public Atencion obtenerAtencionPorId(int id) {
        return atencionRepository.findById(id).orElse(null);
    }

    public List<Atencion> obtenerAtencionesPaciente(int id) {
        return atencionRepository.findByPacienteId(id);
    }

    public Atencion crearAtencion(Atencion atencion) {

        if (atencion.getPaciente() == null) {
            throw new RuntimeException("Debe indicar un paciente");
        }

        Paciente paciente = pacienteRepository
                .findById(atencion.getPaciente().getId())
                .orElseThrow(() ->
                        new RuntimeException("Paciente no encontrado"));

        atencion.setPaciente(paciente);

        return atencionRepository.save(atencion);
    }

    public Atencion actualizarAtencion(int id, Atencion datos) {

        Atencion atencion = atencionRepository.findById(id).orElse(null);

        if (atencion == null) {
            return null;
        }

        atencion.setFecha(datos.getFecha());
        atencion.setProfesional(datos.getProfesional());
        atencion.setEspecialidad(datos.getEspecialidad());
        atencion.setObservaciones(datos.getObservaciones());

        if (datos.getPaciente() != null) {

            Paciente paciente = pacienteRepository
                    .findById(datos.getPaciente().getId())
                    .orElseThrow(() ->
                            new RuntimeException("Paciente no encontrado"));

            atencion.setPaciente(paciente);
        }

        return atencionRepository.save(atencion);
    }

    public boolean eliminarAtencion(int id) {

        if (!atencionRepository.existsById(id)) {
            return false;
        }

        atencionRepository.deleteById(id);
        return true;
    }
}