package ar.edu.unju.fi.arquitectura.tp2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cuentas_corrientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CuentaCorriente extends CuentaFinanciera {

    @Column(name = "margen", precision = 15, scale = 2)
    private BigDecimal margen;

    @Column(name = "mantenimiento", precision = 10, scale = 2)
    private BigDecimal mantenimiento;
}
