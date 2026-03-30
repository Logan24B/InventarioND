package com.colegio.inventario.application.dto.equipo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrdenadorDTO(
        Long id,
        String tipo,
        String marca,
        String modelo,
        String numeroSerie,
        String procesador,
        String ram,
        String rom,
        String so,
        String ofimatica,
        Boolean antivirus,
        String macAdress,
        Boolean dominio,
        String idNDIS,
        String hostName,
        LocalDate mantenimiento,
        LocalDate proximoMantenimiento,
        Boolean estado,
        String observaciones,
        LocalDateTime fecha
) {}
