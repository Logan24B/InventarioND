package com.colegio.inventario.application.service.asignacion;

import com.colegio.inventario.domain.asignacion.Kit;
import com.colegio.inventario.domain.repository.asignacion.KitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class KitService {

    private final KitRepository repository;

    public KitService(KitRepository repository) {
        this.repository = repository;
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
        if (repository.existsByOrdenadorId(kit.getOrdenador().getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este ordenador ya tiene un kit asignado");
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
}
