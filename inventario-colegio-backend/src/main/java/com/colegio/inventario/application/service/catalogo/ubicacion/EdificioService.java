package com.colegio.inventario.application.service.catalogo.ubicacion;

import com.colegio.inventario.application.service.catalogo.base.CatalogoConEstadoService;

import com.colegio.inventario.domain.catalogo.ubicacion.Edificio;
import com.colegio.inventario.domain.repository.catalogo.ubicacion.EdificioRepository;
import org.springframework.stereotype.Service;

@Service
public class EdificioService extends CatalogoConEstadoService<Edificio> {

    public EdificioService(EdificioRepository repo) {
        super(repo, "Edificio");
    }
}
