package com.colegio.inventario.infrastructure.controller.catalogo.hardware;

import com.colegio.inventario.infrastructure.controller.catalogo.base.CatalogoControllerBase;

import com.colegio.inventario.application.service.catalogo.hardware.TipoRomService;
import com.colegio.inventario.domain.catalogo.hardware.TipoRom;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tiporom")
public class TipoRomController extends CatalogoControllerBase<TipoRom>
{
    public TipoRomController(TipoRomService service)
    {
        super(service);
    }
}
