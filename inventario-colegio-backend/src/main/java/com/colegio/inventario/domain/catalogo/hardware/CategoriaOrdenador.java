package com.colegio.inventario.domain.catalogo.hardware;

import com.colegio.inventario.domain.catalogo.base.CatalogoBase;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categoria_ordenador")
@Getter
@Setter
public class CategoriaOrdenador extends CatalogoBase{

}

