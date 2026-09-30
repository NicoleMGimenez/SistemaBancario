package ar.edu.unju.fi.arquitectura.tp2.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class TransferenciaRequestDto {

    @NotBlank(message = "El CBU de la cuenta de origen es obligatorio")
    private String cbuOrigen;

    @NotBlank(message = "El CBU de la cuenta de destino es obligatorio")
    private String cbuDestino;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto a transferir debe ser mayor a cero")
    private BigDecimal monto;
}
