package com.example.usuarios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Daniel");
        usuario.setApellido("Loma");
        usuario.setCorreo("daniel@correo.cl");
        usuario.setPassword("1234");

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(usuario));

        // Act
        Usuario resultado = usuarioService.obtenerUsuarioPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Daniel", resultado.getNombre());
        assertEquals("Loma", resultado.getApellido());
        assertEquals("daniel@correo.cl", resultado.getCorreo());
    }
    @Test
@DisplayName("Debe eliminar un usuario existente")
void eliminarUsuario_debeRetornarTrue() {

    // Arrange
    when(usuarioRepository.existsById(1)).thenReturn(true);

    // Act
    boolean resultado = usuarioService.eliminarUsuario(1);

    // Assert
    assertTrue(resultado);
    verify(usuarioRepository).deleteById(1);
}
}