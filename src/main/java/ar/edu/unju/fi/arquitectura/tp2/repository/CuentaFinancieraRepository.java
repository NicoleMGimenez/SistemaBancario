package ar.edu.unju.fi.arquitectura.tp2.repository;

import ar.edu.unju.fi.arquitectura.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaFinancieraRepository extends JpaRepository<CuentaFinanciera, Long> {

    // Búsqueda exacta por identificador de cuenta
    Optional<CuentaFinanciera> findByCbu(String cbu);

    Optional<CuentaFinanciera> findByAlias(String alias);

    // Búsqueda por estado
    List<CuentaFinanciera> findByEstado(EstadoCuenta estado);
}