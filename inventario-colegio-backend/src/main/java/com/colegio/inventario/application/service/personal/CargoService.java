package com.colegio.inventario.application.service.personal;

import com.colegio.inventario.application.service.catalogo.base.CatalogoService;
import com.colegio.inventario.domain.personal.Cargo;
import com.colegio.inventario.domain.repository.personal.CargoRepository;
import org.springframework.stereotype.Service;

@Service
public class CargoService extends CatalogoService<Cargo>
{
    public CargoService(CargoRepository repo)
    {
        super(repo, "cargo");
    }
}
