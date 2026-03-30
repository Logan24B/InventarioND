package com.colegio.inventario.domain.catalogo.equipo;

import com.colegio.inventario.domain.catalogo.hardware.Ram;

import com.colegio.inventario.domain.catalogo.hardware.Rom;

import com.colegio.inventario.domain.catalogo.hardware.CategoriaHardware;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "modelo")
@Getter
@Setter
public class Modelo {

    // ID PRINCIPAL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RELACIÓN CON MARCA
    @ManyToOne
    @JoinColumn(name = "idMarca", nullable = false)
    private Marca marca;

    // RELACIÓN CON CATEGORIA
    @ManyToOne
    @JoinColumn(name = "idCategoria", nullable = false)
    private CategoriaHardware categoria;

    // NOMBRE DEL MODELO
    @Column(length = 50, nullable = false)
    private String nombre;

    // RELACIÓN CON ROM
    @ManyToOne
    @JoinColumn(name = "idRom")
    private Rom rom;

    // RELACIÓN CON RAM
    @ManyToOne
    @JoinColumn(name = "idRam")
    private Ram ram;

    // FECHA
    @Column
    private LocalDate fecha;

    // OBSERVACIONES
    @Column(length = 250)
    private String observaciones;
}


