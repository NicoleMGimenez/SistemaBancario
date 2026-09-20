package ar.edu.unju.fi.arquitectura.tp2.service.impl;

import org.springframework.stereotype.Service;

import ar.edu.unju.fi.arquitectura.tp2.model.CajaAhorro;
import ar.edu.unju.fi.arquitectura.tp2.repository.CajaAhorroRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.CajaAhorroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CajaAhorroImpl implements CajaAhorroService{
	
	private final CajaAhorroRepository cajaAhorroRepository;

	@Override
	public CajaAhorro crearCajaAhorro(CajaAhorro cajaAhorro) {
		// TODO Auto-generated method stub
		return cajaAhorroRepository.save(cajaAhorro);
	}

}
