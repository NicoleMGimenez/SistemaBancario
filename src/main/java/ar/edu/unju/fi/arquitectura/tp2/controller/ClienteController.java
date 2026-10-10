package ar.edu.unju.fi.arquitectura.tp2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import ar.edu.unju.fi.arquitectura.tp2.dto.ClienteRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.ClienteResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.service.ClienteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

	private final ClienteService clienteService;

	@PostMapping
	public ResponseEntity<ClienteResponseDto> crearCliente(@Valid @RequestBody ClienteRequestDto request) {
		ClienteResponseDto nuevoCliente = clienteService.crearCliente(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
	}
}