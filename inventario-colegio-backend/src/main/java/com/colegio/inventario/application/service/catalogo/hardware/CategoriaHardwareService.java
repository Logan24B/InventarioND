package com.colegio.inventario.application.service.catalogo.hardware;

import com.colegio.inventario.application.service.catalogo.base.CatalogoService;

import com.colegio.inventario.domain.catalogo.hardware.CategoriaHardware;
import com.colegio.inventario.domain.repository.catalogo.hardware.CategoriaHardwareRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaHardwareService extends CatalogoService<CategoriaHardware>
{
    public CategoriaHardwareService(CategoriaHardwareRepository repo)
    {
        super(repo, "CategoriaHardware");
    }
}
