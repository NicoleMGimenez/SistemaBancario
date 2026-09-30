package ar.edu.unju.fi.arquitectura.tp2.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import ar.edu.unju.fi.arquitectura.tp2.model.EstadoTransaccion;
import ar.edu.unju.fi.arquitectura.tp2.model.TipoTransaccion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionRequestDto {

    @NotNull(message = "El ID de la cuenta es obligatorio")
    private Long cuentaId;

    @NotNull(message = "La fecha y hora es obligatoria")
    private LocalDateTime fechaHora;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a cero")
    private BigDecimal monto;

    @NotNull(message = "El tipo de transacción es obligatorio")
    private TipoTransaccion tipo;

    @NotNull(message = "El estado de la transacción es obligatorio")
    private EstadoTransaccion estado;
}