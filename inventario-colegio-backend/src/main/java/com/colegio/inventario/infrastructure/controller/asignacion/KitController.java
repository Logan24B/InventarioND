package com.colegio.inventario.infrastructure.controller.asignacion;

import com.colegio.inventario.application.service.asignacion.KitService;
import com.colegio.inventario.domain.asignacion.Kit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kits")
public class KitController {

    private final KitService service;

    public KitController(KitService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Kit>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kit> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Kit> guardar(@RequestBody Kit kit) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(kit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kit> actualizar(@PathVariable Long id, @RequestBody Kit kit) {
        return ResponseEntity.ok(service.actualizar(id, kit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
