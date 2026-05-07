package com.colegio.inventario.infrastructure.controller.asignacion;

import com.colegio.inventario.application.service.asignacion.EquipoAsignadoService;
import com.colegio.inventario.domain.asignacion.EquipoAsignado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos-asignados")
public class EquipoAsignadoController {

    private final EquipoAsignadoService service;

    public EquipoAsignadoController(EquipoAsignadoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EquipoAsignado>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoAsignado> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<EquipoAsignado> guardar(@RequestBody EquipoAsignado asignacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(asignacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipoAsignado> actualizar(@PathVariable Long id, @RequestBody EquipoAsignado asignacion) {
        return ResponseEntity.ok(service.actualizar(id, asignacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<EquipoAsignado> cambiarEstado(@PathVariable Long id, @RequestBody EstadoDTO dto) {
        return ResponseEntity.ok(service.cambiarEstado(id, dto.estado()));
    }

    public record EstadoDTO(Boolean estado) {}
}
