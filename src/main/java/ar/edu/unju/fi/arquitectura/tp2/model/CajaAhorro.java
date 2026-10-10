package ar.edu.unju.fi.arquitectura.tp2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cajas_ahorro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CajaAhorro extends CuentaFinanciera {

    @Column(name = "interes_anual", precision = 5, scale = 2)
    private BigDecimal interesAnual;

    @Column(name = "cupo_extraccion")
    private Integer cupoExtraccion;
}