package ar.edu.unju.fi.arquitectura.tp2.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.unju.fi.arquitectura.tp2.model.CajaAhorro;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoTransaccion;
import ar.edu.unju.fi.arquitectura.tp2.model.TipoTransaccion;
import ar.edu.unju.fi.arquitectura.tp2.model.Transaccion;
import ar.edu.unju.fi.arquitectura.tp2.repository.TransaccionRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.impl.TransaccionServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TransaccionServiceTest {

	@Mock
	private TransaccionRepository transaccionRepository;

	@InjectMocks
	private TransaccionServiceImpl transaccionService;

	@Test
	@DisplayName("Debe persistir y retornar la transacción correctamente")
	void crearTransaccion_CuandoDatosValidos_DebeRetornarTransaccionPersistida() {
		// Arrange: Preparación del escenario con objetos del dominio desacoplados de la base física
		CajaAhorro cuentaMock = new CajaAhorro();
		cuentaMock.setId(1L);

		Transaccion transaccionEntrada = Transaccion.builder()
				.monto(new BigDecimal("50000.00"))
				.tipo(TipoTransaccion.DEPOSITO)
				.estado(EstadoTransaccion.COMPLETADA)
				.fechaHora(LocalDateTime.now())
				.cuenta(cuentaMock)
				.build();

		Transaccion transaccionGuardada = Transaccion.builder()
				.id(1L)
				.monto(new BigDecimal("50000.00"))
				.tipo(TipoTransaccion.DEPOSITO)
				.estado(EstadoTransaccion.COMPLETADA)
				.fechaHora(transaccionEntrada.getFechaHora())
				.cuenta(cuentaMock)
				.build();

		when(transaccionRepository.save(any(Transaccion.class))).thenReturn(transaccionGuardada);

		// Act: Invocación de la lógica de negocio en la capa de servicio
		Transaccion resultado = transaccionService.crearTransaccion(transaccionEntrada);

		// Assert: Comprobación de estado y verificación de llamada al DAO
		assertNotNull(resultado, "La transacción resultante no debería ser nula");
		assertEquals(1L, resultado.getId());
		assertEquals(new BigDecimal("50000.00"), resultado.getMonto());
		assertEquals(TipoTransaccion.DEPOSITO, resultado.getTipo());
		assertEquals(EstadoTransaccion.COMPLETADA, resultado.getEstado());

		verify(transaccionRepository, times(1)).save(transaccionEntrada);
	}
}