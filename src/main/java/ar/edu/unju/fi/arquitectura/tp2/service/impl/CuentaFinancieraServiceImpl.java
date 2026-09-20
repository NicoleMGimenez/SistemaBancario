package ar.edu.unju.fi.arquitectura.tp2.service.impl;

import org.springframework.stereotype.Service;

import ar.edu.unju.fi.arquitectura.tp2.model.CuentaFinanciera;
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

}
