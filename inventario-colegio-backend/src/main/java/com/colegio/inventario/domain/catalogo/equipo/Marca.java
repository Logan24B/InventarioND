package com.colegio.inventario.domain.catalogo.equipo;

import com.colegio.inventario.domain.catalogo.base.CatalogoBase;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "marcas")
@Getter
@Setter
public class Marca extends CatalogoBase {
}

