package ar.edu.unju.fi.arquitectura.tp2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unju.fi.arquitectura.tp2.dto.TransaccionRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.TransaccionResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.service.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/apl/v1/Transacciones")
@RequiredArgsConstructor
public class TransaccionController {
	
	private TransaccionService transaccionService;
	
	@PostMapping
	public ResponseEntity<TransaccionResponseDto> crearTransaccion(@Valid @RequestBody TransaccionRequestDto transaccion){
		TransaccionResponseDto transaccionRequest = transaccionService.crearTransaccion(transaccion);
		return ResponseEntity.status(HttpStatus.CREATED).body(transaccionRequest);
		
	}

}
