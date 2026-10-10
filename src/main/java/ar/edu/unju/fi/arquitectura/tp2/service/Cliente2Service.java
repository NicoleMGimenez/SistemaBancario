package ar.edu.unju.fi.arquitectura.tp2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.fi.arquitectura.tp2.model.Cliente;
import ar.edu.unju.fi.arquitectura.tp2.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public abstract class Cliente2Service {
	
	private final ClienteRepository clienteRepository;
	
	public Cliente crearCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public Cliente obtenerPorCuil(String Cuil) {
		return clienteRepository.findByCuil(Cuil).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con el Cuil: " + Cuil));
	}
	
	public List<Cliente> obtenerPorNombre(String Nombre) {
		return clienteRepository.findByNombreRazonSocialContainingIgnoreCase(Nombre);
	}
}
