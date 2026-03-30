package com.colegio.inventario.domain.catalogo.subcatalogo;

import com.colegio.inventario.domain.catalogo.CatalogoBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tiporom")
@Getter
@Setter
public class TipoRom extends CatalogoBase
{

}
