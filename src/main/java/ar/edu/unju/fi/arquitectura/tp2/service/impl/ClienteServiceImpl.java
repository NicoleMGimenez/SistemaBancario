package ar.edu.unju.fi.arquitectura.tp2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.fi.arquitectura.tp2.model.Cliente;
import ar.edu.unju.fi.arquitectura.tp2.repository.ClienteRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService{
	
	private final ClienteRepository clienteRepository;

	@Override
	public Cliente crearCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		System.out.println("Iniciando proceso de creación de cliente con CUIL: " + cliente.getCuil());
		
		if(clienteRepository.existsByCuil(cliente.getCuil())) {
			System.out.println("Fallo en la creacion, ya existe un registro con el cuil: "+cliente.getCuil());
			throw new IllegalArgumentException("Ya existe un cliente registrado con el mismo CUIL o Email.");
		}
		Cliente clienteGuardado= clienteRepository.save(cliente);
		return clienteGuardado;
	}

	@Override
	public Cliente obtenerPorCuil(String Cuil) {
		// TODO Auto-generated method stub
		System.out.println("Buscando cliente por Cuil: "+ Cuil);
		return clienteRepository.findByCuil(Cuil).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con el Cuil: " + Cuil));
	}

	@Override
	public List<Cliente> obtenerPorNombre(String Nombre) {
		// TODO Auto-generated method stub
		System.out.println("Buscando Cliente por Nombre: "+ Nombre);
		List<Cliente> respuesta=clienteRepository.findByNombreRazonSocialContainingIgnoreCase(Nombre);
		if(respuesta.isEmpty()) {
			System.out.println("Cliente "+ Nombre + " no encontrado");
		}
		return respuesta;
	}

}
