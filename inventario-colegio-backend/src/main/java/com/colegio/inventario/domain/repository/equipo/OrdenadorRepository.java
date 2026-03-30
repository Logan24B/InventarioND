package com.colegio.inventario.domain.repository.equipo;

import com.colegio.inventario.domain.equipo.Ordenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenadorRepository extends JpaRepository<Ordenador, Long> {
}
