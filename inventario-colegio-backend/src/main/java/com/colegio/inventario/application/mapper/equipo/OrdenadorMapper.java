package com.colegio.inventario.application.mapper.equipo;

import com.colegio.inventario.application.dto.equipo.OrdenadorDTO;
import com.colegio.inventario.domain.equipo.Ordenador;

public class OrdenadorMapper {

    public static OrdenadorDTO toDTO(Ordenador d) {
        if (d == null) return null;
        return new OrdenadorDTO(
                d.getId(),
                d.getTipo() != null ? d.getTipo().getNombre() : null,
                d.getMarca() != null ? d.getMarca().getNombre() : null,
                d.getModelo() != null ? d.getModelo().getNombre() : null,
                d.getNumeroSerie(),
                d.getProcesador() != null ? d.getProcesador().getNombre() : null,
                d.getRam() != null ? d.getRam().getNombre() : null,
                d.getRom() != null ? "Tamano: " + d.getRom().getTamano() : null,
                d.getSo() != null ? d.getSo().getNombre() : null,
                d.getOfimatica() != null ? d.getOfimatica().getNombre() : null,
                d.getAntivirus(),
                d.getMacAdress(),
                d.getDominio(),
                d.getIdNDIS(),
                d.getHostName(),
                d.getMantenimiento(),
                d.getProximoMantenimiento(),
                d.getEstado(),
                d.getObservaciones(),
                d.getFecha()
        );
    }
}
