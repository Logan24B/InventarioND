package com.colegio.inventario.application.mapper.hardware;

import com.colegio.inventario.application.dto.hardware.CategoriaOrdenadorDTO;
import com.colegio.inventario.domain.catalogo.hardware.CategoriaOrdenador;

public class CategoriaOrdenadorMapper {

    public static CategoriaOrdenadorDTO toDTO(CategoriaOrdenador d) {
        if (d == null) return null;
        return new CategoriaOrdenadorDTO(
                d.getId(),
                d.getNombre(),
                d.getDescripcion()
        );
    }
}
