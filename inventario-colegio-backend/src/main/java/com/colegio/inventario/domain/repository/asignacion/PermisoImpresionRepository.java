package com.colegio.inventario.domain.repository.asignacion;

import com.colegio.inventario.domain.asignacion.PermisoImpresion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermisoImpresionRepository extends JpaRepository<PermisoImpresion, Long> {
    List<PermisoImpresion> findByEstadoTrue();
    boolean existsByOrdenadorIdAndImpresoraIdAndEstadoTrue(Long ordenadorId, Long impresoraId);
}
