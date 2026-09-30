package ar.edu.unju.fi.arquitectura.tp2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import ar.edu.unju.fi.arquitectura.tp2.dto.TransferenciaRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.TransferenciaResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.service.TransaccionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

	private final TransaccionService transaccionService;

	@PostMapping("/transferir")
	public ResponseEntity<TransferenciaResponseDto> transferir(@Valid @RequestBody TransferenciaRequestDto request) {
		TransferenciaResponseDto resultado = transaccionService.realizarTransferencia(request);
		return ResponseEntity.ok(resultado);
	}
}