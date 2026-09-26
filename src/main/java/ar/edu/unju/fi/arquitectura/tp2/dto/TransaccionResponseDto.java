package ar.edu.unju.fi.arquitectura.tp2.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import ar.edu.unju.fi.arquitectura.tp2.model.EstadoTransaccion;
import ar.edu.unju.fi.arquitectura.tp2.model.TipoTransaccion;

public class TransaccionResponseDto {
	
	    private Long id;
	
	    private LocalDateTime fechaHora;

	    private BigDecimal monto;

	    private TipoTransaccion tipo;

	    private EstadoTransaccion estado;

}
