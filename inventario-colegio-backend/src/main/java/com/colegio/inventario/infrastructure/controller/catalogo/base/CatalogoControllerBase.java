package com.colegio.inventario.infrastructure.controller.catalogo.base;

import com.colegio.inventario.application.service.catalogo.base.CatalogoService;
import com.colegio.inventario.domain.catalogo.base.CatalogoBase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
public abstract class CatalogoControllerBase<T extends CatalogoBase> {

    protected final CatalogoService<T> service;

    protected CatalogoControllerBase(CatalogoService<T> service) {
        this.service = service;
    }

    @GetMapping
    public List<T> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public T obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public T guardar(@RequestBody T entidad) {
        return service.guardar(entidad);
    }

    @PutMapping("/{id}")
    public T actualizar(@PathVariable Long id, @RequestBody T entidad) {
        return service.actualizar(id, entidad);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PatchMapping("/{id}")
    public T patch(@PathVariable Long id, @RequestBody T entidad) {
        return service.actualizar(id, entidad); // reutiliza tu update parcial
    }

}
