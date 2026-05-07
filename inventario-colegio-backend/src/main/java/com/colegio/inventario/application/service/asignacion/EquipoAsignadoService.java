package com.colegio.inventario.application.service.asignacion;

import com.colegio.inventario.domain.asignacion.EquipoAsignado;
import com.colegio.inventario.domain.catalogo.ubicacion.Departamento;
import com.colegio.inventario.domain.equipo.Ordenador;
import com.colegio.inventario.domain.personal.Empleado;
import com.colegio.inventario.domain.repository.asignacion.EquipoAsignadoRepository;
import com.colegio.inventario.domain.repository.catalogo.ubicacion.DepartamentoRepository;
import com.colegio.inventario.domain.repository.equipo.OrdenadorRepository;
import com.colegio.inventario.domain.repository.personal.EmpleadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EquipoAsignadoService {

    private final EquipoAsignadoRepository repository;
    private final OrdenadorRepository ordenadorRepository;
    private final DepartamentoRepository departamentoRepository;
    private final EmpleadoRepository empleadoRepository;

    public EquipoAsignadoService(
            EquipoAsignadoRepository repository,
            OrdenadorRepository ordenadorRepository,
            DepartamentoRepository departamentoRepository,
            EmpleadoRepository empleadoRepository) {
        this.repository = repository;
        this.ordenadorRepository = ordenadorRepository;
        this.departamentoRepository = departamentoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    @Transactional(readOnly = true)
    public List<EquipoAsignado> listar() {
        return repository.findByEstadoTrue();
    }

    @Transactional(readOnly = true)
    public EquipoAsignado obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Equipo asignado no encontrado con id: " + id));
    }

    @Transactional
    public EquipoAsignado guardar(EquipoAsignado asignacion) {
        aplicarRelaciones(asignacion);
        validarOrdenadorDisponible(null, asignacion);

        if (asignacion.getEstado() == null) {
            asignacion.setEstado(true);
        }

        return repository.save(asignacion);
    }

    @Transactional
    public EquipoAsignado actualizar(Long id, EquipoAsignado datos) {
        EquipoAsignado actual = obtenerPorId(id);

        if (datos.getOrdenador() != null) actual.setOrdenador(datos.getOrdenador());
        if (datos.getDepartamento() != null) actual.setDepartamento(datos.getDepartamento());
        if (datos.getUsuario() != null) actual.setUsuario(datos.getUsuario());
        if (datos.getEstado() != null) actual.setEstado(datos.getEstado());
        if (datos.getFecha() != null) actual.setFecha(datos.getFecha());
        if (datos.getObservaciones() != null) actual.setObservaciones(datos.getObservaciones());

        aplicarRelaciones(actual);
        validarOrdenadorDisponible(id, actual);

        return repository.save(actual);
    }

    @Transactional
    public void eliminar(Long id) {
        EquipoAsignado actual = obtenerPorId(id);
        actual.setEstado(false);
        repository.save(actual);
    }

    @Transactional
    public EquipoAsignado cambiarEstado(Long id, Boolean estado) {
        if (estado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado es obligatorio");
        }
        EquipoAsignado actual = obtenerPorId(id);
        actual.setEstado(estado);
        validarOrdenadorDisponible(id, actual);
        return repository.save(actual);
    }

    private void aplicarRelaciones(EquipoAsignado asignacion) {
        if (asignacion.getOrdenador() == null || asignacion.getOrdenador().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ordenador es obligatorio");
        }
        if (asignacion.getDepartamento() == null || asignacion.getDepartamento().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El departamento es obligatorio");
        }
        if (asignacion.getUsuario() == null || asignacion.getUsuario().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario es obligatorio");
        }

        Ordenador ordenador = ordenadorRepository.findById(asignacion.getOrdenador().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ordenador no existe"));
        if (Boolean.FALSE.equals(ordenador.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede asignar un ordenador inactivo");
        }

        Departamento departamento = departamentoRepository.findById(asignacion.getDepartamento().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El departamento no existe"));
        if (Boolean.FALSE.equals(departamento.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede asignar a un departamento inactivo");
        }

        Empleado usuario = empleadoRepository.findById(asignacion.getUsuario().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no existe"));
        if (Boolean.FALSE.equals(usuario.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede asignar a un usuario inactivo");
        }

        asignacion.setOrdenador(ordenador);
        asignacion.setDepartamento(departamento);
        asignacion.setUsuario(usuario);
    }

    private void validarOrdenadorDisponible(Long idActual, EquipoAsignado asignacion) {
        if (Boolean.FALSE.equals(asignacion.getEstado())) {
            return;
        }
        boolean ordenadorAsignado = repository.existsByOrdenadorIdAndEstadoTrue(asignacion.getOrdenador().getId());
        if (ordenadorAsignado && (idActual == null || !repository.findById(idActual)
                .map(actual -> actual.getOrdenador().getId().equals(asignacion.getOrdenador().getId()))
                .orElse(false))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Este ordenador ya tiene una asignacion activa");
        }
    }
}
