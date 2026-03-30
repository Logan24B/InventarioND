package com.colegio.inventario.application.service.catalogo.hardware;

import com.colegio.inventario.domain.catalogo.hardware.Rom;
import com.colegio.inventario.domain.repository.catalogo.hardware.RomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RomService {

    private final RomRepository repo;

    public RomService(RomRepository repo) {
        this.repo = repo;
    }

    public List<Rom> listar() {
        return repo.findAll();
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
            actual.setTipoRom(dto.getTipoRom());
        }

        return repo.save(actual);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Rom no encontrado con id: " + id
            );
        }
        repo.deleteById(id);
    }
}
