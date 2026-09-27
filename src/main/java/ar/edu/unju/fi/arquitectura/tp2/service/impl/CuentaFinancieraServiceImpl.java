package ar.edu.unju.fi.arquitectura.tp2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.fi.arquitectura.tp2.dto.CuentaRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.CuentaResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.exception.RecursoNoEncontradoException;
import ar.edu.unju.fi.arquitectura.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;
import ar.edu.unju.fi.arquitectura.tp2.repository.CuentaFinancieraRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.CuentaFinancieraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaFinancieraServiceImpl implements CuentaFinancieraService{
	
	private final CuentaFinancieraRepository cuentaFinancieraRepository;

	@Override
	public CuentaFinanciera crearCuentaFinanciera(CuentaFinanciera cuentaFinanciera) {
		// TODO Auto-generated method stub
		return cuentaFinancieraRepository.save(cuentaFinanciera);
	}

	@Override
    public CuentaResponseDto obtenerPorCbu(String cbu) {

        CuentaFinanciera cuenta = cuentaFinancieraRepository.findByCbu(cbu)
                .orElseThrow(() ->new RecursoNoEncontradoException("Cuenta no encontrada con el CBU: " + cbu));

        return convertirAResponseDto(cuenta);
    }

    @Override
    public List<CuentaResponseDto> buscarPorEstado(EstadoCuenta estado) {

        List<CuentaFinanciera> cuentas =
                cuentaFinancieraRepository.findByEstado(estado);

        return cuentas.stream()
                .map(this::convertirAResponseDto)
                .toList();
    }

    @Override
    public CuentaResponseDto obtenerPorAlias(String alias) {

        CuentaFinanciera cuenta = cuentaFinancieraRepository
                .findByAlias(alias)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Cuenta no encontrada con el alias: " + alias));

        return convertirAResponseDto(cuenta);
    }

    private CuentaResponseDto convertirAResponseDto(CuentaFinanciera cuenta) {

        return CuentaResponseDto.builder()
                .id(cuenta.getId())
                .alias(cuenta.getAlias())
                .cbu(cuenta.getCbu())
                .saldoOperativo(cuenta.getSaldoOperativo())
                .estado(cuenta.getEstado())
                .build();
    }
}
