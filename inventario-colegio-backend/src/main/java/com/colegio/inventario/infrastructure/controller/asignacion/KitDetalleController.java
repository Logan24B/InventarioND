package com.colegio.inventario.infrastructure.controller.asignacion;

import com.colegio.inventario.application.service.asignacion.KitDetalleService;
import com.colegio.inventario.domain.asignacion.KitDetalle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kit-detalles")
public class KitDetalleController {

    private final KitDetalleService service;

    public KitDetalleController(KitDetalleService service) {
        this.service = service;
    }

    // Listar todos los detalles de un Kit específico
    @GetMapping("/kit/{kitId}")
    public ResponseEntity<List<KitDetalle>> listarPorKit(@PathVariable Long kitId) {
        return ResponseEntity.ok(service.listarPorKit(kitId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<KitDetalle> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<KitDetalle> guardar(@RequestBody KitDetalle detalle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(detalle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KitDetalle> actualizar(@PathVariable Long id, @RequestBody KitDetalle detalle) {
        return ResponseEntity.ok(service.actualizar(id, detalle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
