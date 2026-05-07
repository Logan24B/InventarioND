package com.colegio.inventario.infrastructure.controller.equipo;

import com.colegio.inventario.application.service.equipo.HardwareService;
import com.colegio.inventario.domain.equipo.Hardware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hardware")
public class HardwareController {

    private final HardwareService service;

    public HardwareController(HardwareService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Hardware>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hardware> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Hardware> guardar(@RequestBody Hardware hardware) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(hardware));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hardware> actualizar(@PathVariable Long id, @RequestBody Hardware hardware) {
        return ResponseEntity.ok(service.actualizar(id, hardware));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Hardware> cambiarEstado(@PathVariable Long id, @RequestBody EstadoDTO dto) {
        return ResponseEntity.ok(service.cambiarEstado(id, dto.estado()));
    }

    public record EstadoDTO(Boolean estado) {}
}
