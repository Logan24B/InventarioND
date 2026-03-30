package com.colegio.inventario.application.service.catalogo.ubicacion;

import com.colegio.inventario.application.service.catalogo.base.CatalogoConEstadoService;

import com.colegio.inventario.domain.catalogo.ubicacion.Secciones;
import com.colegio.inventario.domain.repository.catalogo.ubicacion.SeccionesRepository;
import org.springframework.stereotype.Service;

@Service
public class SeccionesService extends CatalogoConEstadoService<Secciones> {

    public SeccionesService(SeccionesRepository repo)
    {
        super(repo, "Secciones");
    }
}
