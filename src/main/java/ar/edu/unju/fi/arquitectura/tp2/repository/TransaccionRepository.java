package ar.edu.unju.fi.arquitectura.tp2.repository;

import ar.edu.unju.fi.arquitectura.tp2.model.EstadoTransaccion;
import ar.edu.unju.fi.arquitectura.tp2.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    // Historial de una cuenta ordenado de más reciente a más antiguo
    List<Transaccion> findByCuentaIdOrderByFechaHoraDesc(Long cuentaId);

    // Filtro por estado y rango de fechas
    List<Transaccion> findByEstadoAndFechaHoraBetween(
            EstadoTransaccion estado,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    );
}