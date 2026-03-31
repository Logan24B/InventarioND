package com.colegio.inventario.domain.repository.asignacion;

import com.colegio.inventario.domain.asignacion.Kit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitRepository extends JpaRepository<Kit, Long> {
    List<Kit> findByEstadoTrue();
    boolean existsByOrdenadorId(Long ordenadorId);
}
