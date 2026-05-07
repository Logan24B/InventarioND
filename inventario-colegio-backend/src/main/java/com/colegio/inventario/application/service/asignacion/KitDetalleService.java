package com.colegio.inventario.application.service.asignacion;

import com.colegio.inventario.domain.asignacion.Kit;
import com.colegio.inventario.domain.asignacion.KitDetalle;
import com.colegio.inventario.domain.catalogo.equipo.RolEquipo;
import com.colegio.inventario.domain.equipo.Hardware;
import com.colegio.inventario.domain.repository.asignacion.KitDetalleRepository;
import com.colegio.inventario.domain.repository.asignacion.KitRepository;
import com.colegio.inventario.domain.repository.catalogo.equipo.RolEquipoRepository;
import com.colegio.inventario.domain.repository.equipo.HardwareRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class KitDetalleService {

    private final KitDetalleRepository repository;
    private final KitRepository kitRepository;
    private final HardwareRepository hardwareRepository;
    private final RolEquipoRepository rolRepository;

    public KitDetalleService(
            KitDetalleRepository repository,
            KitRepository kitRepository,
            HardwareRepository hardwareRepository,
            RolEquipoRepository rolRepository) {
        this.repository = repository;
        this.kitRepository = kitRepository;
        this.hardwareRepository = hardwareRepository;
        this.rolRepository = rolRepository;
    }

    @Transactional(readOnly = true)
    public List<KitDetalle> listarPorKit(Long kitId) {
        return repository.findByKitIdAndEstadoTrue(kitId);
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

        Kit kit = kitRepository.findById(detalle.getKit().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El kit no existe"));
        if (Boolean.FALSE.equals(kit.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede agregar hardware a un kit inactivo");
        }

        Hardware hardware = hardwareRepository.findById(detalle.getHardware().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El hardware no existe"));
        if (Boolean.FALSE.equals(hardware.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede asignar hardware inactivo");
        }

        RolEquipo rol = rolRepository.findById(detalle.getRol().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rol del equipo no existe"));

        if (repository.existsByHardwareIdAndEstadoTrue(hardware.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este hardware ya esta asignado a otro kit activo");
        }

        if (detalle.getEstado() == null) {
            detalle.setEstado(true);
        }
        detalle.setKit(kit);
        detalle.setHardware(hardware);
        detalle.setRol(rol);

        return repository.save(detalle);
    }

    @Transactional
    public KitDetalle actualizar(Long id, KitDetalle datos) {
        KitDetalle actual = obtenerPorId(id);

        if (datos.getRol() != null && datos.getRol().getId() != null) {
            RolEquipo rol = rolRepository.findById(datos.getRol().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rol del equipo no existe"));
            actual.setRol(rol);
        }
        if (datos.getEstado() != null) actual.setEstado(datos.getEstado());
        if (datos.getFecha() != null) actual.setFecha(datos.getFecha());
        if (datos.getObservaciones() != null) actual.setObservaciones(datos.getObservaciones());

        return repository.save(actual);
    }

    @Transactional
    public void eliminar(Long id) {
        KitDetalle actual = obtenerPorId(id);
        actual.setEstado(false);
        repository.save(actual);
    }

    @Transactional
    public KitDetalle cambiarEstado(Long id, Boolean estado) {
        if (estado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado es obligatorio");
        }
        KitDetalle actual = obtenerPorId(id);
        actual.setEstado(estado);
        return repository.save(actual);
    }
}
