package com.colegio.inventario.infrastructure.controller.equipo;

import com.colegio.inventario.application.dto.equipo.OrdenadorDTO;
import com.colegio.inventario.application.service.equipo.OrdenadorService;
import com.colegio.inventario.domain.equipo.Ordenador;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenadores")
public class OrdenadorController {

    private final OrdenadorService service;

    public OrdenadorController(OrdenadorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OrdenadorDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenadorDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<OrdenadorDTO> guardar(@RequestBody Ordenador ordenador) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(ordenador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenadorDTO> actualizar(@PathVariable Long id, @RequestBody Ordenador ordenador) {
        return ResponseEntity.ok(service.actualizar(id, ordenador));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrdenadorDTO> patch(@PathVariable Long id, @RequestBody Ordenador ordenador) {
        return ResponseEntity.ok(service.actualizar(id, ordenador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<OrdenadorDTO> cambiarEstado(@PathVariable Long id, @RequestBody EstadoDTO dto) {
        return ResponseEntity.ok(service.cambiarEstado(id, dto.estado()));
    }

    public record EstadoDTO(Boolean estado) {}
}
