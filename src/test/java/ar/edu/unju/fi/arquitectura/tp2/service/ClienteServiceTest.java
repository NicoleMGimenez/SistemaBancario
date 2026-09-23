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

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository;

	@InjectMocks
	private ClienteServiceImpl clienteServiceImpl;

	@Test
	@DisplayName("Debe retornar un cliente cuando el Cuil existe")
	void obtenerPorCuil_CuandoClienteExiste_DebeRetornarCliente() {
		// Arrange (Preparación de datos y comportamiento del mock)
		String cuil = "20301112229";
		Cliente clienteEsperado = Cliente.builder()
				.id(1L)
				.nombreRazonSocial("Juan Pérez")
				.cuil(cuil)
				.email("juan.perez@email.com")
				.build();

		when(clienteRepository.findByCuil(cuil)).thenReturn(Optional.of(clienteEsperado));

		// Act (Ejecución del método de negocio)
		Cliente resultado = clienteServiceImpl.obtenerPorCuil(cuil);

		// Assert (Verificación de resultados y colaboración entre capas)
		assertNotNull(resultado, "El cliente retornado no debería ser nulo");
		assertEquals(cuil, resultado.getCuil());
		assertEquals(clienteEsperado.getNombreRazonSocial(), resultado.getNombreRazonSocial());

		verify(clienteRepository, times(1)).findByCuil(cuil);
	}
}