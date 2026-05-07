package com.colegio.inventario.domain.repository.catalogo.equipo;

import com.colegio.inventario.domain.catalogo.equipo.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    List<Modelo> findByEstadoTrue();
}
