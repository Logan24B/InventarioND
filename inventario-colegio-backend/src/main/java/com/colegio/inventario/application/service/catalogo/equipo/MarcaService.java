package com.colegio.inventario.application.service.catalogo.equipo;

import com.colegio.inventario.application.service.catalogo.base.CatalogoService;
import com.colegio.inventario.domain.catalogo.equipo.Marca;
import com.colegio.inventario.domain.repository.catalogo.equipo.MarcaRepository;
import org.springframework.stereotype.Service;

@Service
public class MarcaService extends CatalogoService<Marca> {
    public MarcaService(MarcaRepository repo) {
        super(repo, "Marca");
    }
}
