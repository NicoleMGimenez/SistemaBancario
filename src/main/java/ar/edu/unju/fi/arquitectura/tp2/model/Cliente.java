package ar.edu.unju.fi.arquitectura.tp2.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Setter
@Getter

public class Cliente extends Auditoria{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_razon_social", nullable = false, length = 150)
    private String nombreRazonSocial;

    @Column(nullable = false, unique = true, length = 11)
    private String cuil;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 20)
    private String telefono;

    @Column(length = 200)
    private String direccion;

    @ManyToMany(mappedBy = "titulares", fetch = FetchType.LAZY)
    private List<CuentaFinanciera> cuentas = new ArrayList<>();
}
