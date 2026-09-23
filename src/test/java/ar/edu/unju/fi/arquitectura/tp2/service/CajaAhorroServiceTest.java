package ar.edu.unju.fi.arquitectura.tp2.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.unju.fi.arquitectura.tp2.model.CajaAhorro;
import ar.edu.unju.fi.arquitectura.tp2.model.Cliente;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;
import ar.edu.unju.fi.arquitectura.tp2.repository.CajaAhorroRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.impl.CajaAhorroServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CajaAhorroServiceTest {

	@Mock
	private CajaAhorroRepository cajaAhorroRepository;

	@InjectMocks
	private CajaAhorroServiceImpl cajaAhorroService;

	private CajaAhorro cajaAhorroMock;
	private Cliente clienteMock;

	@BeforeEach
	void setUp() {
		// Arrange general: Construcción de las entidades del dominio respetando los tipos (BigDecimal)
		clienteMock = new Cliente();
		clienteMock.setId(1L);
		clienteMock.setNombreRazonSocial("Juan Pérez");
		clienteMock.setCuil("20301112229");

		cajaAhorroMock = new CajaAhorro();
		cajaAhorroMock.setId(1L);
		cajaAhorroMock.setCbu("0000003100000000000001");
		cajaAhorroMock.setAlias("JUAN.PEREZ.ARS");
		cajaAhorroMock.setSaldoOperativo(new BigDecimal("150000.50"));
		cajaAhorroMock.setEstado(EstadoCuenta.ACTIVA);
		cajaAhorroMock.setCliente(clienteMock);
		cajaAhorroMock.setInteresAnual(new BigDecimal("40.00"));
		cajaAhorroMock.setCupoExtraccion(5);
	}

	@Test
	@DisplayName("Debe persistir y retornar la caja de ahorro de manera correcta")
	void crearCajaAhorro_CuandoDatosSonValidos_DebeRetornarCajaAhorroPersistida() {
		// Arrange: Se configura el mock del DAO para retornar la instancia esperada
		when(cajaAhorroRepository.save(any(CajaAhorro.class))).thenReturn(cajaAhorroMock);

		// Act: Invocación de la operación en la Capa de Negocio (Servicio)
		CajaAhorro resultado = cajaAhorroService.crearCajaAhorro(cajaAhorroMock);

		// Assert: Verificación de la integridad de los datos retornados y de la interacción
		assertNotNull(resultado, "La caja de ahorro creada no debería ser nula");
		assertEquals(1L, resultado.getId());
		assertEquals("0000003100000000000001", resultado.getCbu());
		assertEquals("JUAN.PEREZ.ARS", resultado.getAlias());
		assertEquals(new BigDecimal("150000.50"), resultado.getSaldoOperativo());
		assertEquals(new BigDecimal("40.00"), resultado.getInteresAnual());
		assertEquals(5, resultado.getCupoExtraccion());

		verify(cajaAhorroRepository, times(1)).save(cajaAhorroMock);
	}
}