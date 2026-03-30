package com.colegio.inventario.application.service.catalogo.equipo;

import com.colegio.inventario.application.service.catalogo.base.CatalogoService;
import com.colegio.inventario.domain.catalogo.equipo.RolEquipo;
import com.colegio.inventario.domain.repository.catalogo.equipo.RolEquipoRepository;
import org.springframework.stereotype.Service;

@Service
public class RolEquipoService extends CatalogoService<RolEquipo>
{
    public RolEquipoService(RolEquipoRepository repo)
    {
        super (repo, "RolEquipo");
    }
}
