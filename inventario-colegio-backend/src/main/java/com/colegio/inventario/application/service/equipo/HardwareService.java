package com.colegio.inventario.application.service.equipo;

import com.colegio.inventario.domain.catalogo.equipo.Modelo;
import com.colegio.inventario.domain.catalogo.hardware.CategoriaHardware;
import com.colegio.inventario.domain.equipo.Hardware;
import com.colegio.inventario.domain.repository.catalogo.equipo.ModeloRepository;
import com.colegio.inventario.domain.repository.catalogo.hardware.CategoriaHardwareRepository;
import com.colegio.inventario.domain.repository.equipo.HardwareRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HardwareService {

    private final HardwareRepository repository;
    private final CategoriaHardwareRepository categoriaRepository;
    private final ModeloRepository modeloRepository;

    public HardwareService(
            HardwareRepository repository,
            CategoriaHardwareRepository categoriaRepository,
            ModeloRepository modeloRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.modeloRepository = modeloRepository;
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
        normalizar(hardware);
        validarSerialUnico(null, hardware.getSerialNumber());
        aplicarReferencias(hardware);

        if (hardware.getEstado() == null) {
            hardware.setEstado(true);
        }

        return repository.save(hardware);
    }

    @Transactional
    public Hardware actualizar(Long id, Hardware datos) {
        Hardware actual = obtenerPorId(id);

        if (datos.getSerialNumber() != null) actual.setSerialNumber(datos.getSerialNumber());
        if (datos.getCategoria() != null) actual.setCategoria(datos.getCategoria());
        if (datos.getModelo() != null) actual.setModelo(datos.getModelo());
        if (datos.getEstado() != null) actual.setEstado(datos.getEstado());
        if (datos.getFecha() != null) actual.setFecha(datos.getFecha());
        if (datos.getObservaciones() != null) actual.setObservaciones(datos.getObservaciones());

        normalizar(actual);
        validarSerialUnico(id, actual.getSerialNumber());
        aplicarReferencias(actual);

        return repository.save(actual);
    }

    @Transactional
    public void eliminar(Long id) {
        Hardware actual = obtenerPorId(id);
        actual.setEstado(false);
        repository.save(actual);
    }

    @Transactional
    public Hardware cambiarEstado(Long id, Boolean estado) {
        if (estado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado es obligatorio");
        }
        Hardware actual = obtenerPorId(id);
        actual.setEstado(estado);
        return repository.save(actual);
    }

    private void aplicarReferencias(Hardware hardware) {
        if (hardware.getCategoria() == null || hardware.getCategoria().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La categoria de hardware es obligatoria");
        }
        if (hardware.getModelo() == null || hardware.getModelo().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El modelo es obligatorio");
        }

        CategoriaHardware categoria = categoriaRepository.findById(hardware.getCategoria().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La categoria de hardware no existe"));
        Modelo modelo = modeloRepository.findById(hardware.getModelo().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El modelo no existe"));

        if (modelo.getCategoria() != null && !Objects.equals(modelo.getCategoria().getId(), categoria.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El modelo seleccionado no pertenece a la categoria indicada");
        }

        hardware.setCategoria(categoria);
        hardware.setModelo(modelo);
    }

    private void validarSerialUnico(Long idActual, String serialNumber) {
        if (serialNumber == null || serialNumber.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El numero de serie es obligatorio");
        }

        Optional<Hardware> existente = repository.findBySerialNumberIgnoreCase(serialNumber);
        if (existente.isPresent() && !Objects.equals(existente.get().getId(), idActual)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un hardware con ese numero de serie");
        }
    }

    private void normalizar(Hardware hardware) {
        hardware.setSerialNumber(limpiar(hardware.getSerialNumber()));
    }

    private String limpiar(String valor) {
        if (valor == null) {
            return null;
        }
        String limpio = valor.trim();
        return limpio.isEmpty() ? null : limpio;
    }
}
