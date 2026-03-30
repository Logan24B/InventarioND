package com.colegio.inventario.application.mapper.hardware;

import com.colegio.inventario.application.dto.hardware.ProcesadorDTO;
import com.colegio.inventario.domain.catalogo.hardware.Procesador;

public class ProcesadorMapper {

    public static ProcesadorDTO toDTO(Procesador d) {
        if (d == null) return null;
        return new ProcesadorDTO(
                d.getId(),
                d.getNombre(),
                d.getDescripcion()
        );
    }
}
