package com.colegio.inventario.infrastructure.controller.catalogo.equipo;

import com.colegio.inventario.infrastructure.controller.catalogo.base.CatalogoControllerBase;

import com.colegio.inventario.application.service.catalogo.equipo.MarcaService;
import com.colegio.inventario.domain.catalogo.equipo.Marca;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/marcas")
public class MarcasController extends CatalogoControllerBase<Marca> {

    public MarcasController(MarcaService service) {
        super(service);
    }
}



