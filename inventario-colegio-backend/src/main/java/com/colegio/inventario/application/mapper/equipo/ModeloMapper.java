package com.colegio.inventario.application.mapper.equipo;

import com.colegio.inventario.application.dto.equipo.ModeloDTO;
import com.colegio.inventario.domain.catalogo.equipo.Modelo;

public class ModeloMapper {

    public static ModeloDTO toDTO(Modelo d) {
        if (d == null) return null;
        return new ModeloDTO(
                d.getId(),
                d.getMarca().getNombre(),
                d.getCategoria().getNombre(),
                d.getNombre(),
                d.getRom() != null ? String.valueOf(d.getRom().getTamano()) : null,
                d.getRam() != null ? d.getRam().getNombre() : null,
                d.getFecha(),
                d.getObservaciones()
        );
    }
}
