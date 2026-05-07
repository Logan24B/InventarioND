package com.colegio.inventario.domain.repository.catalogo.base;

import com.colegio.inventario.domain.catalogo.base.CatalogoBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CatalogoRepository<T extends CatalogoBase> extends JpaRepository<T, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
    List<T> findByEstadoTrue();
}
