package com.colegio.inventario.domain.personal;

import com.colegio.inventario.domain.catalogo.base.CatalogoBase;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cargo")
@Getter
@Setter
public class Cargo extends CatalogoBase
{

}
