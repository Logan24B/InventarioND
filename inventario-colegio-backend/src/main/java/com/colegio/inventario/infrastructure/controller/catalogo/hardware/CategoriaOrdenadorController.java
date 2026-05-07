package com.colegio.inventario.infrastructure.controller.catalogo.hardware;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colegio.inventario.application.service.catalogo.hardware.CategoriaOrdenadorService;
import com.colegio.inventario.domain.catalogo.hardware.CategoriaOrdenador;
import com.colegio.inventario.infrastructure.controller.catalogo.base.CatalogoControllerBase;

@RestController
@RequestMapping("/api/categorias-ordenador")
public class CategoriaOrdenadorController extends CatalogoControllerBase<CategoriaOrdenador> {

    public CategoriaOrdenadorController(CategoriaOrdenadorService service) {
        super(service);
    }
}
