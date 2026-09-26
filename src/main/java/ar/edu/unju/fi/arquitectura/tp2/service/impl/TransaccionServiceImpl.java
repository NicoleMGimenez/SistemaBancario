package ar.edu.unju.fi.arquitectura.tp2.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.arquitectura.tp2.dto.TransaccionRequestDto;
import ar.edu.unju.fi.arquitectura.tp2.dto.TransaccionResponseDto;
import ar.edu.unju.fi.arquitectura.tp2.model.Transaccion;
import ar.edu.unju.fi.arquitectura.tp2.repository.TransaccionRepository;
import ar.edu.unju.fi.arquitectura.tp2.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransaccionServiceImpl implements TransaccionService {

	private final TransaccionRepository transaccionRepository;

    @Override
    @Transactional
    public TransaccionResponseDto crearTransaccion(TransaccionRequestDto request) {

        log.info("Persistiendo transacción de tipo: {} por un monto de: {}", request.getTipo(), request.getMonto());

        Transaccion transaccion = Transaccion.builder()
                .fechaHora(request.getFechaHora())
                .monto(request.getMonto())
                .tipo(request.getTipo())
                .estado(request.getEstado())
                .build();

        Transaccion transaccionGuardada = transaccionRepository.save(transaccion);

        return TransaccionResponseDto.builder()
                .id(transaccionGuardada.getId())
                .fechaHora(transaccionGuardada.getFechaHora())
                .monto(transaccionGuardada.getMonto())
                .tipo(transaccionGuardada.getTipo())
                .estado(transaccionGuardada.getEstado())
                .build();
    }
}