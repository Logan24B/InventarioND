package com.colegio.inventario.domain.catalogo.hardware;

import com.colegio.inventario.domain.catalogo.base.CatalogoBase;
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
