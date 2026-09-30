package ar.edu.unju.fi.arquitectura.tp2.dto;

import java.math.BigDecimal;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;
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
public class CuentaResponseDto {
    private Long id;
    private String tipoCuenta;
    private String alias;
    private String cbu;
    private BigDecimal saldoOperativo;
    private EstadoCuenta estado;
    private Long clienteId;

    // Campos específicos que se llenarán según corresponda
    private BigDecimal interesAnual;
    private Integer cupoExtraccion;
    private BigDecimal margen;
    private BigDecimal mantenimiento;
}