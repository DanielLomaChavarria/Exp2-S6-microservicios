package com.example.usuarios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.usuarios.model.Rol;
import com.example.usuarios.model.Usuario;
import com.example.usuarios.repository.DireccionRepository;
import com.example.usuarios.repository.RolRepository;
import com.example.usuarios.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private DireccionRepository direccionRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Debe obtener un usuario por su ID")
    void obtenerUsuarioPorId_debeRetornarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Daniel");
        usuario.setApellido("Loma");
        usuario.setCorreo("daniel@correo.cl");
        usuario.setPassword("1234");

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.obtenerUsuarioPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Daniel", resultado.getNombre());
        assertEquals("Loma", resultado.getApellido());
        assertEquals("daniel@correo.cl", resultado.getCorreo());
    }

    @Test
    @DisplayName("Debe eliminar un usuario existente")
    void eliminarUsuario_debeRetornarTrue() {

        when(usuarioRepository.existsById(1))
                .thenReturn(true);

        boolean resultado = usuarioService.eliminarUsuario(1);

        assertTrue(resultado);
        verify(usuarioRepository).deleteById(1);
    }

    @Test
    @DisplayName("Debe obtener todos los usuarios")
    void obtenerUsuarios_debeRetornarLista() {

        Usuario usuario1 = new Usuario();
        usuario1.setId(1);
        usuario1.setNombre("Daniel");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2);
        usuario2.setNombre("Camila");

        when(usuarioRepository.findAll())
                .thenReturn(List.of(usuario1, usuario2));

        List<Usuario> resultado = usuarioService.obtenerUsuarios();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Daniel", resultado.get(0).getNombre());
        assertEquals("Camila", resultado.get(1).getNombre());
    }

    @Test
    @DisplayName("Debe obtener un rol por su ID")
    void obtenerRolPorId_debeRetornarRol() {

        Rol rol = new Rol();
        rol.setId(1);
        rol.setNombre("Administrador");

        when(rolRepository.findById(1))
                .thenReturn(Optional.of(rol));

        Rol resultado = usuarioService.obtenerRolPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Administrador", resultado.getNombre());
    }
}