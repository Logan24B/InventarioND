package com.colegio.inventario.application.service.asignacion;

import com.colegio.inventario.domain.asignacion.Kit;
import com.colegio.inventario.domain.equipo.Ordenador;
import com.colegio.inventario.domain.repository.asignacion.KitRepository;
import com.colegio.inventario.domain.repository.equipo.OrdenadorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class KitService {

    private final KitRepository repository;
    private final OrdenadorRepository ordenadorRepository;

    public KitService(KitRepository repository, OrdenadorRepository ordenadorRepository) {
        this.repository = repository;
        this.ordenadorRepository = ordenadorRepository;
    }

    @Transactional(readOnly = true)
    public List<Kit> listar() {
        return repository.findByEstadoTrue();
    }

    @Transactional(readOnly = true)
    public Kit obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kit no encontrado con id: " + id));
    }

    @Transactional
    public Kit guardar(Kit kit) {
        if (kit.getOrdenador() == null || kit.getOrdenador().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ordenador es obligatorio");
        }
        Ordenador ordenador = ordenadorRepository.findById(kit.getOrdenador().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ordenador no existe"));
        if (Boolean.FALSE.equals(ordenador.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede crear un kit con un ordenador inactivo");
        }
        if (repository.existsByOrdenadorIdAndEstadoTrue(ordenador.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este ordenador ya tiene un kit asignado");
        }
        kit.setOrdenador(ordenador);
        if (kit.getEstado() == null) {
            kit.setEstado(true);
        }
        if (kit.getDetalles() != null) {
            kit.getDetalles().forEach(detalle -> detalle.setKit(kit));
        }
        return repository.save(kit);
    }

    @Transactional
    public Kit actualizar(Long id, Kit datos) {
        Kit actual = obtenerPorId(id);

        if (datos.getEstado() != null) actual.setEstado(datos.getEstado());
        if (datos.getObservaciones() != null) actual.setObservaciones(datos.getObservaciones());

        return repository.save(actual);
    }

    @Transactional
    public void eliminar(Long id) {
        Kit actual = obtenerPorId(id);
        actual.setEstado(false);
        repository.save(actual);
    }

    @Transactional
    public Kit cambiarEstado(Long id, Boolean estado) {
        if (estado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado es obligatorio");
        }
        Kit actual = obtenerPorId(id);
        actual.setEstado(estado);
        return repository.save(actual);
    }
}
