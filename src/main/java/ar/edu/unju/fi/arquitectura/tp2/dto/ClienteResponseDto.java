package ar.edu.unju.fi.arquitectura.tp2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDto {
	
	private Long id;
    private String nombreRazonSocial;
    private String cuil;
    private String email;
    private String telefono;
    private String direccion;
}
