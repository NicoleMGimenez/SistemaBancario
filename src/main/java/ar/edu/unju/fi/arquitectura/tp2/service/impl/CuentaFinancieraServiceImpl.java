package ar.edu.unju.fi.arquitectura.tp2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.arquitectura.tp2.dto.CuentaRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.CuentaResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.exception.RecursoNoEncontradoException;
import ar.edu.unju.fi.arquitectura.tp2.model.CajaAhorro;
import ar.edu.unju.fi.arquitectura.tp2.model.Cliente;
import ar.edu.unju.fi.arquitectura.tp2.model.CuentaCorriente;
import ar.edu.unju.fi.arquitectura.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;
import ar.edu.unju.fi.arquitectura.tp2.repository.ClienteRepository;
import ar.edu.unju.fi.arquitectura.tp2.repository.CuentaFinancieraRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.CuentaFinancieraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaFinancieraServiceImpl implements CuentaFinancieraService {

    private final CuentaFinancieraRepository cuentaFinancieraRepository;
    private final ClienteRepository clienteRepository; // Requerido para buscar al titular

    @Override
    @Transactional
    public CuentaResponseDto crearCuentaFinanciera(CuentaRequestDto request) {
        log.info("Creando cuenta de tipo: {}", request.getTipoCuenta());

        // 1. Buscar al cliente titular en la base de datos
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Cliente no encontrado con ID: " + request.getClienteId()));

        CuentaFinanciera cuenta;

        // 2. Instanciar la subclase correspondiente según el discriminador
        if ("CAJA_AHORRO".equalsIgnoreCase(request.getTipoCuenta())) {
            CajaAhorro ca = new CajaAhorro();
            ca.setInteresAnual(request.getInteresAnual());
            ca.setCupoExtraccion(request.getCupoExtraccion());
            cuenta = ca;
        } else if ("CUENTA_CORRIENTE".equalsIgnoreCase(request.getTipoCuenta())) {
            CuentaCorriente cc = new CuentaCorriente();
            cc.setMargen(request.getMargen());
            cc.setMantenimiento(request.getMantenimiento());
            cuenta = cc;
        } else {
            throw new IllegalArgumentException("Tipo de cuenta inválido. Use CAJA_AHORRO o CUENTA_CORRIENTE");
        }

        // 3. Setear los atributos comunes heredados
        cuenta.setAlias(request.getAlias());
        cuenta.setCbu(request.getCbu());
        cuenta.setSaldoOperativo(request.getSaldoOperativo());
        cuenta.setEstado(request.getEstado());

        // ASIGNAR EL CLIENTE TITULAR (Resuelve la violación NOT NULL en cliente_id)
        cuenta.setCliente(cliente);

        // 4. Persistir a través del repositorio
        CuentaFinanciera cuentaGuardada = cuentaFinancieraRepository.save(cuenta);

        // 5. Retornar el DTO de respuesta desacoplado
        return convertirAResponseDto(cuentaGuardada);
    }

    @Override
    public CuentaResponseDto obtenerPorCbu(String cbu) {
        log.info("Buscando cuenta por CBU: {}", cbu);

        CuentaFinanciera cuenta = cuentaFinancieraRepository.findByCbu(cbu)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta no encontrada con el CBU: " + cbu));

        CuentaResponseDto response = convertirAResponseDto(cuenta);
        log.info("DTO generado para CBU {}: ID={}, Alias={}, Saldo={}",
                cbu, response.getId(), response.getAlias(), response.getSaldoOperativo());

        return response;
    }

    @Override
    public List<CuentaResponseDto> buscarPorEstado(EstadoCuenta estado) {
        return List.of();
    }

    @Override
    public CuentaResponseDto obtenerPorAlias(String alias) {
        log.info("Buscando cuenta por alias: {}", alias);

        CuentaFinanciera cuenta = cuentaFinancieraRepository.findByAlias(alias)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta no encontrada con el alias: " + alias));

        return convertirAResponseDto(cuenta);
    }

    private CuentaResponseDto convertirAResponseDto(CuentaFinanciera cuenta) {
        CuentaResponseDto.CuentaResponseDtoBuilder builder = CuentaResponseDto.builder()
                .id(cuenta.getId())
                .alias(cuenta.getAlias())
                .cbu(cuenta.getCbu())
                .saldoOperativo(cuenta.getSaldoOperativo())
                .estado(cuenta.getEstado());

        if (cuenta.getCliente() != null) {
            builder.clienteId(cuenta.getCliente().getId());
        }

        if (cuenta instanceof CajaAhorro ca) {
            builder.tipoCuenta("CAJA_AHORRO")
                    .interesAnual(ca.getInteresAnual())
                    .cupoExtraccion(ca.getCupoExtraccion());
        } else if (cuenta instanceof CuentaCorriente cc) {
            builder.tipoCuenta("CUENTA_CORRIENTE")
                    .margen(cc.getMargen())
                    .mantenimiento(cc.getMantenimiento());
        }

        return builder.build();
    }
}