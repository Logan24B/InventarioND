package com.colegio.inventario.infrastructure.controller.catalogo.hardware;

import com.colegio.inventario.application.service.catalogo.hardware.RomService;
import com.colegio.inventario.domain.catalogo.hardware.Rom;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rom")
@CrossOrigin
public class RomController {

    private final RomService service;

    public RomController(RomService service) {
        this.service = service;
    }

    @GetMapping
    public List<Rom> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Rom obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public Rom guardar(@RequestBody Rom rom) {
        return service.guardar(rom);
    }

    @PutMapping("/{id}")
    public Rom actualizar(@PathVariable Long id, @RequestBody Rom rom) {
        return service.actualizar(id, rom);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}

