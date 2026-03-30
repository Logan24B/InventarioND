package com.colegio.inventario.application.mapper.personal;

import com.colegio.inventario.application.dto.personal.CargoDTO;
import com.colegio.inventario.domain.personal.Cargo;

public class CargoMapper {

    public static CargoDTO toDTO(Cargo d) {
        if (d == null) return null;
        return new CargoDTO(
                d.getId(),
                d.getNombre(),
                d.getDescripcion()
        );
    }
}
