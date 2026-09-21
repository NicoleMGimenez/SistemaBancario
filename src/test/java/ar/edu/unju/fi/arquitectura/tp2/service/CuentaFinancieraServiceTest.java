package ar.edu.unju.fi.arquitectura.tp2.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.unju.fi.arquitectura.tp2.repository.CuentaFinancieraRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.impl.CuentaFinancieraServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CuentaFinancieraServiceTest {
	
	@Mock
	private CuentaFinancieraRepository cuentaFinancieraRepository;
	
	@InjectMocks
	private CuentaFinancieraServiceImpl cuentaFinancieraServiceImpl;

}
