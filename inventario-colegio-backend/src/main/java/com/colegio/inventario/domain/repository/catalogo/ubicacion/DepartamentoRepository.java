package com.colegio.inventario.domain.repository.catalogo.ubicacion;

import com.colegio.inventario.domain.catalogo.ubicacion.Departamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long>
{
    List<Departamento> findByEstadoTrue();
    Page<Departamento> findByEstadoTrue(Pageable pageable);
}

