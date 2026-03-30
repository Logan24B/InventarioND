package com.colegio.inventario.application.mapper.ubicacion;

import com.colegio.inventario.application.dto.ubicacion.EdificioDTO;
import com.colegio.inventario.domain.catalogo.ubicacion.Edificio;

public class EdificioMapper {

    public static EdificioDTO toDTO(Edificio d) {
        if (d == null) return null;
        return new EdificioDTO(
                d.getId(),
                d.getNombre(),
                d.getDescripcion(),
                d.getEstado(),
                d.getFechaCreacion()
        );
    }
}
