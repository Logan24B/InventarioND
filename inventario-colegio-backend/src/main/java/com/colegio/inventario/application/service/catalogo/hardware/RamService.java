package com.colegio.inventario.application.service.catalogo.hardware;

import com.colegio.inventario.application.service.catalogo.base.CatalogoService;
import com.colegio.inventario.domain.catalogo.hardware.Ram;
import com.colegio.inventario.domain.repository.catalogo.hardware.RamRepository;
import org.springframework.stereotype.Service;

@Service
public class RamService extends CatalogoService<Ram>
{
    public RamService(RamRepository repo)
    {
        super(repo, "Ram");
    }
}
