package ar.edu.unju.fi.arquitectura.tp2.dto;

import java.math.BigDecimal;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
public class CuentaRequestDto {

    // --- DISCRIMINADOR ---
    @NotBlank(message = "El tipo de cuenta es obligatorio (CAJA_AHORRO o CUENTA_CORRIENTE)")
    private String tipoCuenta;

    // --- ATRIBUTOS COMUNES ---
    @NotBlank(message = "El alias no puede estar vacío")
    @Size(max = 20)
    private String alias;

    @NotBlank(message = "El CBU no puede estar vacío")
    @Size(max = 22)
    private String cbu;

    @NotNull(message = "El saldo operativo es obligatorio")
    @PositiveOrZero(message = "El saldo inicial no puede ser negativo")
    private BigDecimal saldoOperativo;

    @NotNull(message = "El estado de la cuenta es obligatorio")
    private EstadoCuenta estado;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    // --- ATRIBUTOS DE CAJA DE AHORRO ---
    private BigDecimal interesAnual;
    private Integer cupoExtraccion;

    // --- ATRIBUTOS DE CUENTA CORRIENTE ---
    private BigDecimal margen;
    private BigDecimal mantenimiento;
}