package com.colegio.inventario.application.mapper.equipo;

import com.colegio.inventario.application.dto.equipo.RolEquipoDTO;
import com.colegio.inventario.domain.catalogo.equipo.RolEquipo;

public class RolEquipoMapper {

    public static RolEquipoDTO toDTO(RolEquipo d) {
        if (d == null) return null;
        return new RolEquipoDTO(
                d.getId(),
                d.getNombre(),
                d.getDescripcion()
        );
    }
}
