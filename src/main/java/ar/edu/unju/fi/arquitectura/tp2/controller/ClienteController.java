package ar.edu.unju.fi.arquitectura.tp2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unju.fi.arquitectura.tp2.dto.ClienteRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.ClienteResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/apl/v1/Cuentas")
@RequiredArgsConstructor
public class ClienteController {
	
	private final ClienteService clienteService;
	
	@PostMapping
	public ResponseEntity<ClienteResponseDto> CrearCliente(@Valid @RequestBody ClienteRequestDto cliente){
		ClienteResponseDto nuevoCliente=clienteService.crearCliente(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
	}
	
	@GetMapping("/{Cuil}")
	public ResponseEntity<ClienteResponseDto> obtenerPorCuil(@PathVariable String cuil){
		ClienteResponseDto cliente=clienteService.obtenerPorCuil(cuil);
		return ResponseEntity.ok(cliente);
	}
	
	@GetMapping("/{Nombre}")
	public ResponseEntity<List<ClienteResponseDto>> obtenerPorNombre(@PathVariable String nombre){
		List<ClienteResponseDto> clientes=clienteService.obtenerPorNombre(nombre);
		return ResponseEntity.ok(clientes);
	}

}
