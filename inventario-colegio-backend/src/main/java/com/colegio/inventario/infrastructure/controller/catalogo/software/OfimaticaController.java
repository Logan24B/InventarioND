package com.colegio.inventario.infrastructure.controller.catalogo.software;

import com.colegio.inventario.infrastructure.controller.catalogo.base.CatalogoControllerBase;

import com.colegio.inventario.application.service.catalogo.software.OfimaticaService;
import com.colegio.inventario.domain.catalogo.software.Ofimatica;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ofimatica")
public class OfimaticaController extends CatalogoControllerBase<Ofimatica> {

    public OfimaticaController(OfimaticaService service) {
        super(service);
    }
}
