package com.colegio.inventario.application.service.catalogo.ubicacion;

import com.colegio.inventario.application.dto.ubicacion.DepartamentoDTO;
import com.colegio.inventario.application.mapper.ubicacion.DepartamentoMapper;
import com.colegio.inventario.domain.catalogo.ubicacion.Departamento;
import com.colegio.inventario.domain.repository.catalogo.ubicacion.DepartamentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service // Marca la clase como servicio de negocio
public class DepartamentoService {

    private final DepartamentoRepository repository;

    // Inyección por constructor (recomendada)
    public DepartamentoService(DepartamentoRepository repository) {
        this.repository = repository;
    }

    // LISTAR TODOS=
    public List<Departamento> listar() {
        return repository.findByEstadoTrue();
    }

    // LISTAR DTO
    public List<DepartamentoDTO> listarDTO() {
        return repository.findByEstadoTrue()
                .stream()
                .map(DepartamentoMapper::toDTO)
                .toList();
    }

    // OBTENER POR ID
    public Departamento obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Departamento no encontrado con id: " + id));
    }

    // GUARDAR NUEVO
    public Departamento guardar(Departamento departamento) {

        // Validación básica
        if (departamento.getEdificio() == null ||
                departamento.getEdificio().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El edificio es obligatorio");
        }

        if (departamento.getSeccion() == null ||
                departamento.getSeccion().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La sección es obligatoria");
        }

        return repository.save(departamento);
    }

    // ACTUALIZAR
    public Departamento actualizar(Long id, Departamento datos) {

        Departamento actual = obtenerPorId(id);

        // Actualiza edificio si viene
        if (datos.getEdificio() != null &&
                datos.getEdificio().getId() != null) {
            actual.setEdificio(datos.getEdificio());
        }

        // Actualiza sección si viene
        if (datos.getSeccion() != null &&
                datos.getSeccion().getId() != null) {
            actual.setSeccion(datos.getSeccion());
        }

        // Actualiza descripción si viene
        if (datos.getDescripcion() != null) {
            actual.setDescripcion(datos.getDescripcion());
        }

        // Actualiza estado si viene
        if (datos.getEstado() != null) {
            actual.setEstado(datos.getEstado());
        }

        return repository.save(actual);
    }

    public void eliminar(Long id) {

        Departamento departamento = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Departamento no encontrado con id: " + id));

        departamento.setEstado(false);

        repository.save(departamento);
    }

    public Departamento reactivar(Long id) {

        Departamento departamento = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Departamento no encontrado con id: " + id));

        departamento.setEstado(true);

        return repository.save(departamento);
    }

    public Departamento cambiarEstado(Long id, Boolean estado) {
        if (estado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado es obligatorio");
        }
        Departamento departamento = obtenerPorId(id);
        departamento.setEstado(estado);
        return repository.save(departamento);
    }

    public Page<Departamento> listarPaginado(Pageable pageable) {
        return repository.findByEstadoTrue(pageable);
    }
}
