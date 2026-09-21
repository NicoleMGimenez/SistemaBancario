package ar.edu.unju.fi.arquitectura.tp2.service;

import java.util.List;

import ar.edu.unju.fi.arquitectura.tp2.model.CuentaFinanciera;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;

public interface CuentaFinancieraService {
	
	CuentaFinanciera crearCuentaFinanciera(CuentaFinanciera cuentaFinanciera);
	
	CuentaFinanciera obtenerPorCbu(String cbu);
	
	CuentaFinanciera obtenerPorAlias(String alias);
	
	List<CuentaFinanciera> buscarPorEstado(EstadoCuenta estado);

}
