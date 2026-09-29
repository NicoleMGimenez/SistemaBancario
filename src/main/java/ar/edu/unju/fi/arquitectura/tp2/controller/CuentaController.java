package ar.edu.unju.fi.arquitectura.tp2.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unju.fi.arquitectura.tp2.dto.CuentaResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.model.EstadoCuenta;
import ar.edu.unju.fi.arquitectura.tp2.service.CuentaFinancieraService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/apl/v1/clientes")
@RequiredArgsConstructor
public class CuentaController {
	
	private CuentaFinancieraService cuentaFinancieraService;
	
	@GetMapping("/{CBU}")
	public ResponseEntity<CuentaResponseDto> obtenerPorCbu(@PathVariable String cbu) {
		CuentaResponseDto cuenta = cuentaFinancieraService.obtenerPorCbu(cbu);
        return ResponseEntity.ok(cuenta);
	}
	
	@GetMapping("/{Estado}")
	public ResponseEntity<List<CuentaResponseDto>> buscarPorEstado(@PathVariable EstadoCuenta estado) {
		List<CuentaResponseDto> cuenta=cuentaFinancieraService.buscarPorEstado(estado);
        return ResponseEntity.ok(cuenta);
    }
	
	@GetMapping("/{Alias}")
	public ResponseEntity<CuentaResponseDto> obtenerPorAlias (@PathVariable String alias){
		CuentaResponseDto cuenta = cuentaFinancieraService.obtenerPorAlias(alias);
		return ResponseEntity.ofNullable(cuenta);
	}
}
