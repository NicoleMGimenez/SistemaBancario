package ar.edu.unju.fi.arquitectura.tp2.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Cliente extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nombre_razon_social", nullable = false, length = 150)
    private String nombreRazonSocial;

    @Column(nullable = false, unique = true, length = 11)
    @EqualsAndHashCode.Include
    private String cuil;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 20)
    private String telefono;

    @Column(length = 200)
    private String direccion;

    @ManyToMany(mappedBy = "titulares", fetch = FetchType.LAZY)
    @Builder.Default
    private List<CuentaFinanciera> cuentas = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_familiar", nullable = false)
    @Builder.Default
    private RolFamiliar rolFamiliar = RolFamiliar.TITULAR;

    // Si es adherente, apunta a su titular. Si es titular, este campo queda en null.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titular_id")
    private Cliente titular;
}