package com.colegio.inventario.domain.repository.personal;

import com.colegio.inventario.domain.personal.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long>
{
    List<Empleado> findByEstadoTrue();
    boolean existsByNombre1IgnoreCaseAndApellido1IgnoreCase(String nombre1, String apellido1);
}

