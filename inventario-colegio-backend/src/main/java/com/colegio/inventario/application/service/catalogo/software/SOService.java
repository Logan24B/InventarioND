package com.colegio.inventario.application.service.catalogo.software;

import com.colegio.inventario.application.service.catalogo.base.CatalogoService;

import com.colegio.inventario.domain.catalogo.software.SO;
import com.colegio.inventario.domain.repository.catalogo.software.SORepository;
import org.springframework.stereotype.Service;

@Service
public class SOService extends CatalogoService<SO>
{
    public SOService(SORepository repo)
    {
        super(repo, "SO");
    }
}
