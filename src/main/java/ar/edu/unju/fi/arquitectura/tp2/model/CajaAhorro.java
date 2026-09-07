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
@Table(name = "cajas_ahorro")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CajaAhorro extends CuentaFinanciera{
    @Column(name = "interes_anual", precision = 5, scale = 2)
    private BigDecimal interesAnual;

    @Column(name = "cupo_extraccion")
    private Integer cupoExtraccion;
}
