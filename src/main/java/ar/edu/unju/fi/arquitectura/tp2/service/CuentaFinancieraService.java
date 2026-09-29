package ar.edu.unju.fi.arquitectura.tp2.service;

import java.util.List;

import ar.edu.unju.fi.arquitectura.tp2.dto.CuentaRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.CuentaResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;

public interface CuentaFinancieraService {
	
	CuentaFinanciera crearCuentaFinanciera(CuentaFinanciera cuentaFinanciera);
	
	CuentaResponseDto obtenerPorCbu(String cbu);
	
	CuentaResponseDto obtenerPorAlias(String alias);
	
	List<CuentaResponseDto> buscarPorEstado(EstadoCuenta estado);

}
