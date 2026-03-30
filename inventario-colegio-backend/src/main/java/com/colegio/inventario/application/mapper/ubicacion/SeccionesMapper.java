package com.colegio.inventario.application.mapper.ubicacion;

import com.colegio.inventario.application.dto.ubicacion.SeccionesDTO;
import com.colegio.inventario.domain.catalogo.ubicacion.Secciones;

public class SeccionesMapper {

    public static SeccionesDTO toDTO(Secciones d) {
        if (d == null) return null;
        return new SeccionesDTO(
                d.getId(),
                d.getNombre(),
                d.getDescripcion(),
                d.getEstado(),
                d.getFechaCreacion()
        );
    }
}
