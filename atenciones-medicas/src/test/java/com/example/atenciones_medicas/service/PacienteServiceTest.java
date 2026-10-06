package com.example.atenciones_medicas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.atenciones_medicas.model.Paciente;
import com.example.atenciones_medicas.repository.AtencionRepository;
import com.example.atenciones_medicas.repository.ConsultaRepository;
import com.example.atenciones_medicas.repository.PacienteRepository;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private AtencionRepository atencionRepository;

    @InjectMocks
    private PacienteService pacienteService;

    @Test
    @DisplayName("Debe obtener un paciente por su ID")
    void obtenerPacientePorId_debeRetornarPaciente() {

        // Arrange
        Paciente paciente = new Paciente();
        paciente.setId(1);
        paciente.setNombre("Daniel");
        paciente.setApellido("Loma");
        paciente.setRut("11111111-1");
        paciente.setEdad(30);
        paciente.setTelefono("912345678");

        when(pacienteRepository.findById(1))
                .thenReturn(Optional.of(paciente));

        // Act
        Paciente resultado =
                pacienteService.obtenerPacientePorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Daniel", resultado.getNombre());
        assertEquals("Loma", resultado.getApellido());
        assertEquals("11111111-1", resultado.getRut());
    }

    @Test
    @DisplayName("Debe eliminar un paciente existente")
    void eliminarPaciente_debeRetornarTrue() {

        // Arrange
        when(pacienteRepository.existsById(1))
                .thenReturn(true);

        // Act
        boolean resultado =
                pacienteService.eliminarPaciente(1);

        // Assert
        assertTrue(resultado);
        verify(pacienteRepository).deleteById(1);
    }
}