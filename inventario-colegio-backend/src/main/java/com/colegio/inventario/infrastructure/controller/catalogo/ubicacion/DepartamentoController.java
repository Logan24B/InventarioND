package com.colegio.inventario.infrastructure.controller.catalogo.ubicacion;

import com.colegio.inventario.application.dto.ubicacion.DepartamentoDTO;
import com.colegio.inventario.application.service.catalogo.ubicacion.DepartamentoService;
import com.colegio.inventario.domain.catalogo.ubicacion.Departamento;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController // Indica que es un controlador REST
@RequestMapping("/api/departamentos") // Ruta base del endpoint
@CrossOrigin // Permite peticiones desde frontend (CORS)
public class DepartamentoController {

    private final DepartamentoService service;

    // Inyección por constructor
    public DepartamentoController(DepartamentoService service) {
        this.service = service;
    }

    // GET - Listar todos
    @GetMapping
    public List<Departamento> listar() {
        return service.listar();
    }

    // GET - Obtener por ID
    @GetMapping("/{id}")
    public Departamento obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    // POST - Crear nuevo
    @PostMapping
    public Departamento guardar(@RequestBody Departamento departamento) {
        return service.guardar(departamento);
    }

    // PUT - Actualizar completo
    @PutMapping("/{id}")
    public Departamento actualizar(@PathVariable Long id,
                                   @RequestBody Departamento departamento) {
        return service.actualizar(id, departamento);
    }

    // DELETE - Eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    //Reactivar un departamento
    @PutMapping("/{id}/reactivar")
    public Departamento reactivar(@PathVariable Long id) {
        return service.reactivar(id);
    }

    @GetMapping("/page")
    public Page<Departamento> listarPaginado(Pageable pageable) {
        return service.listarPaginado(pageable);
    }

    @GetMapping("/dto")
    public List<DepartamentoDTO> listarDTO() {
        return service.listarDTO();
    }
    
}
