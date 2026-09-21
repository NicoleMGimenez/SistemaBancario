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
		
		String cuil="";
		Cliente clienteEsperado= Cliente.builder()
				.cuil(cuil)
				.nombreRazonSocial()
				.cuil()
				.email()
				.build();
		
		when(clienteRepository.findByCuil(cuil)).thenReturn(Optional.of(clienteEsperado));
		
		Cliente resultado = clienteServiceImpl.obtenerPorCuil(cuil);
		
		assertNotNull(resultado, "El cliente retornado no debería ser nulo");
        assertEquals(clienteId, resultado.getCuil());
        assertEquals("", resultado.getNombreRazonSocial());
        
        verify(clienteRepository, times(1)).findByCuil(cuil);
	}
	
	

}
