package com.colegio.inventario.infrastructure.controller.personal;

import com.colegio.inventario.application.service.personal.EmpleadoService;
import com.colegio.inventario.domain.personal.Empleado;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin
public class EmpleadoController
{

    private final EmpleadoService service;

    public EmpleadoController(EmpleadoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Empleado> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Empleado obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public Empleado guardar(@RequestBody Empleado empleado) {
        return service.guardar(empleado);
    }

    @PutMapping("/{id}")
    public Empleado actualizar(@PathVariable Long id, @RequestBody Empleado empleado) {
        return service.actualizar(id, empleado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PatchMapping("/{id}/estado")
    public Empleado cambiarEstado(@PathVariable Long id, @RequestBody EstadoDTO dto) {
        return service.cambiarEstado(id, dto.estado());
    }

    public record EstadoDTO(Boolean estado) {}
}
