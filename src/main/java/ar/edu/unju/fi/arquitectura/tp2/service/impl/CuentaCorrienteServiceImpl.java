package ar.edu.unju.fi.arquitectura.tp2.service.impl;

import org.springframework.stereotype.Service;

import ar.edu.unju.fi.arquitectura.tp2.model.CuentaCorriente;
import ar.edu.unju.fi.arquitectura.tp2.repository.CuentaCorrienteRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.CuentaCorrienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaCorrienteServiceImpl implements CuentaCorrienteService{
	
	private final CuentaCorrienteRepository cuentaCorrienteRepository;

	@Override
	public CuentaCorriente crearCuentaCorriente(CuentaCorriente cuentaCorriente) {
		// TODO Auto-generated method stub
		return cuentaCorrienteRepository.save(cuentaCorriente);
	}

}
