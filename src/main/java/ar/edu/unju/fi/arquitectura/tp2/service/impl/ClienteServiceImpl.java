package ar.edu.unju.fi.arquitectura.tp2.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ar.edu.unju.fi.arquitectura.tp2.dto.ClienteRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.ClienteResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.CuentaResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.exception.RecursoNoEncontradoException;
import ar.edu.unju.fi.arquitectura.tp2.model.Cliente;
import ar.edu.unju.fi.arquitectura.tp2.model.CuentaFinanciera;
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
	public ClienteResponseDto crearCliente(ClienteRequestDto request) {

	    log.info("Iniciando proceso de creación de cliente con CUIL: {}", request.getCuil());

	    if (clienteRepository.existsByCuil(request.getCuil())) {
	        log.info("Fallo en la creación, ya existe un cliente con CUIL: {}", request.getCuil());

	        throw new IllegalArgumentException(
	                "Ya existe un cliente registrado con el mismo CUIL o Email."
	        );
	    }

	    Cliente cliente = Cliente.builder()
	            .nombreRazonSocial(request.getNombreRazonSocial())
	            .cuil(request.getCuil())
	            .email(request.getEmail())
	            .telefono(request.getTelefono())
	            .direccion(request.getDireccion())
	            .build();

	    Cliente clienteGuardado = clienteRepository.save(cliente);

	    return ClienteResponseDto.builder()
	            .id(clienteGuardado.getId())
	            .nombreRazonSocial(clienteGuardado.getNombreRazonSocial())
	            .cuil(clienteGuardado.getCuil())
	            .email(clienteGuardado.getEmail())
	            .telefono(clienteGuardado.getTelefono())
	            .direccion(clienteGuardado.getDireccion())
	            .build();
	}

	@Override
	public ClienteResponseDto obtenerPorCuil(String cuil) {

	    log.info("Buscando cliente por CUIL: {}", cuil);

	    Cliente cliente = clienteRepository.findByCuil(cuil)
	            .orElseThrow(() ->
	                    new RecursoNoEncontradoException("Cliente no encontrado con el CUIL: " + cuil)
	            );
	    return ClienteResponseDto.builder()
	            .id(cliente.getId())
	            .nombreRazonSocial(cliente.getNombreRazonSocial())
	            .cuil(cliente.getCuil())
	            .email(cliente.getEmail())
	            .telefono(cliente.getTelefono())
	            .direccion(cliente.getDireccion())
	            .build();
	}

	@Override
	public List<ClienteResponseDto> obtenerPorNombre(String nombre) {

	    log.info("Buscando clientes por nombre: {}", nombre);

	    List<Cliente> clientes = clienteRepository.findByNombreRazonSocialContainingIgnoreCase(nombre);

	    if (clientes.isEmpty()) {
	        log.info("No se encontraron clientes con nombre: {}", nombre);
	    }

	    return clientes.stream()
                .map(this::convertirAResponseDto)
                .toList();
	}
	private ClienteResponseDto convertirAResponseDto(Cliente cliente) {

		 return ClienteResponseDto.builder()
        .id(cliente.getId())
        .nombreRazonSocial(cliente.getNombreRazonSocial())
        .cuil(cliente.getCuil())
        .email(cliente.getEmail())
        .telefono(cliente.getTelefono())
        .direccion(cliente.getDireccion())
        .build();
    }

}
