package ar.edu.unju.fi.arquitectura.tp2.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import ar.edu.unju.fi.arquitectura.tp2.model.EstadoTransaccion;
import ar.edu.unju.fi.arquitectura.tp2.model.TipoTransaccion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class TransaccionRequestDto {
	
	@NotNull
    private LocalDateTime fechaHora;

    @NotNull
    private BigDecimal monto;

    @NotNull
    @Size(max = 30)
    private TipoTransaccion tipo;

    @NotNull
    @Size(max = 20)
    private EstadoTransaccion estado;

    

}
