package com.colegio.inventario.application.service.catalogo.software;

import com.colegio.inventario.application.service.catalogo.base.CatalogoService;
import com.colegio.inventario.domain.catalogo.software.Ofimatica;
import com.colegio.inventario.domain.repository.catalogo.software.OfimaticaRepository;
import org.springframework.stereotype.Service;

@Service
public class OfimaticaService extends CatalogoService<Ofimatica>
{
    public OfimaticaService(OfimaticaRepository repo) {
        super(repo, "Ofimatica");
    }
}
