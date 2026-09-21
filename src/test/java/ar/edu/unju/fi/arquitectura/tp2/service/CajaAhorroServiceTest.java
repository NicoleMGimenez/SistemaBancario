package ar.edu.unju.fi.arquitectura.tp2.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.unju.fi.arquitectura.tp2.repository.CajaAhorroRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.impl.CajaAhorroServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CajaAhorroServiceTest {
	
	@Mock
	private CajaAhorroRepository cajaAhorroRepository;
	
	@InjectMocks
	private CajaAhorroServiceImpl cajaAhorroServiceImpl;

}
