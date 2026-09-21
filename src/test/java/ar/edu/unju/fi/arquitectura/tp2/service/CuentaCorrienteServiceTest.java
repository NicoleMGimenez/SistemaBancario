package ar.edu.unju.fi.arquitectura.tp2.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.unju.fi.arquitectura.tp2.repository.CuentaCorrienteRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.impl.CuentaCorrienteServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CuentaCorrienteServiceTest {
	
	@Mock
	private CuentaCorrienteRepository cuentaCorrienteRepository;
	
	@InjectMocks
	private CuentaCorrienteServiceImpl cuentaCorrienteSeriviceImpl;

}
