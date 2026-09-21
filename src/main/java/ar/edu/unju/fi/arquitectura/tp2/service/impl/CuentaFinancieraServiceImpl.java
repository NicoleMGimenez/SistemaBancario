package ar.edu.unju.fi.arquitectura.tp2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

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
	public CuentaFinanciera obtenerPorCbu(String cbu) {
		// TODO Auto-generated method stub
		return cuentaFinancieraRepository.findByCbu(cbu).orElseThrow(() -> new IllegalArgumentException("Cuentas no encontrado con el CBU: " + cbu));
	}

	@Override
	public List<CuentaFinanciera> buscarPorEstado(EstadoCuenta estado) {
		// TODO Auto-generated method stub
		return cuentaFinancieraRepository.findByEstado(estado);
	}

	@Override
	public CuentaFinanciera obtenerPorAlias(String alias) {
		// TODO Auto-generated method stub
		return cuentaFinancieraRepository.findByAlias(alias).orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrado con el alias: " + alias));
	}

}
