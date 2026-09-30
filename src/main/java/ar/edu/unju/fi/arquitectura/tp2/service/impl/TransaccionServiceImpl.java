package ar.edu.unju.fi.arquitectura.tp2.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.arquitectura.tp2.dto.TransaccionRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.TransaccionResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.exception.RecursoNoEncontradoException;
import ar.edu.unju.fi.arquitectura.tp2.exception.SaldoInsuficienteException;
import ar.edu.unju.fi.arquitectura.tp2.model.CuentaCorriente;
import ar.edu.unju.fi.arquitectura.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitectura.tp2.model.TipoTransaccion;
import ar.edu.unju.fi.arquitectura.tp2.model.Transaccion;
import ar.edu.unju.fi.arquitectura.tp2.repository.CuentaFinancieraRepository;
import ar.edu.unju.fi.arquitectura.tp2.repository.TransaccionRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final CuentaFinancieraRepository cuentaFinancieraRepository;

    @Override
    @Transactional
    public TransaccionResponseDto crearTransaccion(TransaccionRequestDto request) {
        log.info("Procesando transacción de tipo: {} por un monto de: {}", request.getTipo(), request.getMonto());

        // Validamos existencia del recurso
        CuentaFinanciera cuenta = cuentaFinancieraRepository.findById(request.getCuentaId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Cuenta financiera no encontrada con ID: " + request.getCuentaId()));

        // Si la operación debita fondos, validamos saldo y margen
        if (request.getTipo() == TipoTransaccion.EXTRACCION ||
                request.getTipo() == TipoTransaccion.TRANSFERENCIA_ENVIADA) {

            BigDecimal fondosDisponibles = cuenta.getSaldoOperativo();

            // Si es Cuenta Corriente, sumar el margen de descubierto autorizado
            if (cuenta instanceof CuentaCorriente cc) {
                if (cc.getMargen() != null) {
                    fondosDisponibles = fondosDisponibles.add(cc.getMargen());
                }
            }

            // Validar si los fondos alcanzan
            if (request.getMonto().compareTo(fondosDisponibles) > 0) {
                log.warn("Fondos insuficientes en la cuenta ID: {}. Disponibles: {}, Requeridos: {}",
                        cuenta.getId(), fondosDisponibles, request.getMonto());
                throw new SaldoInsuficienteException(
                        "Fondos insuficientes: el saldo operativo y margen disponible ($" +
                                fondosDisponibles + ") no cubren el monto a debitar ($" + request.getMonto() + ")");
            }

            // Aplicar el débito sobre el saldo
            cuenta.setSaldoOperativo(cuenta.getSaldoOperativo().subtract(request.getMonto()));
            cuentaFinancieraRepository.save(cuenta);
        } else if (request.getTipo() == TipoTransaccion.DEPOSITO ||
                request.getTipo() == TipoTransaccion.TRANSFERENCIA_RECIBIDA) {
            // Acreditar fondos
            cuenta.setSaldoOperativo(cuenta.getSaldoOperativo().add(request.getMonto()));
            cuentaFinancieraRepository.save(cuenta);
        }

        // Crear y persistir la transacción vinculada a la cuenta
        Transaccion transaccion = Transaccion.builder()
                .cuenta(cuenta)
                .fechaHora(request.getFechaHora())
                .monto(request.getMonto())
                .tipo(request.getTipo())
                .estado(request.getEstado())
                .build();

        Transaccion transaccionGuardada = transaccionRepository.save(transaccion);

        return TransaccionResponseDto.builder()
                .id(transaccionGuardada.getId())
                .fechaHora(transaccionGuardada.getFechaHora())
                .monto(transaccionGuardada.getMonto())
                .tipo(transaccionGuardada.getTipo())
                .estado(transaccionGuardada.getEstado())
                .build();
    }
}