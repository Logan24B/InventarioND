package com.colegio.inventario.application.mapper.software;

import com.colegio.inventario.application.dto.software.OfimaticaDTO;
import com.colegio.inventario.domain.catalogo.software.Ofimatica;

public class OfimaticaMapper {

    public static OfimaticaDTO toDTO(Ofimatica d) {
        if (d == null) return null;
        return new OfimaticaDTO(
                d.getId(),
                d.getNombre(),
                d.getDescripcion()
        );
    }
}
