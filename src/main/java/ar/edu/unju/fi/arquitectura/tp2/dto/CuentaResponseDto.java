package ar.edu.unju.fi.arquitectura.tp2.dto;

import java.math.BigDecimal;

import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;

public class CuentaResponseDto {
	
	private Long id;
    private String alias;
    private String cbu;
    private BigDecimal saldoOperativo;
    private EstadoCuenta estado;
    private Long cuentaid;

}
