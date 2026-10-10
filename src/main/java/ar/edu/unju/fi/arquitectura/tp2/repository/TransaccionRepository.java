package ar.edu.unju.fi.arquitectura.tp2.repository;

import ar.edu.unju.fi.arquitectura.tp2.model.EstadoTransaccion;
import ar.edu.unju.fi.arquitectura.tp2.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

    @Query("SELECT COALESCE(SUM(t.monto), 0) FROM Transaccion t " +
            "WHERE t.clienteOperador.id = :clienteId " +
            "AND t.tipo = ar.edu.unju.fi.arquitectura.tp2.model.TipoTransaccion.EXTRACCION " +
            "AND t.estado = ar.edu.unju.fi.arquitectura.tp2.model.EstadoTransaccion.COMPLETADA " +
            "AND t.fechaHora BETWEEN :inicioDia AND :finDia")
    BigDecimal calcularTotalExtraidoEnElDia(
            @Param  ("clienteId") Long clienteId,
            @Param("inicioDia") LocalDateTime inicioDia,
            @Param("finDia") LocalDateTime finDia);

}