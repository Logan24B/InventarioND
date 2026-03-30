package com.colegio.inventario.domain.catalogo.hardware;

import com.colegio.inventario.domain.catalogo.base.CatalogoBase;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "procesador")
@Getter
@Setter
public class Procesador extends CatalogoBase
{

}
