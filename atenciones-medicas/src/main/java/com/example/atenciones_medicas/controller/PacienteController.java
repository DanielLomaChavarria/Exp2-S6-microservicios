package com.example.atenciones_medicas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.atenciones_medicas.model.Atencion;
import com.example.atenciones_medicas.model.Consulta;
import com.example.atenciones_medicas.model.Paciente;
import com.example.atenciones_medicas.service.PacienteService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // =========================================================
    // PACIENTES
    // =========================================================

    @GetMapping("/pacientes")
    public CollectionModel<EntityModel<Paciente>> obtenerPacientes() {

        List<EntityModel<Paciente>> pacientes =
                pacienteService.obtenerPacientes()
                        .stream()
                        .map(paciente -> EntityModel.of(
                                paciente,

                                linkTo(methodOn(PacienteController.class)
                                        .obtenerPaciente(paciente.getId()))
                                        .withSelfRel(),

                                linkTo(methodOn(PacienteController.class)
                                        .consultasPaciente(paciente.getId()))
                                        .withRel("consultas"),

                                linkTo(methodOn(PacienteController.class)
                                        .atencionesPaciente(paciente.getId()))
                                        .withRel("atenciones")
                        ))
                        .toList();

        return CollectionModel.of(
                pacientes,
                linkTo(methodOn(PacienteController.class)
                        .obtenerPacientes())
                        .withSelfRel()
        );
    }

    @GetMapping("/pacientes/{id}")
    public ResponseEntity<?> obtenerPaciente(@PathVariable int id) {

        Paciente paciente =
                pacienteService.obtenerPacientePorId(id);

        if (paciente == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error",
                            "Paciente no encontrado"
                    ));
        }

        EntityModel<Paciente> recurso =
                EntityModel.of(paciente);

        recurso.add(
                linkTo(methodOn(PacienteController.class)
                        .obtenerPaciente(id))
                        .withSelfRel()
        );

        recurso.add(
                linkTo(methodOn(PacienteController.class)
                        .obtenerPacientes())
                        .withRel("pacientes")
        );

        recurso.add(
                linkTo(methodOn(PacienteController.class)
                        .consultasPaciente(id))
                        .withRel("consultas")
        );

        recurso.add(
                linkTo(methodOn(PacienteController.class)
                        .atencionesPaciente(id))
                        .withRel("atenciones")
        );

        return ResponseEntity.ok(recurso);
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
                    .body(Map.of(
                            "error",
                            "Paciente no encontrado"
                    ));
        }

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/pacientes/{id}")
    public ResponseEntity<?> eliminarPaciente(
            @PathVariable int id) {

        if (!pacienteService.eliminarPaciente(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error",
                            "Paciente no encontrado"
                    ));
        }

        return ResponseEntity.ok(
                Map.of(
                        "mensaje",
                        "Paciente eliminado correctamente"
                )
        );
    }

    // =========================================================
    // CONSULTAS
    // =========================================================

    @GetMapping("/consultas")
    public CollectionModel<EntityModel<Consulta>> obtenerConsultas() {

        List<EntityModel<Consulta>> consultas =
                pacienteService.obtenerConsultas()
                        .stream()
                        .map(consulta -> EntityModel.of(
                                consulta,

                                linkTo(methodOn(PacienteController.class)
                                        .obtenerConsulta(consulta.getId()))
                                        .withSelfRel()
                        ))
                        .toList();

        return CollectionModel.of(
                consultas,
                linkTo(methodOn(PacienteController.class)
                        .obtenerConsultas())
                        .withSelfRel()
        );
    }

    @GetMapping("/consultas/{id}")
    public ResponseEntity<?> obtenerConsulta(
            @PathVariable int id) {

        Consulta consulta =
                pacienteService.obtenerConsultaPorId(id);

        if (consulta == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error",
                            "Consulta no encontrada"
                    ));
        }

        EntityModel<Consulta> recurso =
                EntityModel.of(consulta);

        recurso.add(
                linkTo(methodOn(PacienteController.class)
                        .obtenerConsulta(id))
                        .withSelfRel()
        );

        recurso.add(
                linkTo(methodOn(PacienteController.class)
                        .obtenerConsultas())
                        .withRel("consultas")
        );

        if (consulta.getPaciente() != null) {

            recurso.add(
                    linkTo(methodOn(PacienteController.class)
                            .obtenerPaciente(
                                    consulta.getPaciente().getId()))
                            .withRel("paciente")
            );
        }

        return ResponseEntity.ok(recurso);
    }

    @GetMapping("/pacientes/{id}/consultas")
    public ResponseEntity<?> consultasPaciente(
            @PathVariable int id) {

        if (pacienteService.obtenerPacientePorId(id) == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error",
                            "Paciente no encontrado"
                    ));
        }

        List<EntityModel<Consulta>> consultas =
                pacienteService.obtenerConsultasPaciente(id)
                        .stream()
                        .map(consulta -> EntityModel.of(
                                consulta,

                                linkTo(methodOn(PacienteController.class)
                                        .obtenerConsulta(consulta.getId()))
                                        .withSelfRel()
                        ))
                        .toList();

        CollectionModel<EntityModel<Consulta>> recurso =
                CollectionModel.of(consultas);

        recurso.add(
                linkTo(methodOn(PacienteController.class)
                        .consultasPaciente(id))
                        .withSelfRel()
        );

        recurso.add(
                linkTo(methodOn(PacienteController.class)
                        .obtenerPaciente(id))
                        .withRel("paciente")
        );

        return ResponseEntity.ok(recurso);
    }

    @PostMapping("/consultas")
    public ResponseEntity<?> crearConsulta(
            @Valid @RequestBody Consulta consulta) {

        try {

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(
                            pacienteService.crearConsulta(consulta)
                    );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            e.getMessage()
                    ));
        }
    }

    @PutMapping("/consultas/{id}")
    public ResponseEntity<?> actualizarConsulta(
            @PathVariable int id,
            @Valid @RequestBody Consulta consulta) {

        try {

            Consulta actualizada =
                    pacienteService.actualizarConsulta(
                            id,
                            consulta
                    );

            if (actualizada == null) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "error",
                                "Consulta no encontrada"
                        ));
            }

            return ResponseEntity.ok(actualizada);

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            e.getMessage()
                    ));
        }
    }

    @DeleteMapping("/consultas/{id}")
    public ResponseEntity<?> eliminarConsulta(
            @PathVariable int id) {

        if (!pacienteService.eliminarConsulta(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error",
                            "Consulta no encontrada"
                    ));
        }

        return ResponseEntity.ok(
                Map.of(
                        "mensaje",
                        "Consulta eliminada correctamente"
                )
        );
    }

    // =========================================================
    // ATENCIONES
    // =========================================================

    @GetMapping("/atenciones")
    public CollectionModel<EntityModel<Atencion>> obtenerAtenciones() {

        List<EntityModel<Atencion>> atenciones =
                pacienteService.obtenerAtenciones()
                        .stream()
                        .map(atencion -> EntityModel.of(
                                atencion,

                                linkTo(methodOn(PacienteController.class)
                                        .obtenerAtencion(atencion.getId()))
                                        .withSelfRel()
                        ))
                        .toList();

        return CollectionModel.of(
                atenciones,
                linkTo(methodOn(PacienteController.class)
                        .obtenerAtenciones())
                        .withSelfRel()
        );
    }

    @GetMapping("/atenciones/{id}")
    public ResponseEntity<?> obtenerAtencion(
            @PathVariable int id) {

        Atencion atencion =
                pacienteService.obtenerAtencionPorId(id);

        if (atencion == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error",
                            "Atención no encontrada"
                    ));
        }

        EntityModel<Atencion> recurso =
                EntityModel.of(atencion);

        recurso.add(
                linkTo(methodOn(PacienteController.class)
                        .obtenerAtencion(id))
                        .withSelfRel()
        );

        recurso.add(
                linkTo(methodOn(PacienteController.class)
                        .obtenerAtenciones())
                        .withRel("atenciones")
        );

        if (atencion.getPaciente() != null) {

            recurso.add(
                    linkTo(methodOn(PacienteController.class)
                            .obtenerPaciente(
                                    atencion.getPaciente().getId()))
                            .withRel("paciente")
            );
        }

        return ResponseEntity.ok(recurso);
    }

    @GetMapping("/pacientes/{id}/atenciones")
    public ResponseEntity<?> atencionesPaciente(
            @PathVariable int id) {

        if (pacienteService.obtenerPacientePorId(id) == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error",
                            "Paciente no encontrado"
                    ));
        }

        List<EntityModel<Atencion>> atenciones =
                pacienteService.obtenerAtencionesPaciente(id)
                        .stream()
                        .map(atencion -> EntityModel.of(
                                atencion,

                                linkTo(methodOn(PacienteController.class)
                                        .obtenerAtencion(atencion.getId()))
                                        .withSelfRel()
                        ))
                        .toList();

        CollectionModel<EntityModel<Atencion>> recurso =
                CollectionModel.of(atenciones);

        recurso.add(
                linkTo(methodOn(PacienteController.class)
                        .atencionesPaciente(id))
                        .withSelfRel()
        );

        recurso.add(
                linkTo(methodOn(PacienteController.class)
                        .obtenerPaciente(id))
                        .withRel("paciente")
        );

        return ResponseEntity.ok(recurso);
    }

    @PostMapping("/atenciones")
    public ResponseEntity<?> crearAtencion(
            @Valid @RequestBody Atencion atencion) {

        try {

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(
                            pacienteService.crearAtencion(atencion)
                    );

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            e.getMessage()
                    ));
        }
    }

    @PutMapping("/atenciones/{id}")
    public ResponseEntity<?> actualizarAtencion(
            @PathVariable int id,
            @Valid @RequestBody Atencion atencion) {

        try {

            Atencion actualizada =
                    pacienteService.actualizarAtencion(
                            id,
                            atencion
                    );

            if (actualizada == null) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "error",
                                "Atención no encontrada"
                        ));
            }

            return ResponseEntity.ok(actualizada);

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            e.getMessage()
                    ));
        }
    }

    @DeleteMapping("/atenciones/{id}")
    public ResponseEntity<?> eliminarAtencion(
            @PathVariable int id) {

        if (!pacienteService.eliminarAtencion(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error",
                            "Atención no encontrada"
                    ));
        }

        return ResponseEntity.ok(
                Map.of(
                        "mensaje",
                        "Atención eliminada correctamente"
                )
        );
    }
}