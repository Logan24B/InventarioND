package com.colegio.inventario.application.service.catalogo.hardware;

import com.colegio.inventario.domain.catalogo.hardware.Rom;
import com.colegio.inventario.domain.catalogo.hardware.TipoRom;
import com.colegio.inventario.domain.repository.catalogo.hardware.RomRepository;
import com.colegio.inventario.domain.repository.catalogo.hardware.TipoRomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RomService {

    private final RomRepository repo;
    private final TipoRomRepository tipoRomRepository;

    public RomService(RomRepository repo, TipoRomRepository tipoRomRepository) {
        this.repo = repo;
        this.tipoRomRepository = tipoRomRepository;
    }

    public List<Rom> listar() {
        return repo.findByEstadoTrue();
    }

    public Rom obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Rom no encontrado con id: " + id
                        ));
    }

    public Rom guardar(Rom rom) {

        if (rom.getTamano() == null || rom.getTamano() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El tamaño es obligatorio y debe ser mayor a 0"
            );
        }

        if (rom.getTipoRom() == null || rom.getTipoRom().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El tipoRom es obligatorio"
            );
        }

        rom.setTipoRom(obtenerTipoRomActivo(rom.getTipoRom().getId()));
        if (rom.getEstado() == null) {
            rom.setEstado(true);
        }

        return repo.save(rom);
    }

    public Rom actualizar(Long id, Rom dto) {
        Rom actual = obtenerPorId(id);

        if (dto.getTamano() != null) {
            if (dto.getTamano() <= 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "El tamaño debe ser mayor a 0"
                );
            }
            actual.setTamano(dto.getTamano());
        }

        if (dto.getTipoRom() != null && dto.getTipoRom().getId() != null) {
            actual.setTipoRom(obtenerTipoRomActivo(dto.getTipoRom().getId()));
        }

        if (dto.getEstado() != null) {
            actual.setEstado(dto.getEstado());
        }

        return repo.save(actual);
    }

    public void eliminar(Long id) {
        Rom actual = obtenerPorId(id);
        actual.setEstado(false);
        repo.save(actual);
    }

    public Rom cambiarEstado(Long id, Boolean estado) {
        if (estado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado es obligatorio");
        }
        Rom actual = obtenerPorId(id);
        actual.setEstado(estado);
        return repo.save(actual);
    }

    private TipoRom obtenerTipoRomActivo(Long id) {
        TipoRom tipoRom = tipoRomRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tipoRom no existe"));
        if (Boolean.FALSE.equals(tipoRom.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede asignar un tipoRom inactivo");
        }
        return tipoRom;
    }
}
