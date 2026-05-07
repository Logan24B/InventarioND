package com.colegio.inventario.domain.repository.asignacion;

import com.colegio.inventario.domain.asignacion.EquipoAsignado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipoAsignadoRepository extends JpaRepository<EquipoAsignado, Long> {
    List<EquipoAsignado> findByEstadoTrue();
    boolean existsByOrdenadorIdAndEstadoTrue(Long ordenadorId);
}
