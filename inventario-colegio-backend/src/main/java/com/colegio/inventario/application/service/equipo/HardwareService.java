package com.colegio.inventario.application.service.equipo;

import com.colegio.inventario.domain.equipo.Hardware;
import com.colegio.inventario.domain.repository.equipo.HardwareRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class HardwareService {

    private final HardwareRepository repository;

    public HardwareService(HardwareRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Hardware> listar() {
        return repository.findByEstadoTrue();
    }

    @Transactional(readOnly = true)
    public Hardware obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hardware no encontrado con id: " + id));
    }

    @Transactional
    public Hardware guardar(Hardware hardware) {
        if (hardware.getSerialNumber() == null || hardware.getSerialNumber().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El número de serie es obligatorio");
        }
        if (repository.findBySerialNumber(hardware.getSerialNumber()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un hardware con ese número de serie");
        }
        return repository.save(hardware);
    }

    @Transactional
    public Hardware actualizar(Long id, Hardware datos) {
        Hardware actual = obtenerPorId(id);

        if (datos.getSerialNumber() != null && !datos.getSerialNumber().isBlank()) {
            boolean serialCambiado = !actual.getSerialNumber().equalsIgnoreCase(datos.getSerialNumber());
            if (serialCambiado && repository.findBySerialNumber(datos.getSerialNumber()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ese número de serie ya está registrado");
            }
            actual.setSerialNumber(datos.getSerialNumber());
        }
        if (datos.getCategoria() != null) actual.setCategoria(datos.getCategoria());
        if (datos.getModelo() != null) actual.setModelo(datos.getModelo());
        if (datos.getEstado() != null) actual.setEstado(datos.getEstado());
        if (datos.getFecha() != null) actual.setFecha(datos.getFecha());
        if (datos.getObservaciones() != null) actual.setObservaciones(datos.getObservaciones());

        return repository.save(actual);
    }

    @Transactional
    public void eliminar(Long id) {
        Hardware actual = obtenerPorId(id);
        actual.setEstado(false);
        repository.save(actual);
    }
}
