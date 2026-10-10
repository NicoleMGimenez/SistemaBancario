package ar.edu.unju.fi.arquitectura.tp2.service;

import java.util.List;
import ar.edu.unju.fi.arquitectura.tp2.dto.CuentaRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.CuentaResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;

public interface CuentaFinancieraService {

	CuentaResponseDto crearCuentaFinanciera(CuentaRequestDto request);

	CuentaResponseDto obtenerPorCbu(String cbu);

	List<CuentaResponseDto> buscarPorEstado(EstadoCuenta estado);

	CuentaResponseDto obtenerPorAlias(String alias);
}