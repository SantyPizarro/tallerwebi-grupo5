package com.tallerwebi.config;
import com.tallerwebi.dominio.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private PlanService planService;

    @Autowired
    public SchedulerConfig(PlanService planService) {
        this.planService = planService;
    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void verificarYActualizarPlanesExpirados() {
        planService.verificarYActualizarPlanesExpirados();
    }
}

