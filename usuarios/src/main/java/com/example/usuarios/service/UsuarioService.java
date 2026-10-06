package com.example.usuarios.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.usuarios.model.Direccion;
import com.example.usuarios.model.Rol;
import com.example.usuarios.model.Usuario;
import com.example.usuarios.repository.DireccionRepository;
import com.example.usuarios.repository.RolRepository;
import com.example.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final DireccionRepository direccionRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            DireccionRepository direccionRepository) {

        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.direccionRepository = direccionRepository;
    }

    // =========================================================
    // USUARIOS
    // =========================================================

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario crearUsuario(Usuario usuario) {

        if (usuario.getRol() != null) {

            Rol rol = rolRepository
                    .findById(usuario.getRol().getId())
                    .orElseThrow(() ->
                            new RuntimeException("Rol no encontrado"));

            usuario.setRol(rol);
        }

        if (usuario.getDireccion() != null) {

            Direccion direccion = direccionRepository
                    .findById(usuario.getDireccion().getId())
                    .orElseThrow(() ->
                            new RuntimeException("Dirección no encontrada"));

            usuario.setDireccion(direccion);
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(int id, Usuario datos) {

        Usuario usuario =
                usuarioRepository.findById(id).orElse(null);

        if (usuario == null) {
            return null;
        }

        usuario.setNombre(datos.getNombre());
        usuario.setApellido(datos.getApellido());
        usuario.setCorreo(datos.getCorreo());

        if (datos.getPassword() != null
                && !datos.getPassword().isBlank()) {

            usuario.setPassword(datos.getPassword());
        }

        if (datos.getRol() != null) {

            Rol rol = rolRepository
                    .findById(datos.getRol().getId())
                    .orElseThrow(() ->
                            new RuntimeException("Rol no encontrado"));

            usuario.setRol(rol);
        }

        if (datos.getDireccion() != null) {

            Direccion direccion = direccionRepository
                    .findById(datos.getDireccion().getId())
                    .orElseThrow(() ->
                            new RuntimeException("Dirección no encontrada"));

            usuario.setDireccion(direccion);
        }

        return usuarioRepository.save(usuario);
    }

    public boolean eliminarUsuario(int id) {

        if (!usuarioRepository.existsById(id)) {
            return false;
        }

        usuarioRepository.deleteById(id);

        return true;
    }

    // =========================================================
    // ROLES
    // =========================================================

    public List<Rol> obtenerRoles() {
        return rolRepository.findAll();
    }

    public Rol obtenerRolPorId(int id) {
        return rolRepository.findById(id).orElse(null);
    }

    public Rol crearRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public Rol actualizarRol(int id, Rol datos) {

        Rol rol =
                rolRepository.findById(id).orElse(null);

        if (rol == null) {
            return null;
        }

        rol.setNombre(datos.getNombre());

        return rolRepository.save(rol);
    }

    public boolean eliminarRol(int id) {

        if (!rolRepository.existsById(id)) {
            return false;
        }

        rolRepository.deleteById(id);

        return true;
    }

    // =========================================================
    // DIRECCIONES
    // =========================================================

    public List<Direccion> obtenerDirecciones() {
        return direccionRepository.findAll();
    }

    public Direccion obtenerDireccionPorId(int id) {
        return direccionRepository.findById(id).orElse(null);
    }

    public Direccion crearDireccion(Direccion direccion) {
        return direccionRepository.save(direccion);
    }

    public Direccion actualizarDireccion(
            int id,
            Direccion datos) {

        Direccion direccion =
                direccionRepository.findById(id).orElse(null);

        if (direccion == null) {
            return null;
        }

        direccion.setCalle(datos.getCalle());
        direccion.setNumero(datos.getNumero());
        direccion.setComuna(datos.getComuna());
        direccion.setCiudad(datos.getCiudad());

        return direccionRepository.save(direccion);
    }

    public boolean eliminarDireccion(int id) {

        if (!direccionRepository.existsById(id)) {
            return false;
        }

        direccionRepository.deleteById(id);

        return true;
    }

    // =========================================================
    // LOGIN
    // =========================================================

    public Usuario iniciarSesion(
            String correo,
            String password) {

        return usuarioRepository
                .findByCorreoIgnoreCase(correo)
                .filter(usuario ->
                        usuario.getPassword().equals(password))
                .orElse(null);
    }
}