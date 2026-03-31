package com.colegio.inventario.application.service.asignacion;

import com.colegio.inventario.domain.asignacion.KitDetalle;
import com.colegio.inventario.domain.repository.asignacion.KitDetalleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class KitDetalleService {

    private final KitDetalleRepository repository;

    public KitDetalleService(KitDetalleRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<KitDetalle> listarPorKit(Long kitId) {
        return repository.findByKitId(kitId);
    }

    @Transactional(readOnly = true)
    public KitDetalle obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "KitDetalle no encontrado con id: " + id));
    }

    @Transactional
    public KitDetalle guardar(KitDetalle detalle) {
        if (detalle.getKit() == null || detalle.getKit().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El kit es obligatorio");
        }
        if (detalle.getHardware() == null || detalle.getHardware().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El hardware es obligatorio");
        }
        if (detalle.getRol() == null || detalle.getRol().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rol del equipo es obligatorio");
        }
        if (repository.existsByHardwareId(detalle.getHardware().getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este hardware ya está asignado a otro kit");
        }
        return repository.save(detalle);
    }

    @Transactional
    public KitDetalle actualizar(Long id, KitDetalle datos) {
        KitDetalle actual = obtenerPorId(id);

        if (datos.getRol() != null) actual.setRol(datos.getRol());
        if (datos.getEstado() != null) actual.setEstado(datos.getEstado());
        if (datos.getFecha() != null) actual.setFecha(datos.getFecha());
        if (datos.getObservaciones() != null) actual.setObservaciones(datos.getObservaciones());

        return repository.save(actual);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "KitDetalle no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }
}
