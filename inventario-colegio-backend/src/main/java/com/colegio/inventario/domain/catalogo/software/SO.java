package com.colegio.inventario.domain.catalogo.software;

import com.colegio.inventario.domain.catalogo.base.CatalogoBase;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "so")
@Getter
@Setter
public class SO extends CatalogoBase
{

}
