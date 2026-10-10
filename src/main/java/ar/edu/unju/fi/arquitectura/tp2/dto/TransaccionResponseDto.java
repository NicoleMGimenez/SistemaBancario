package ar.edu.unju.fi.arquitectura.tp2.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import ar.edu.unju.fi.arquitectura.tp2.model.EstadoTransaccion;
import ar.edu.unju.fi.arquitectura.tp2.model.TipoTransaccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionResponseDto {

	private Long id;
	private LocalDateTime fechaHora;
	private BigDecimal monto;
	private TipoTransaccion tipo;
	private EstadoTransaccion estado;
}