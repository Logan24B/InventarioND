package com.colegio.inventario.application.service.catalogo.hardware;

import com.colegio.inventario.application.service.catalogo.base.CatalogoService;
import com.colegio.inventario.domain.catalogo.hardware.Procesador;
import com.colegio.inventario.domain.repository.catalogo.hardware.ProcesadorRepository;
import org.springframework.stereotype.Service;

@Service
public class ProcesadorService extends CatalogoService<Procesador>
{
  public ProcesadorService(ProcesadorRepository repo)
  {
      super(repo,"Procesador");
  }
}
