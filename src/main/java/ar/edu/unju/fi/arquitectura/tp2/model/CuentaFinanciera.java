package ar.edu.unju.fi.arquitectura.tp2.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cuentas_financieras")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public abstract class CuentaFinanciera extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    @EqualsAndHashCode.Include
    private String alias;

    @Column(nullable = false, unique = true, length = 22)
    @EqualsAndHashCode.Include
    private String cbu;

    @Column(name = "saldo_operativo", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoOperativo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCuenta estado;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cuentas_titulares",
            joinColumns = @JoinColumn(name = "cuenta_id"),
            inverseJoinColumns = @JoinColumn(name = "cliente_id")
    )
    private List<Cliente> titulares = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaccion> transacciones = new ArrayList<>();
}