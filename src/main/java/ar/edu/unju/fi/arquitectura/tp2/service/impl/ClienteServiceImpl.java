package ar.edu.unju.fi.arquitectura.tp2.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import ar.edu.unju.fi.arquitectura.tp2.event.ClienteRegistradoEvent;
import ar.edu.unju.fi.arquitectura.tp2.model.*;
import ar.edu.unju.fi.arquitectura.tp2.repository.TokenActivacionRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.arquitectura.tp2.dto.ClienteRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.ClienteResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.exception.RecursoNoEncontradoException;
import ar.edu.unju.fi.arquitectura.tp2.repository.ClienteRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService{
	
	private final ClienteRepository clienteRepository;
	private final TokenActivacionRepository tokenRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public ClienteResponseDto crearCliente(ClienteRequestDto request) {
		// 1. Instanciar la entidad en estado PENDIENTE_ACTIVACION
		Cliente cliente = Cliente.builder()
				.nombreRazonSocial(request.getNombreRazonSocial())
				.cuil(request.getCuil())
				.email(request.getEmail())
				.telefono(request.getTelefono())
				.direccion(request.getDireccion())
				.estado(EstadoCliente.PENDIENTE_ACTIVACION)
				.rolFamiliar(RolFamiliar.TITULAR)
				.build();

		Cliente clienteGuardado = clienteRepository.save(cliente);

		// 2. Generar Token UUID con vigencia de 24 horas
		String tokenStr = UUID.randomUUID().toString();
		TokenActivacion token = TokenActivacion.builder()
				.token(tokenStr)
				.cliente(clienteGuardado)
				.fechaExpiracion(LocalDateTime.now().plusHours(24))
				.build();
		tokenRepository.save(token);

		// 3. Publicar evento asíncrono de dominio
		eventPublisher.publishEvent(new ClienteRegistradoEvent(
				clienteGuardado.getEmail(),
				clienteGuardado.getNombreRazonSocial(),
				tokenStr
		));

		return convertirAResponseDto(clienteGuardado);
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

	@Transactional
	@Override
	public String activarClientePorToken(String tokenStr) {
		TokenActivacion token = tokenRepository.findByToken(tokenStr)
				.orElseThrow(() -> new IllegalArgumentException("El token de activación no existe"));

		if (token.estaUtilizado()) {
			throw new IllegalArgumentException("El token ya ha sido utilizado previamente");
		}

		if (token.estaExpirado()) {
			throw new IllegalArgumentException("El token ha expirado. Solicite un nuevo enlace de activación");
		}

		// Marcar token como utilizado
		token.setFechaUtilizacion(LocalDateTime.now());
		tokenRepository.save(token);

		// Activar al cliente
		Cliente cliente = token.getCliente();
		cliente.setEstado(EstadoCliente.ACTIVO);
		clienteRepository.save(cliente);

		log.info("Cliente {} (ID: {}) activado exitosamente.", cliente.getNombreRazonSocial(), cliente.getId());
		return "Cuenta activada exitosamente para " + cliente.getNombreRazonSocial();
	}

}
