package ar.edu.unju.fi.arquitectura.tp2.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.arquitectura.tp2.model.CajaAhorro;
import ar.edu.unju.fi.arquitectura.tp2.repository.CajaAhorroRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.CajaAhorroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CajaAhorroServiceImpl implements CajaAhorroService {

	private final CajaAhorroRepository cajaAhorroRepository;

	@Override
	@Transactional
	public CajaAhorro crearCajaAhorro(CajaAhorro cajaAhorro) {
		log.info("Persistiendo nueva caja de ahorro con CBU: {}", cajaAhorro.getCbu());
		return cajaAhorroRepository.save(cajaAhorro);
	}
}