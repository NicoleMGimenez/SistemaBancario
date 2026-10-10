package ar.edu.unju.fi.arquitectura.tp2.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ar.edu.unju.fi.arquitectura.tp2.service.LiquidacionComisionesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LiquidacionComisionesScheduler {

    private final LiquidacionComisionesService liquidacionService;

    // Se parametriza la expresión Cron desde application.yaml
    @Scheduled(cron = "${app.comisiones.cron:*/30 * * * * *}")
    public void ejecutarLiquidacionProgramada() {
        log.info("Disparador Cron activado: ejecutando tarea programada de comisiones...");
        try {
            liquidacionService.procesarLiquidacionMensual();
        } catch (Exception e) {
            log.error("Error durante la ejecución de la liquidación de comisiones: {}", e.getMessage(), e);
        }
    }
}
