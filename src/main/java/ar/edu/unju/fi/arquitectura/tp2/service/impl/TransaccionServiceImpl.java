package ar.edu.unju.fi.arquitectura.tp2.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.arquitectura.tp2.dto.TransaccionRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.TransaccionResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.TransferenciaRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.TransferenciaResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.exception.RecursoNoEncontradoException;
import ar.edu.unju.fi.arquitectura.tp2.exception.SaldoInsuficienteException;
import ar.edu.unju.fi.arquitectura.tp2.model.CuentaCorriente;
import ar.edu.unju.fi.arquitectura.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoTransaccion;
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
        log.info("Procesando transacción unitaria de tipo: {} por un monto de: {}", request.getTipo(), request.getMonto());

        // 1. Validamos existencia del recurso
        CuentaFinanciera cuenta = cuentaFinancieraRepository.findById(request.getCuentaId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Cuenta financiera no encontrada con ID: " + request.getCuentaId()));

        // 2. Si la operación debita fondos, validamos saldo y margen
        if (request.getTipo() == TipoTransaccion.EXTRACCION ||
                request.getTipo() == TipoTransaccion.TRANSFERENCIA_ENVIADA) {

            BigDecimal fondosDisponibles = cuenta.getSaldoOperativo();

            if (cuenta instanceof CuentaCorriente cc && cc.getMargen() != null) {
                fondosDisponibles = fondosDisponibles.add(cc.getMargen());
            }

            if (request.getMonto().compareTo(fondosDisponibles) > 0) {
                log.warn("Fondos insuficientes en la cuenta ID: {}. Disponibles: {}, Requeridos: {}",
                        cuenta.getId(), fondosDisponibles, request.getMonto());
                throw new SaldoInsuficienteException(
                        "Fondos insuficientes: el saldo operativo y margen disponible ($" +
                                fondosDisponibles + ") no cubren el monto a debitar ($" + request.getMonto() + ")");
            }

            cuenta.setSaldoOperativo(cuenta.getSaldoOperativo().subtract(request.getMonto()));
            cuentaFinancieraRepository.save(cuenta);
        } else if (request.getTipo() == TipoTransaccion.DEPOSITO ||
                request.getTipo() == TipoTransaccion.TRANSFERENCIA_RECIBIDA) {
            cuenta.setSaldoOperativo(cuenta.getSaldoOperativo().add(request.getMonto()));
            cuentaFinancieraRepository.save(cuenta);
        }

        // 3. Crear y persistir la transacción vinculada a la cuenta
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

    @Override
    @Transactional
    public TransferenciaResponseDto realizarTransferencia(TransferenciaRequestDto request) {
        log.info("Iniciando transferencia de ${} desde CBU: {} hacia CBU: {}",
                request.getMonto(), request.getCbuOrigen(), request.getCbuDestino());

        // 1. Regla de consistencia: No transferirse a sí mismo
        if (request.getCbuOrigen().equalsIgnoreCase(request.getCbuDestino())) {
            throw new IllegalArgumentException("La cuenta de origen y de destino no pueden ser la misma");
        }

        // 2. Verificar existencia de ambas cuentas (RecursoNoEncontradoException)
        CuentaFinanciera cuentaOrigen = cuentaFinancieraRepository.findByCbu(request.getCbuOrigen())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Cuenta de origen no encontrada con CBU: " + request.getCbuOrigen()));

        CuentaFinanciera cuentaDestino = cuentaFinancieraRepository.findByCbu(request.getCbuDestino())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Cuenta de destino no encontrada con CBU: " + request.getCbuDestino()));

        // 3. Verificar estado operativo de las cuentas
        if (cuentaOrigen.getEstado() != EstadoCuenta.ACTIVA) {
            throw new IllegalArgumentException("La cuenta de origen no se encuentra ACTIVA para operar");
        }
        if (cuentaDestino.getEstado() != EstadoCuenta.ACTIVA) {
            throw new IllegalArgumentException("La cuenta de destino no se encuentra ACTIVA para recibir fondos");
        }

        // 4. Validar fondos disponibles en origen (Saldo + Margen si es Cuenta Corriente)
        BigDecimal fondosDisponibles = cuentaOrigen.getSaldoOperativo();
        if (cuentaOrigen instanceof CuentaCorriente cc && cc.getMargen() != null) {
            fondosDisponibles = fondosDisponibles.add(cc.getMargen());
        }

        if (request.getMonto().compareTo(fondosDisponibles) > 0) {
            log.warn("Transferencia rechazada: CBU {} dispone de ${} pero solicitó debitar ${}",
                    cuentaOrigen.getCbu(), fondosDisponibles, request.getMonto());
            throw new SaldoInsuficienteException(
                    "Fondos insuficientes: el disponible ($" + fondosDisponibles +
                            ") no cubre el monto a transferir ($" + request.getMonto() + ")");
        }

        // 5. Aplicar movimientos sobre saldos
        cuentaOrigen.setSaldoOperativo(cuentaOrigen.getSaldoOperativo().subtract(request.getMonto()));
        cuentaDestino.setSaldoOperativo(cuentaDestino.getSaldoOperativo().add(request.getMonto()));

        cuentaFinancieraRepository.save(cuentaOrigen);
        cuentaFinancieraRepository.save(cuentaDestino);

        LocalDateTime ahora = LocalDateTime.now();

        // 6. Asentar transacciones de auditoría para ambas cuentas
        Transaccion debito = Transaccion.builder()
                .cuenta(cuentaOrigen)
                .fechaHora(ahora)
                .monto(request.getMonto())
                .tipo(TipoTransaccion.TRANSFERENCIA_ENVIADA)
                .estado(EstadoTransaccion.COMPLETADA)
                .build();

        Transaccion credito = Transaccion.builder()
                .cuenta(cuentaDestino)
                .fechaHora(ahora)
                .monto(request.getMonto())
                .tipo(TipoTransaccion.TRANSFERENCIA_RECIBIDA)
                .estado(EstadoTransaccion.COMPLETADA)
                .build();

        Transaccion debitoGuardado = transaccionRepository.save(debito);
        Transaccion creditoGuardado = transaccionRepository.save(credito);

        // 7. Retornar DTO de transferencia desacoplado del modelo relacional
        return TransferenciaResponseDto.builder()
                .idTransaccionOrigen(debitoGuardado.getId())
                .idTransaccionDestino(creditoGuardado.getId())
                .cbuOrigen(cuentaOrigen.getCbu())
                .cbuDestino(cuentaDestino.getCbu())
                .monto(request.getMonto())
                .estado(EstadoTransaccion.COMPLETADA)
                .fechaHora(ahora)
                .mensaje("Transferencia ejecutada exitosamente")
                .build();
    }
}