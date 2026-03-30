package com.colegio.inventario.application.service.catalogo.hardware;

import com.colegio.inventario.application.service.catalogo.base.CatalogoService;
import com.colegio.inventario.domain.catalogo.hardware.TipoRom;
import com.colegio.inventario.domain.repository.catalogo.hardware.TipoRomRepository;
import org.springframework.stereotype.Service;

@Service
public class TipoRomService extends CatalogoService<TipoRom>
{
    public TipoRomService(TipoRomRepository repo)
    {
        super(repo,"TipoRom");
    }
}
