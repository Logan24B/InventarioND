package com.colegio.inventario.domain.repository.equipo;

import com.colegio.inventario.domain.equipo.Hardware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HardwareRepository extends JpaRepository<Hardware, Long> {
    Optional<Hardware> findBySerialNumber(String serialNumber);
    List<Hardware> findByEstadoTrue();
}
