package com.colegio.inventario.domain.repository.catalogo.hardware;

import com.colegio.inventario.domain.catalogo.hardware.Rom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RomRepository extends JpaRepository<Rom, Long> {
    List<Rom> findByEstadoTrue();
}
