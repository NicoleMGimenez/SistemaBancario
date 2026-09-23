package ar.edu.unju.fi.arquitectura.tp2.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.unju.fi.arquitectura.tp2.model.CajaAhorro;
import ar.edu.unju.fi.arquitectura.tp2.model.Cliente;
import ar.edu.unju.fi.arquitectura.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;
import ar.edu.unju.fi.arquitectura.tp2.repository.CuentaFinancieraRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.impl.CuentaFinancieraServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CuentaFinancieraServiceTest {

	@Mock
	private CuentaFinancieraRepository cuentaFinancieraRepository;

	@InjectMocks
	private CuentaFinancieraServiceImpl cuentaFinancieraService;

	private CuentaFinanciera cuentaMock;
	private Cliente clienteMock;

	@BeforeEach
	void setUp() {
		// Arrange general: Instanciamos una clase concreta (CajaAhorro) al ser CuentaFinanciera abstracta
		clienteMock = new Cliente();
		clienteMock.setId(1L);
		clienteMock.setNombreRazonSocial("Juan Pérez");
		clienteMock.setCuil("20301112229");

		CajaAhorro caja = new CajaAhorro();
		caja.setId(1L);
		caja.setCbu("0000003100000000000001");
		caja.setAlias("JUAN.PEREZ.ARS");
		caja.setSaldoOperativo(new BigDecimal("150000.50"));
		caja.setEstado(EstadoCuenta.ACTIVA);
		caja.setCliente(clienteMock);
		caja.setInteresAnual(new BigDecimal("40.0"));
		caja.setCupoExtraccion(5);

		cuentaMock = caja;
	}

	@Test
	@DisplayName("Debe persistir y retornar la cuenta financiera correctamente")
	void crearCuentaFinanciera_CuandoDatosCorrectos_DebeRetornarCuentaGuardada() {
		// Arrange
		when(cuentaFinancieraRepository.save(any(CuentaFinanciera.class))).thenReturn(cuentaMock);

		// Act
		CuentaFinanciera resultado = cuentaFinancieraService.crearCuentaFinanciera(cuentaMock);

		// Assert
		assertNotNull(resultado, "La cuenta creada no debería ser nula");
		assertEquals(cuentaMock.getCbu(), resultado.getCbu());
		assertEquals(cuentaMock.getAlias(), resultado.getAlias());
		verify(cuentaFinancieraRepository, times(1)).save(cuentaMock);
	}

	@Test
	@DisplayName("Debe retornar la cuenta cuando el CBU existe")
	void obtenerPorCbu_CuandoExiste_DebeRetornarCuenta() {
		// Arrange
		String cbu = "0000003100000000000001";
		when(cuentaFinancieraRepository.findByCbu(cbu)).thenReturn(Optional.of(cuentaMock));

		// Act
		CuentaFinanciera resultado = cuentaFinancieraService.obtenerPorCbu(cbu);

		// Assert
		assertNotNull(resultado);
		assertEquals(cbu, resultado.getCbu());
		verify(cuentaFinancieraRepository, times(1)).findByCbu(cbu);
	}

	@Test
	@DisplayName("Debe lanzar excepción cuando el CBU no existe")
	void obtenerPorCbu_CuandoNoExiste_DebeLanzarExcepcion() {
		// Arrange
		String cbuInexistente = "9999999999999999999999";
		when(cuentaFinancieraRepository.findByCbu(cbuInexistente)).thenReturn(Optional.empty());

		// Act & Assert
		IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
			cuentaFinancieraService.obtenerPorCbu(cbuInexistente);
		});

		assertTrue(excepcion.getMessage().contains("Cuentas no encontrado con el CBU: " + cbuInexistente));
		verify(cuentaFinancieraRepository, times(1)).findByCbu(cbuInexistente);
	}

	@Test
	@DisplayName("Debe retornar la cuenta cuando el alias existe")
	void obtenerPorAlias_CuandoExiste_DebeRetornarCuenta() {
		// Arrange
		String alias = "JUAN.PEREZ.ARS";
		when(cuentaFinancieraRepository.findByAlias(alias)).thenReturn(Optional.of(cuentaMock));

		// Act
		CuentaFinanciera resultado = cuentaFinancieraService.obtenerPorAlias(alias);

		// Assert
		assertNotNull(resultado);
		assertEquals(alias, resultado.getAlias());
		verify(cuentaFinancieraRepository, times(1)).findByAlias(alias);
	}

	@Test
	@DisplayName("Debe retornar lista de cuentas según el estado solicitado")
	void buscarPorEstado_CuandoExistenCuentas_DebeRetornarLista() {
		// Arrange
		when(cuentaFinancieraRepository.findByEstado(EstadoCuenta.ACTIVA)).thenReturn(List.of(cuentaMock));

		// Act
		List<CuentaFinanciera> resultado = cuentaFinancieraService.buscarPorEstado(EstadoCuenta.ACTIVA);

		// Assert
		assertNotNull(resultado);
		assertFalse(resultado.isEmpty());
		assertEquals(1, resultado.size());
		assertEquals(EstadoCuenta.ACTIVA, resultado.get(0).getEstado());
		verify(cuentaFinancieraRepository, times(1)).findByEstado(EstadoCuenta.ACTIVA);
	}
}