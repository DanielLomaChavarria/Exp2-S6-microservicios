package com.example.usuarios.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.usuarios.model.Direccion;
import com.example.usuarios.model.Rol;
import com.example.usuarios.model.Usuario;
import com.example.usuarios.service.UsuarioService;

@RestController
@RequestMapping
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // =========================
    // USUARIOS
    // =========================

    @GetMapping("/usuarios")
    public List<Usuario> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable int id) {

        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Usuario no encontrado"));
        }

        return ResponseEntity.ok(usuario);
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

            Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);

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
                Map.of("mensaje", "Usuario eliminado correctamente"));
    }

    // =========================
    // ROLES
    // =========================

    @GetMapping("/roles")
    public List<Rol> obtenerRoles() {
        return usuarioService.obtenerRoles();
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

        Rol actualizado = usuarioService.actualizarRol(id, rol);

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
                Map.of("mensaje", "Rol eliminado correctamente"));
    }

    // =========================
    // DIRECCIONES
    // =========================

    @GetMapping("/direcciones")
    public List<Direccion> obtenerDirecciones() {
        return usuarioService.obtenerDirecciones();
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
                    .body(Map.of("error", "Dirección no encontrada"));
        }

        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/direcciones/{id}")
    public ResponseEntity<?> eliminarDireccion(@PathVariable int id) {

        if (!usuarioService.eliminarDireccion(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Dirección no encontrada"));
        }

        return ResponseEntity.ok(
                Map.of("mensaje", "Dirección eliminada correctamente"));
    }

    // =========================
    // LOGIN
    // =========================

    @GetMapping("/login")
    public ResponseEntity<?> iniciarSesion(
            @RequestParam(required = false) String correo,
            @RequestParam(required = false) String password) {

        if (correo == null || correo.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "El correo es obligatorio"));
        }

        if (password == null || password.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "La contraseña es obligatoria"));
        }

        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "El formato del correo no es válido"));
        }

        if (password.length() < 4) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error",
                            "La contraseña debe tener al menos 4 caracteres"));
        }

        Usuario usuario =
                usuarioService.iniciarSesion(correo, password);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error",
                            "Correo o contraseña incorrectos"));
        }

        return ResponseEntity.ok(
                Map.of(
                        "mensaje", "Inicio de sesión correcto",
                        "usuario", usuario
                )
        );
    }
}