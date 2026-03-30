package com.colegio.inventario.infrastructure.controller.catalogo.hardware;

import com.colegio.inventario.infrastructure.controller.catalogo.base.CatalogoControllerBase;


import com.colegio.inventario.application.service.catalogo.hardware.ProcesadorService;
import com.colegio.inventario.domain.catalogo.hardware.Procesador;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/procesador")
public class ProcesadorController extends CatalogoControllerBase<Procesador>
{
    public ProcesadorController(ProcesadorService service)
    {
        super(service);
    }
}
