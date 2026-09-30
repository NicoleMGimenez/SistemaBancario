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

        // 1. Buscar al cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + request.getClienteId()));

        CuentaFinanciera cuenta;

        // 2. Resolver la herencia y armar la entidad específica
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

        // Relacionar la cuenta con su cliente principal
        // cuenta.setCliente(cliente); // Descomentar si la relación es ManyToOne bidireccional desde Cuenta

        // Agregamos al cliente a la lista de titulares de la cuenta
        cuenta.getTitulares().add(cliente);

        // 4. Persistir a través del DataAccessObject
        CuentaFinanciera cuentaGuardada = cuentaFinancieraRepository.save(cuenta);

        // 5. Instanciar el TransferObject y retornarlo[cite: 12]
        return convertirAResponseDto(cuentaGuardada);
    }

    @Override
    public CuentaResponseDto obtenerPorCbu(String cbu) {
        return null;
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

    // ... (mantén tus métodos obtenerPorCbu, buscarPorEstado, obtenerPorAlias igual)

    private CuentaResponseDto convertirAResponseDto(CuentaFinanciera cuenta) {
        CuentaResponseDto.CuentaResponseDtoBuilder builder = CuentaResponseDto.builder()
                .id(cuenta.getId())
                .alias(cuenta.getAlias())
                .cbu(cuenta.getCbu())
                .saldoOperativo(cuenta.getSaldoOperativo())
                .estado(cuenta.getEstado());
        // .clienteId(cuenta.getCliente().getId()); // Descomentar según tu modelo

        // Aplicamos "instanceof" para extraer los campos si es una subclase específica
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