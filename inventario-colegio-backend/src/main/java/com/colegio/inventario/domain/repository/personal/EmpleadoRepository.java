package com.colegio.inventario.domain.repository.personal;

import com.colegio.inventario.domain.personal.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long>
{
    boolean existsByNombre1IgnoreCaseAndApellido1IgnoreCase(String nombre1, String apellido1);
}

