package com.colegio.inventario.application.service.personal;

import com.colegio.inventario.domain.personal.Empleado;
import com.colegio.inventario.domain.repository.personal.EmpleadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoRepository repo;

    public EmpleadoService(EmpleadoRepository repo) {
        this.repo = repo;
    }

    public List<Empleado> listar() {
        return repo.findAll();
    }

    @SuppressWarnings("null")
    public Empleado obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Empleado no encontrado con id: " + id
                        ));
    }

    public Empleado guardar(Empleado empleado) {

        if (empleado.getNombre1() == null || empleado.getNombre1().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El primer nombre es obligatorio");
        }

        if (empleado.getApellido1() == null || empleado.getApellido1().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El primer apellido es obligatorio");
        }

        if (empleado.getCargo() == null || empleado.getCargo().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cargo es obligatorio");
        }

        empleado.setNombre1(empleado.getNombre1().trim());
        if (empleado.getNombre2() != null) empleado.setNombre2(empleado.getNombre2().trim());
        empleado.setApellido1(empleado.getApellido1().trim());
        if (empleado.getApellido2() != null) empleado.setApellido2(empleado.getApellido2().trim());

        if (repo.existsByNombre1IgnoreCaseAndApellido1IgnoreCase(
                empleado.getNombre1(), empleado.getApellido1())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un empleado con ese nombre y apellido"
            );
        }

        return repo.save(empleado);
    }

    public Empleado actualizar(Long id, Empleado dto) {

        Empleado actual = obtenerPorId(id);

        if (dto.getNombre1() != null && !dto.getNombre1().trim().isEmpty()) {
            actual.setNombre1(dto.getNombre1().trim());
        }

        if (dto.getNombre2() != null) {
            actual.setNombre2(dto.getNombre2().trim());
        }

        if (dto.getApellido1() != null && !dto.getApellido1().trim().isEmpty()) {
            actual.setApellido1(dto.getApellido1().trim());
        }

        if (dto.getApellido2() != null) {
            actual.setApellido2(dto.getApellido2().trim());
        }

        if (dto.getFechaNacimiento() != null) {
            actual.setFechaNacimiento(dto.getFechaNacimiento());
        }

        if (dto.getCargo() != null && dto.getCargo().getId() != null) {
            actual.setCargo(dto.getCargo());
        }

        return repo.save(actual);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Empleado no encontrado con id: " + id
            );
        }
        repo.deleteById(id);
    }
}
