package com.colegio.inventario.application.service.catalogo.hardware;

import com.colegio.inventario.application.service.catalogo.base.CatalogoService;

import com.colegio.inventario.domain.catalogo.hardware.CategoriaOrdenador;
import com.colegio.inventario.domain.repository.catalogo.hardware.CategoriaOrdenadorRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaOrdenadorService extends CatalogoService<CategoriaOrdenador> {

    public CategoriaOrdenadorService(CategoriaOrdenadorRepository repo) {
        super(repo, "CategoriaOrdenador");
    }
}
