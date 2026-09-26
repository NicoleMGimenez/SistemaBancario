package ar.edu.unju.fi.arquitectura.tp2.service;

import ar.edu.unju.fi.arquitectura.tp2.dto.TransaccionRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.TransaccionResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.model.Transaccion;

public interface TransaccionService {
	
	TransaccionResponseDto crearTransaccion(TransaccionRequestDto transaccion);

}
