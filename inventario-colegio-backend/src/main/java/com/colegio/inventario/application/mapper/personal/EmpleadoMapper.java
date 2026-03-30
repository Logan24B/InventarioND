package com.colegio.inventario.application.mapper.personal;

import com.colegio.inventario.application.dto.personal.EmpleadoDTO;
import com.colegio.inventario.domain.personal.Empleado;

public class EmpleadoMapper {

    public static EmpleadoDTO toDTO(Empleado d) {
        if (d == null)
            return null;
        return new EmpleadoDTO(
                d.getId(),
                d.getNombre1(),
                d.getNombre2(),
                d.getApellido1(),
                d.getApellido2(),
                d.getFechaNacimiento(),
                d.getCargo().getNombre());
    }
}
