package com.colegio.inventario.infrastructure.controller.catalogo.software;

import com.colegio.inventario.infrastructure.controller.catalogo.base.CatalogoControllerBase;

import com.colegio.inventario.application.service.catalogo.software.SOService;
import com.colegio.inventario.domain.catalogo.software.SO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/so")
public class SOController extends CatalogoControllerBase<SO>
{
    public SOController(SOService service)
    {
        super(service);
    }
}
