package ar.edu.unju.fi.arquitectura.tp2.service;

import java.util.List;

import ar.edu.unju.fi.arquitectura.tp2.dto.ClienteRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.ClienteResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.model.Cliente;
import jakarta.transaction.Transactional;

public interface ClienteService {
	
	ClienteResponseDto crearCliente(ClienteRequestDto request);
    ClienteResponseDto obtenerPorCuil(String cuil);
    List<ClienteResponseDto> obtenerPorNombre(String nombre);

    @Transactional
    String activarClientePorToken(String tokenStr);
}
