package com.colegio.inventario.infrastructure.controller.catalogo.ubicacion;

import com.colegio.inventario.infrastructure.controller.catalogo.base.CatalogoControllerBase;

import com.colegio.inventario.application.service.catalogo.ubicacion.SeccionesService;
import com.colegio.inventario.domain.catalogo.ubicacion.Secciones;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secciones")
@CrossOrigin
public class SeccionesController extends CatalogoControllerBase<Secciones> {

    private final SeccionesService seccionesService;

    public SeccionesController(SeccionesService seccionesService) {
        super(seccionesService);
        this.seccionesService = seccionesService;
    }

    @PatchMapping("/{id}/estado")
    public Secciones cambiarEstado(@PathVariable Long id, @RequestBody EstadoDTO body) {
        return seccionesService.cambiarEstado(id, body.estado());
    }

    public record EstadoDTO(Boolean estado) {}
}


