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

import ar.edu.unju.fi.arquitectura.tp2.model.Cliente;
import ar.edu.unju.fi.arquitectura.tp2.model.CuentaCorriente;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;
import ar.edu.unju.fi.arquitectura.tp2.repository.CuentaCorrienteRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.impl.CuentaCorrienteServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CuentaCorrienteServiceTest {

	@Mock
	private CuentaCorrienteRepository cuentaCorrienteRepository;

	@InjectMocks
	private CuentaCorrienteServiceImpl cuentaCorrienteServiceImpl;

	private CuentaCorriente cuentaCorrienteMock;
	private Cliente clienteMock;

	@BeforeEach
	void setUp() {
		// Arrange general: Construcción del grafo de objetos con los accesores get/set del PSM
		clienteMock = new Cliente();
		clienteMock.setId(1L);
		clienteMock.setNombreRazonSocial("María Gómez");
		clienteMock.setCuil("27312223334");

		cuentaCorrienteMock = new CuentaCorriente();
		cuentaCorrienteMock.setId(7L);
		cuentaCorrienteMock.setCbu("0000003100000000000007");
		cuentaCorrienteMock.setAlias("SOFIA.FERNANDEZ.ARS");
		cuentaCorrienteMock.setSaldoOperativo(new BigDecimal("67000.00"));
		cuentaCorrienteMock.setEstado(EstadoCuenta.ACTIVA);
		cuentaCorrienteMock.setCliente(clienteMock);

		// Uso de los accesores que corresponden a tus nombres originales: margen y mantenimiento
		cuentaCorrienteMock.setMargen(new BigDecimal("100000.00"));
		cuentaCorrienteMock.setMantenimiento(new BigDecimal("2500.00"));
	}

	@Test
	@DisplayName("Debe persistir y retornar la cuenta corriente correctamente")
	void crearCuentaCorriente_CuandoDatosSonValidos_DebeRetornarCuentaCorrientePersistida() {
		// Arrange: Configuración del doble de prueba (Mock) del repositorio DAO
		when(cuentaCorrienteRepository.save(any(CuentaCorriente.class))).thenReturn(cuentaCorrienteMock);

		// Act: Invocación del método de negocio en el servicio
		CuentaCorriente resultado = cuentaCorrienteServiceImpl.crearCuentaCorriente(cuentaCorrienteMock);

		// Assert: Validación del estado y verificación de llamadas entre capas
		assertNotNull(resultado, "La cuenta corriente creada no debería ser nula");
		assertEquals(7L, resultado.getId());
		assertEquals("0000003100000000000007", resultado.getCbu());
		assertEquals("SOFIA.FERNANDEZ.ARS", resultado.getAlias());
		assertEquals(new BigDecimal("67000.00"), resultado.getSaldoOperativo());
		assertEquals(new BigDecimal("100000.00"), resultado.getMargen());
		assertEquals(new BigDecimal("2500.00"), resultado.getMantenimiento());

		verify(cuentaCorrienteRepository, times(1)).save(cuentaCorrienteMock);
	}
}