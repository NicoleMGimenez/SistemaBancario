package ar.edu.unju.fi.arquitectura.tp2.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoTransaccion;
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
public class TransferenciaResponseDto {
    private Long idTransaccionOrigen;
    private Long idTransaccionDestino;
    private String cbuOrigen;
    private String cbuDestino;
    private BigDecimal monto;
    private EstadoTransaccion estado;
    private LocalDateTime fechaHora;
    private String mensaje;
}