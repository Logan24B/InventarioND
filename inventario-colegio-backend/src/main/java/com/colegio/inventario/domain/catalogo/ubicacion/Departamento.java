package com.colegio.inventario.domain.catalogo.ubicacion;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "departamento")
@Getter
@Setter
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremental en SQL Server
    private Long id;

    // RELACIÓN CON EDIFICIO

    @ManyToOne // Muchos departamentos pueden pertenecer a un edificio
    @JoinColumn(name = "IdEdificio", nullable = false)
    // Crea la FK IdEdificio en la tabla departamento
    private Edificio edificio;


    // RELACIÓN CON SECCIÓN
    @ManyToOne // Muchos departamentos pueden pertenecer a una sección
    @JoinColumn(name = "IdSeccion", nullable = false)
    private Secciones seccion;

    // ESTADO ACTIVO / INACTIVO
    @Column(nullable = false)
    private Boolean estado = true;

    // FECHA DE CREACIÓN
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    // DESCRIPCIÓN OPCIONAL
    @Column(length = 250)
    private String descripcion;

    // MÉTODO QUE SE EJECUTA ANTES DE INSERTAR
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }

    @Column
    private LocalDateTime fechaModificacion;

    @PreUpdate
    protected void onUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }


}

