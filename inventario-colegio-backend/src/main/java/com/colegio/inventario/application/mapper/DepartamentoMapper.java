package com.colegio.inventario.application.mapper;

import com.colegio.inventario.application.dto.DepartamentoDTO;
import com.colegio.inventario.domain.catalogo.ubicacion.Departamento;

public class DepartamentoMapper {

    public static DepartamentoDTO toDTO(Departamento d) {
        return new DepartamentoDTO(
                d.getId(),
                d.getEdificio().getNombre(),
                d.getSeccion().getNombre(),
                d.getEstado(),
                d.getFechaCreacion(),
                d.getDescripcion());
    }
}
