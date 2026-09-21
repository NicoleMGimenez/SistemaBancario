package ar.edu.unju.fi.arquitectura.tp2.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.unju.fi.arquitectura.tp2.repository.TransaccionRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.impl.TransaccionServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TransaccionServiceTest {
	
	@Mock
	private TransaccionRepository transaccionRepository;
	
	@InjectMocks
	private TransaccionServiceImpl transaccionServiceImpl;

}
