package com.example.atenciones_medicas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.atenciones_medicas.model.Atencion;
import com.example.atenciones_medicas.model.Consulta;
import com.example.atenciones_medicas.model.Paciente;
import com.example.atenciones_medicas.service.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // PACIENTES

    @GetMapping("/pacientes")
    public List<Paciente> obtenerPacientes() {
        return pacienteService.obtenerPacientes();
    }

    @GetMapping("/pacientes/{id}")
    public ResponseEntity<?> obtenerPaciente(@PathVariable int id) {

        Paciente paciente = pacienteService.obtenerPacientePorId(id);

        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Paciente no encontrado"));
        }

        return ResponseEntity.ok(paciente);
    }

    @PostMapping("/pacientes")
    public ResponseEntity<Paciente> crearPaciente(
            @Valid @RequestBody Paciente paciente) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pacienteService.crearPaciente(paciente));
    }

    @PutMapping("/pacientes/{id}")
    public ResponseEntity<?> actualizarPaciente(
            @PathVariable int id,
            @Valid @RequestBody Paciente paciente) {

        Paciente actualizado =
                pacienteService.actualizarPaciente(id, paciente);

        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Paciente no encontrado"));
        }

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/pacientes/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable int id) {

        if (!pacienteService.eliminarPaciente(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Paciente no encontrado"));
        }

        return ResponseEntity.ok(
                Map.of("mensaje", "Paciente eliminado correctamente"));
    }

    // CONSULTAS

    @GetMapping("/consultas")
    public List<Consulta> obtenerConsultas() {
        return pacienteService.obtenerConsultas();
    }

    @GetMapping("/consultas/{id}")
    public ResponseEntity<?> obtenerConsulta(@PathVariable int id) {

        Consulta consulta = pacienteService.obtenerConsultaPorId(id);

        if (consulta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Consulta no encontrada"));
        }

        return ResponseEntity.ok(consulta);
    }

    @GetMapping("/pacientes/{id}/consultas")
    public ResponseEntity<?> consultasPaciente(@PathVariable int id) {

        if (pacienteService.obtenerPacientePorId(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Paciente no encontrado"));
        }

        return ResponseEntity.ok(
                pacienteService.obtenerConsultasPaciente(id));
    }

    @PostMapping("/consultas")
    public ResponseEntity<?> crearConsulta(
            @Valid @RequestBody Consulta consulta) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(pacienteService.crearConsulta(consulta));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/consultas/{id}")
    public ResponseEntity<?> actualizarConsulta(
            @PathVariable int id,
            @Valid @RequestBody Consulta consulta) {

        try {

            Consulta actualizada =
                    pacienteService.actualizarConsulta(id, consulta);

            if (actualizada == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Consulta no encontrada"));
            }

            return ResponseEntity.ok(actualizada);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/consultas/{id}")
    public ResponseEntity<?> eliminarConsulta(@PathVariable int id) {

        if (!pacienteService.eliminarConsulta(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Consulta no encontrada"));
        }

        return ResponseEntity.ok(
                Map.of("mensaje", "Consulta eliminada correctamente"));
    }

    // ATENCIONES

    @GetMapping("/atenciones")
    public List<Atencion> obtenerAtenciones() {
        return pacienteService.obtenerAtenciones();
    }

    @GetMapping("/atenciones/{id}")
    public ResponseEntity<?> obtenerAtencion(@PathVariable int id) {

        Atencion atencion = pacienteService.obtenerAtencionPorId(id);

        if (atencion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Atención no encontrada"));
        }

        return ResponseEntity.ok(atencion);
    }

    @GetMapping("/pacientes/{id}/atenciones")
    public ResponseEntity<?> atencionesPaciente(@PathVariable int id) {

        if (pacienteService.obtenerPacientePorId(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Paciente no encontrado"));
        }

        return ResponseEntity.ok(
                pacienteService.obtenerAtencionesPaciente(id));
    }

    @PostMapping("/atenciones")
    public ResponseEntity<?> crearAtencion(
            @Valid @RequestBody Atencion atencion) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(pacienteService.crearAtencion(atencion));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/atenciones/{id}")
    public ResponseEntity<?> actualizarAtencion(
            @PathVariable int id,
            @Valid @RequestBody Atencion atencion) {

        try {

            Atencion actualizada =
                    pacienteService.actualizarAtencion(id, atencion);

            if (actualizada == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Atención no encontrada"));
            }

            return ResponseEntity.ok(actualizada);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/atenciones/{id}")
    public ResponseEntity<?> eliminarAtencion(@PathVariable int id) {

        if (!pacienteService.eliminarAtencion(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Atención no encontrada"));
        }

        return ResponseEntity.ok(
                Map.of("mensaje", "Atención eliminada correctamente"));
    }
}