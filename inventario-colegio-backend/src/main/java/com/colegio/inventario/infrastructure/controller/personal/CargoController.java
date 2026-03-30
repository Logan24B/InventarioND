package com.colegio.inventario.infrastructure.controller.personal;

import com.colegio.inventario.infrastructure.controller.catalogo.base.CatalogoControllerBase;

import com.colegio.inventario.application.service.personal.CargoService;
import com.colegio.inventario.domain.personal.Cargo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cargos")
public class CargoController extends CatalogoControllerBase<Cargo>
{
    public CargoController(CargoService service)
    {
        super(service);
    }
}
