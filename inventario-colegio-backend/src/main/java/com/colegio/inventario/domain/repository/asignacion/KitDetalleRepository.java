package com.colegio.inventario.domain.repository.asignacion;

import com.colegio.inventario.domain.asignacion.KitDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitDetalleRepository extends JpaRepository<KitDetalle, Long> {
    List<KitDetalle> findByKitId(Long kitId);
    List<KitDetalle> findByKitIdAndEstadoTrue(Long kitId);
    boolean existsByHardwareId(Long hardwareId);
    boolean existsByHardwareIdAndEstadoTrue(Long hardwareId);
}
