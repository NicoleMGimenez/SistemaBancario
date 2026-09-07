package ar.edu.unju.fi.arquitectura.tp2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cuentas_corrientes")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CuentaCorriente extends CuentaFinanciera {
    @Column(precision = 15, scale = 2)
    private BigDecimal margen;

    @Column(precision = 10, scale = 2)
    private BigDecimal mantenimiento;
}
