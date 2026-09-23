package ar.edu.unju.fi.arquitectura.tp2.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.unju.fi.arquitectura.tp2.model.Cliente;
import ar.edu.unju.fi.arquitectura.tp2.repository.ClienteRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.impl.ClienteServiceImpl;
import lombok.Builder;

@ExtendWith(MockitoExtension.class)
@Builder
public class ClienteServiceTest {
	
	@Mock
	private ClienteRepository clienteRepository;
	
	@InjectMocks
	private ClienteServiceImpl clienteServiceImpl;
	
	@Test
	@DisplayName("Debe retornar un cliente cuando el Cuil existe")
	void obtenerPorCuil_CuandoClienteExiste_DebeRetornarCliente() {
		
		Long clienteId = 1L;
        Cliente clienteEsperado = Cliente.builder()
                .id(clienteId)
                .nombre("david gomez")
                .cuil("20351234567")
                .email("david@email.com")
                .build();
		
        when(clienteRepository.findByCuil(clienteEsperado.getCuil())).thenReturn(Optional.of(clienteEsperado));
		
		Cliente resultado = clienteServiceImpl.obtenerPorCuil(clienteEsperado.getCuil());
		
		assertNotNull(resultado, "El cliente retornado no debería ser nulo");
        assertEquals(clienteEsperado.getCuil(), resultado.getCuil());
        assertEquals(clienteEsperado.getNombreRazonSocial(), resultado.getNombreRazonSocial());
        
        verify(clienteRepository, times(1)).findByCuil(clienteEsperado.getCuil());
	}
	
	

}
