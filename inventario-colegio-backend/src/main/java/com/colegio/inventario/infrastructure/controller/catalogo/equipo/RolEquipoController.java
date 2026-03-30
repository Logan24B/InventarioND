package com.colegio.inventario.infrastructure.controller.catalogo.equipo;

import com.colegio.inventario.infrastructure.controller.catalogo.base.CatalogoControllerBase;
import com.colegio.inventario.application.service.catalogo.equipo.RolEquipoService;
import com.colegio.inventario.domain.catalogo.equipo.RolEquipo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rolequipo")
public class RolEquipoController extends CatalogoControllerBase<RolEquipo>
{
    public RolEquipoController(RolEquipoService service)
    {
        super(service);
    }

}
