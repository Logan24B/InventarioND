package com.colegio.inventario.application.service.personal;

import com.colegio.inventario.domain.personal.Empleado;
import com.colegio.inventario.domain.personal.Cargo;
import com.colegio.inventario.domain.repository.personal.CargoRepository;
import com.colegio.inventario.domain.repository.personal.EmpleadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoRepository repo;
    private final CargoRepository cargoRepository;

    public EmpleadoService(EmpleadoRepository repo, CargoRepository cargoRepository) {
        this.repo = repo;
        this.cargoRepository = cargoRepository;
    }

    public List<Empleado> listar() {
        return repo.findByEstadoTrue();
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

        normalizar(empleado);
        empleado.setCargo(obtenerCargoActivo(empleado.getCargo().getId()));
        if (empleado.getEstado() == null) {
            empleado.setEstado(true);
        }

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
            actual.setCargo(obtenerCargoActivo(dto.getCargo().getId()));
        }

        if (dto.getEstado() != null) {
            actual.setEstado(dto.getEstado());
        }

        return repo.save(actual);
    }

    public void eliminar(Long id) {
        Empleado actual = obtenerPorId(id);
        actual.setEstado(false);
        repo.save(actual);
    }

    public Empleado cambiarEstado(Long id, Boolean estado) {
        if (estado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado es obligatorio");
        }
        Empleado actual = obtenerPorId(id);
        actual.setEstado(estado);
        return repo.save(actual);
    }

    private Cargo obtenerCargoActivo(Long id) {
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cargo no existe"));
        if (Boolean.FALSE.equals(cargo.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede asignar un cargo inactivo");
        }
        return cargo;
    }

    private void normalizar(Empleado empleado) {
        empleado.setNombre1(empleado.getNombre1().trim());
        if (empleado.getNombre2() != null) empleado.setNombre2(empleado.getNombre2().trim());
        empleado.setApellido1(empleado.getApellido1().trim());
        if (empleado.getApellido2() != null) empleado.setApellido2(empleado.getApellido2().trim());
    }
}
