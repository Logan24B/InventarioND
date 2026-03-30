package com.colegio.inventario.infrastructure.controller.catalogo.ubicacion;

import com.colegio.inventario.infrastructure.controller.catalogo.base.CatalogoControllerBase;

import com.colegio.inventario.application.service.catalogo.ubicacion.EdificioService;
import com.colegio.inventario.domain.catalogo.ubicacion.Edificio;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/edificios")
public class EdificioController extends CatalogoControllerBase<Edificio>
{
    public EdificioController(EdificioService service) {
        super(service);
    }
}
