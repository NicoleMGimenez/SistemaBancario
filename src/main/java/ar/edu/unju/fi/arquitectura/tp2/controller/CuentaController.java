package ar.edu.unju.fi.arquitectura.tp2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import ar.edu.unju.fi.arquitectura.tp2.dto.CuentaRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.CuentaResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;
import ar.edu.unju.fi.arquitectura.tp2.service.CuentaFinancieraService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/apl/v1/cuentas")
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

	@GetMapping("/estado/{estado}") // Ajusté la ruta para que no colisione con {cbu} o {alias}
	public ResponseEntity<List<CuentaResponseDto>> buscarPorEstado(@PathVariable EstadoCuenta estado) {
		List<CuentaResponseDto> cuentas = cuentaFinancieraService.buscarPorEstado(estado);
		return ResponseEntity.ok(cuentas);
	}

	@GetMapping("/alias/{alias}")
	public ResponseEntity<CuentaResponseDto> obtenerPorAlias (@PathVariable String alias){
		CuentaResponseDto cuenta = cuentaFinancieraService.obtenerPorAlias(alias);
		return ResponseEntity.ok(cuenta); // Se ajustó ofNullable a ok para mantener consistencia con el manejo de excepciones
	}
}