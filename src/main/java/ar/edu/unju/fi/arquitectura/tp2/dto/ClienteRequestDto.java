package ar.edu.unju.fi.arquitectura.tp2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDto {
	
	@NotBlank
	@Size(max = 150)
    private String nombreRazonSocial;
	
	@NotBlank
	@Size(max = 11)
    private String cuil;
	
	@NotBlank
	@Size(max = 100)
	@Email
    private String email;
	
	@Size(max=20)
    private String telefono;
	
	@Size(max=200)
    private String direccion;

}
