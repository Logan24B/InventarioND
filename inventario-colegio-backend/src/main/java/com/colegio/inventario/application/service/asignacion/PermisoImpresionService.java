package com.colegio.inventario.application.service.asignacion;

import com.colegio.inventario.domain.asignacion.PermisoImpresion;
import com.colegio.inventario.domain.equipo.Hardware;
import com.colegio.inventario.domain.equipo.Ordenador;
import com.colegio.inventario.domain.repository.asignacion.PermisoImpresionRepository;
import com.colegio.inventario.domain.repository.equipo.HardwareRepository;
import com.colegio.inventario.domain.repository.equipo.OrdenadorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PermisoImpresionService {

    private final PermisoImpresionRepository repository;
    private final OrdenadorRepository ordenadorRepository;
    private final HardwareRepository hardwareRepository;

    public PermisoImpresionService(
            PermisoImpresionRepository repository,
            OrdenadorRepository ordenadorRepository,
            HardwareRepository hardwareRepository) {
        this.repository = repository;
        this.ordenadorRepository = ordenadorRepository;
        this.hardwareRepository = hardwareRepository;
    }

    @Transactional(readOnly = true)
    public List<PermisoImpresion> listar() {
        return repository.findByEstadoTrue();
    }

    @Transactional(readOnly = true)
    public PermisoImpresion obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Permiso de impresion no encontrado con id: " + id));
    }

    @Transactional
    public PermisoImpresion guardar(PermisoImpresion permiso) {
        aplicarRelaciones(permiso);
        validarDuplicadoActivo(null, permiso);

        if (permiso.getEstado() == null) {
            permiso.setEstado(true);
        }
        if (Boolean.TRUE.equals(permiso.getEstado())) {
            permiso.setFechaBaja(null);
        } else if (permiso.getFechaBaja() == null) {
            permiso.setFechaBaja(LocalDateTime.now());
        }

        return repository.save(permiso);
    }

    @Transactional
    public PermisoImpresion actualizar(Long id, PermisoImpresion datos) {
        PermisoImpresion actual = obtenerPorId(id);

        if (datos.getOrdenador() != null) actual.setOrdenador(datos.getOrdenador());
        if (datos.getImpresora() != null) actual.setImpresora(datos.getImpresora());
        if (datos.getEstado() != null) actual.setEstado(datos.getEstado());
        if (datos.getFechaAlta() != null) actual.setFechaAlta(datos.getFechaAlta());
        if (datos.getFechaBaja() != null) actual.setFechaBaja(datos.getFechaBaja());
        if (datos.getObservaciones() != null) actual.setObservaciones(datos.getObservaciones());

        aplicarRelaciones(actual);
        validarDuplicadoActivo(id, actual);
        sincronizarFechaBaja(actual);

        return repository.save(actual);
    }

    @Transactional
    public void eliminar(Long id) {
        PermisoImpresion actual = obtenerPorId(id);
        actual.setEstado(false);
        actual.setFechaBaja(LocalDateTime.now());
        repository.save(actual);
    }

    @Transactional
    public PermisoImpresion cambiarEstado(Long id, Boolean estado) {
        if (estado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado es obligatorio");
        }
        PermisoImpresion actual = obtenerPorId(id);
        actual.setEstado(estado);
        sincronizarFechaBaja(actual);
        return repository.save(actual);
    }

    private void aplicarRelaciones(PermisoImpresion permiso) {
        if (permiso.getOrdenador() == null || permiso.getOrdenador().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ordenador es obligatorio");
        }
        if (permiso.getImpresora() == null || permiso.getImpresora().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La impresora es obligatoria");
        }

        Ordenador ordenador = ordenadorRepository.findById(permiso.getOrdenador().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ordenador no existe"));
        if (Boolean.FALSE.equals(ordenador.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede asignar permiso a un ordenador inactivo");
        }

        Hardware impresora = hardwareRepository.findById(permiso.getImpresora().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La impresora no existe"));
        if (Boolean.FALSE.equals(impresora.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede asignar una impresora inactiva");
        }

        permiso.setOrdenador(ordenador);
        permiso.setImpresora(impresora);
    }

    private void validarDuplicadoActivo(Long idActual, PermisoImpresion permiso) {
        if (Boolean.FALSE.equals(permiso.getEstado())) {
            return;
        }
        boolean duplicado = repository.existsByOrdenadorIdAndImpresoraIdAndEstadoTrue(
                permiso.getOrdenador().getId(), permiso.getImpresora().getId());
        if (duplicado && (idActual == null || !repository.findById(idActual)
                .map(actual -> actual.getOrdenador().getId().equals(permiso.getOrdenador().getId())
                        && actual.getImpresora().getId().equals(permiso.getImpresora().getId()))
                .orElse(false))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Este ordenador ya tiene permiso activo para esa impresora");
        }
    }

    private void sincronizarFechaBaja(PermisoImpresion permiso) {
        if (Boolean.TRUE.equals(permiso.getEstado())) {
            permiso.setFechaBaja(null);
        } else if (permiso.getFechaBaja() == null) {
            permiso.setFechaBaja(LocalDateTime.now());
        }
    }
}
