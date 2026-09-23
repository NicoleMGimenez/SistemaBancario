package ar.edu.unju.fi.arquitectura.tp2.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.arquitectura.tp2.model.Transaccion;
import ar.edu.unju.fi.arquitectura.tp2.repository.TransaccionRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransaccionServiceImpl implements TransaccionService {

	private final TransaccionRepository transaccionRepository;

	@Override
	@Transactional
	public Transaccion crearTransaccion(Transaccion transaccion) {
		log.info("Persistiendo transaccion de tipo: {} por un monto de: {}", transaccion.getTipo(), transaccion.getMonto());
		return transaccionRepository.save(transaccion);
	}
}