package ar.edu.unju.fi.arquitectura.tp2.dto;

import java.math.BigDecimal;

import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CuentaRequestDto {
	
    @NotBlank
    @Size(max = 20)
    private String alias;

    @NotBlank
    @Size(max = 22)
    private String cbu;

    @NotNull
    private BigDecimal saldoOperativo;

    @NotNull
    private EstadoCuenta estado;
    
    @NotNull
    private Long ClienteId;
}
