package com.example.usuarios.controller;

import java.util.List;
import java.util.Map;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.usuarios.model.Direccion;
import com.example.usuarios.model.Rol;
import com.example.usuarios.model.Usuario;
import com.example.usuarios.service.UsuarioService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // =========================================================
    // USUARIOS
    // =========================================================

    @GetMapping("/usuarios")
    public CollectionModel<EntityModel<Usuario>> obtenerUsuarios() {

        List<EntityModel<Usuario>> usuarios = usuarioService.obtenerUsuarios()
                .stream()
                .map(usuario -> EntityModel.of(
                        usuario,
                        linkTo(methodOn(UsuarioController.class)
                                .obtenerUsuarioPorId(usuario.getId()))
                                .withSelfRel()
                ))
                .toList();

        return CollectionModel.of(
                usuarios,
                linkTo(methodOn(UsuarioController.class)
                        .obtenerUsuarios())
                        .withSelfRel()
        );
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable int id) {

        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Usuario no encontrado"));
        }

        EntityModel<Usuario> recurso = EntityModel.of(usuario);

        recurso.add(
                linkTo(methodOn(UsuarioController.class)
                        .obtenerUsuarioPorId(id))
                        .withSelfRel()
        );

        recurso.add(
                linkTo(methodOn(UsuarioController.class)
                        .obtenerUsuarios())
                        .withRel("usuarios")
        );

        return ResponseEntity.ok(recurso);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {

        try {

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(usuarioService.crearUsuario(usuario));

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @PathVariable int id,
            @RequestBody Usuario usuario) {

        try {

            Usuario actualizado =
                    usuarioService.actualizarUsuario(id, usuario);

            if (actualizado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Usuario no encontrado"));
            }

            return ResponseEntity.ok(actualizado);

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable int id) {

        if (!usuarioService.eliminarUsuario(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Usuario no encontrado"));
        }

        return ResponseEntity.ok(
                Map.of("mensaje", "Usuario eliminado correctamente")
        );
    }

    // =========================================================
    // ROLES
    // =========================================================

    @GetMapping("/roles")
    public CollectionModel<EntityModel<Rol>> obtenerRoles() {

        List<EntityModel<Rol>> roles = usuarioService.obtenerRoles()
                .stream()
                .map(rol -> EntityModel.of(
                        rol,
                        linkTo(methodOn(UsuarioController.class)
                                .obtenerRolPorId(rol.getId()))
                                .withSelfRel()
                ))
                .toList();

        return CollectionModel.of(
                roles,
                linkTo(methodOn(UsuarioController.class)
                        .obtenerRoles())
                        .withSelfRel()
        );
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<?> obtenerRolPorId(@PathVariable int id) {

        Rol rol = usuarioService.obtenerRolPorId(id);

        if (rol == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Rol no encontrado"));
        }

        EntityModel<Rol> recurso = EntityModel.of(rol);

        recurso.add(
                linkTo(methodOn(UsuarioController.class)
                        .obtenerRolPorId(id))
                        .withSelfRel()
        );

        recurso.add(
                linkTo(methodOn(UsuarioController.class)
                        .obtenerRoles())
                        .withRel("roles")
        );

        return ResponseEntity.ok(recurso);
    }

    @PostMapping("/roles")
    public ResponseEntity<Rol> crearRol(@RequestBody Rol rol) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearRol(rol));
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<?> actualizarRol(
            @PathVariable int id,
            @RequestBody Rol rol) {

        Rol actualizado =
                usuarioService.actualizarRol(id, rol);

        if (actualizado == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Rol no encontrado"));
        }

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> eliminarRol(@PathVariable int id) {

        if (!usuarioService.eliminarRol(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Rol no encontrado"));
        }

        return ResponseEntity.ok(
                Map.of("mensaje", "Rol eliminado correctamente")
        );
    }

    // =========================================================
    // DIRECCIONES
    // =========================================================

    @GetMapping("/direcciones")
    public CollectionModel<EntityModel<Direccion>> obtenerDirecciones() {

        List<EntityModel<Direccion>> direcciones =
                usuarioService.obtenerDirecciones()
                        .stream()
                        .map(direccion -> EntityModel.of(
                                direccion,
                                linkTo(methodOn(UsuarioController.class)
                                        .obtenerDireccionPorId(
                                                direccion.getId()))
                                        .withSelfRel()
                        ))
                        .toList();

        return CollectionModel.of(
                direcciones,
                linkTo(methodOn(UsuarioController.class)
                        .obtenerDirecciones())
                        .withSelfRel()
        );
    }

    @GetMapping("/direcciones/{id}")
    public ResponseEntity<?> obtenerDireccionPorId(
            @PathVariable int id) {

        Direccion direccion =
                usuarioService.obtenerDireccionPorId(id);

        if (direccion == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error",
                            "Dirección no encontrada"
                    ));
        }

        EntityModel<Direccion> recurso =
                EntityModel.of(direccion);

        recurso.add(
                linkTo(methodOn(UsuarioController.class)
                        .obtenerDireccionPorId(id))
                        .withSelfRel()
        );

        recurso.add(
                linkTo(methodOn(UsuarioController.class)
                        .obtenerDirecciones())
                        .withRel("direcciones")
        );

        return ResponseEntity.ok(recurso);
    }

    @PostMapping("/direcciones")
    public ResponseEntity<Direccion> crearDireccion(
            @RequestBody Direccion direccion) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearDireccion(direccion));
    }

    @PutMapping("/direcciones/{id}")
    public ResponseEntity<?> actualizarDireccion(
            @PathVariable int id,
            @RequestBody Direccion direccion) {

        Direccion actualizada =
                usuarioService.actualizarDireccion(id, direccion);

        if (actualizada == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error",
                            "Dirección no encontrada"
                    ));
        }

        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/direcciones/{id}")
    public ResponseEntity<?> eliminarDireccion(
            @PathVariable int id) {

        if (!usuarioService.eliminarDireccion(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error",
                            "Dirección no encontrada"
                    ));
        }

        return ResponseEntity.ok(
                Map.of(
                        "mensaje",
                        "Dirección eliminada correctamente"
                )
        );
    }

    // =========================================================
    // LOGIN
    // =========================================================

    @GetMapping("/login")
    public ResponseEntity<?> iniciarSesion(
            @RequestParam(required = false) String correo,
            @RequestParam(required = false) String password) {

        if (correo == null || correo.isBlank()) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "El correo es obligatorio"
                    ));
        }

        if (password == null || password.isBlank()) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "La contraseña es obligatoria"
                    ));
        }

        if (!correo.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "El formato del correo no es válido"
                    ));
        }

        if (password.length() < 4) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error",
                            "La contraseña debe tener al menos 4 caracteres"
                    ));
        }

        Usuario usuario =
                usuarioService.iniciarSesion(correo, password);

        if (usuario == null) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "error",
                            "Correo o contraseña incorrectos"
                    ));
        }

        return ResponseEntity.ok(
                Map.of(
                        "mensaje",
                        "Inicio de sesión correcto",
                        "usuario",
                        usuario
                )
        );
    }
}