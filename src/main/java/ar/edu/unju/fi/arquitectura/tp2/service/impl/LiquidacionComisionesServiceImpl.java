package ar.edu.unju.fi.arquitectura.tp2.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.arquitectura.tp2.model.CajaAhorro;
import ar.edu.unju.fi.arquitectura.tp2.model.CuentaCorriente;
import ar.edu.unju.fi.arquitectura.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoTransaccion;
import ar.edu.unju.fi.arquitectura.tp2.model.TipoTransaccion;
import ar.edu.unju.fi.arquitectura.tp2.model.Transaccion;
import ar.edu.unju.fi.arquitectura.tp2.repository.CuentaFinancieraRepository;
import ar.edu.unju.fi.arquitectura.tp2.repository.TransaccionRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.LiquidacionComisionesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LiquidacionComisionesServiceImpl implements LiquidacionComisionesService {

    private final CuentaFinancieraRepository cuentaFinancieraRepository;
    private final TransaccionRepository transaccionRepository;

    @Value("${app.comisiones.cuenta-corriente:5000.00}")
    private BigDecimal comisionCuentaCorriente;

    @Value("${app.comisiones.caja-ahorro:2000.00}")
    private BigDecimal comisionCajaAhorro;

    @Override
    @Transactional
    public void procesarLiquidacionMensual() {
        log.info("Iniciando liquidación mensual masiva de comisiones...");

        List<CuentaFinanciera> cuentasActivas = cuentaFinancieraRepository.findByEstado(EstadoCuenta.ACTIVA);

        if (cuentasActivas.isEmpty()) {
            log.info("No se hallaron cuentas en estado ACTIVA para liquidar.");
            return;
        }

        int totalProcesadas = 0;

        for (CuentaFinanciera cuenta : cuentasActivas) {
            BigDecimal importeComision = BigDecimal.ZERO;

            // Pattern Matching for instanceof para resolver la jerarquía concreta
            if (cuenta instanceof CuentaCorriente) {
                importeComision = comisionCuentaCorriente;
            } else if (cuenta instanceof CajaAhorro) {
                importeComision = comisionCajaAhorro;
            }

            if (importeComision.compareTo(BigDecimal.ZERO) > 0) {
                // Descontar del saldo operativo
                cuenta.setSaldoOperativo(cuenta.getSaldoOperativo().subtract(importeComision));
                cuentaFinancieraRepository.save(cuenta);

                // Crear e impactar el registro de Transaccion
                Transaccion transaccion = Transaccion.builder()
                        .cuenta(cuenta)
                        .monto(importeComision)
                        .tipo(TipoTransaccion.DEBITO_COMISION)
                        .estado(EstadoTransaccion.COMPLETADA)
                        .fechaHora(LocalDateTime.now())
                        .build();

                transaccionRepository.save(transaccion);
                totalProcesadas++;

                log.info("Comisión de ${} debitada con éxito de la cuenta ID: {} (CBU: {}). Nuevo saldo: ${}",
                        importeComision, cuenta.getId(), cuenta.getCbu(), cuenta.getSaldoOperativo());
            }
        }

        log.info("Proceso completado. Total cuentas debitadas: {}", totalProcesadas);
    }
}