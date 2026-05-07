package com.colegio.inventario.infrastructure.controller.asignacion;

import com.colegio.inventario.application.service.asignacion.PermisoImpresionService;
import com.colegio.inventario.domain.asignacion.PermisoImpresion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permisos-impresion")
public class PermisoImpresionController {

    private final PermisoImpresionService service;

    public PermisoImpresionController(PermisoImpresionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PermisoImpresion>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermisoImpresion> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PermisoImpresion> guardar(@RequestBody PermisoImpresion permiso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(permiso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermisoImpresion> actualizar(@PathVariable Long id, @RequestBody PermisoImpresion permiso) {
        return ResponseEntity.ok(service.actualizar(id, permiso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PermisoImpresion> cambiarEstado(@PathVariable Long id, @RequestBody EstadoDTO dto) {
        return ResponseEntity.ok(service.cambiarEstado(id, dto.estado()));
    }

    public record EstadoDTO(Boolean estado) {}
}
