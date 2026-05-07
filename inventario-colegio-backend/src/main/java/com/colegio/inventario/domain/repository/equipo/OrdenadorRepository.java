package com.colegio.inventario.domain.repository.equipo;

import com.colegio.inventario.domain.equipo.Ordenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenadorRepository extends JpaRepository<Ordenador, Long> {
    List<Ordenador> findByEstadoTrue();

    Optional<Ordenador> findByNumeroSerieIgnoreCase(String numeroSerie);

    Optional<Ordenador> findByMacAdressIgnoreCase(String macAdress);

    Optional<Ordenador> findByIdNDISIgnoreCase(String idNDIS);

    Optional<Ordenador> findByHostNameIgnoreCase(String hostName);
}
