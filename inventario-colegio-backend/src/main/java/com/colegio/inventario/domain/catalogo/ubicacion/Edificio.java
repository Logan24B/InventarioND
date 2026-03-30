package com.colegio.inventario.domain.catalogo.ubicacion;

import com.colegio.inventario.domain.catalogo.base.CatalogoConEstadoBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "edificio")
@Getter
@Setter
public class Edificio extends CatalogoConEstadoBase
{
    
}
