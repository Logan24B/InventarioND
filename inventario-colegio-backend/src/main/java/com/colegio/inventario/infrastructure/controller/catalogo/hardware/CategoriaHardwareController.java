package com.colegio.inventario.infrastructure.controller.catalogo.hardware;

import com.colegio.inventario.infrastructure.controller.catalogo.base.CatalogoControllerBase;
import com.colegio.inventario.application.service.catalogo.hardware.CategoriaHardwareService;
import com.colegio.inventario.domain.catalogo.hardware.CategoriaHardware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categorias-hardware")
public class CategoriaHardwareController extends CatalogoControllerBase<CategoriaHardware>
{
    public CategoriaHardwareController(CategoriaHardwareService service)
    {
        super(service);
    }
}
