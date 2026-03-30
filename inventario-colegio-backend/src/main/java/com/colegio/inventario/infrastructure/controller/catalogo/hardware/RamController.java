package com.colegio.inventario.infrastructure.controller.catalogo.hardware;

import com.colegio.inventario.infrastructure.controller.catalogo.base.CatalogoControllerBase;

import com.colegio.inventario.application.service.catalogo.hardware.RamService;
import com.colegio.inventario.domain.catalogo.hardware.Ram;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ram")
public class RamController extends CatalogoControllerBase<Ram>
{
    public RamController(RamService service)
    {
        super(service);
    }
}
