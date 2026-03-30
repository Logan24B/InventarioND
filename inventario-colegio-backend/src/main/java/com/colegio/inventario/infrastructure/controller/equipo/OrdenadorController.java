package com.colegio.inventario.infrastructure.controller.equipo;

import com.colegio.inventario.application.dto.equipo.OrdenadorDTO;
import com.colegio.inventario.application.service.equipo.OrdenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenadores")
public class OrdenadorController {

    @Autowired
    private OrdenadorService service;

    @GetMapping
    public ResponseEntity<List<OrdenadorDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenadorDTO> getById(@PathVariable Long id) {
        OrdenadorDTO dto = service.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }
}
