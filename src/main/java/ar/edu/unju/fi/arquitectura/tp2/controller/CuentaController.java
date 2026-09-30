package ar.edu.unju.fi.arquitectura.tp2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import ar.edu.unju.fi.arquitectura.tp2.dto.CuentaRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.CuentaResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.service.CuentaFinancieraService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cuentas")
@RequiredArgsConstructor
public class CuentaController {

	private final CuentaFinancieraService cuentaFinancieraService;

	@PostMapping
	public ResponseEntity<CuentaResponseDto> crearCuenta(@Valid @RequestBody CuentaRequestDto request) {
		CuentaResponseDto nuevaCuenta = cuentaFinancieraService.crearCuentaFinanciera(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCuenta);
	}

	@GetMapping("/{cbu}")
	public ResponseEntity<CuentaResponseDto> obtenerPorCbu(@PathVariable String cbu) {
		CuentaResponseDto cuenta = cuentaFinancieraService.obtenerPorCbu(cbu);
		return ResponseEntity.ok(cuenta);
	}
}