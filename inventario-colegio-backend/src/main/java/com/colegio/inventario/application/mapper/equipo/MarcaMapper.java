package com.colegio.inventario.application.mapper.equipo;

import com.colegio.inventario.application.dto.equipo.MarcaDTO;
import com.colegio.inventario.domain.catalogo.equipo.Marca;

public class MarcaMapper {

    public static MarcaDTO toDTO(Marca d) {
        if (d == null) return null;
        return new MarcaDTO(
                d.getId(),
                d.getNombre(),
                d.getDescripcion()
        );
    }
}
