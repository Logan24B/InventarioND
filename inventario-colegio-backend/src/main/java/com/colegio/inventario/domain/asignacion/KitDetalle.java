package com.colegio.inventario.domain.asignacion;

import com.colegio.inventario.domain.catalogo.equipo.RolEquipo;
import com.colegio.inventario.domain.equipo.Hardware;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "kit_detalles")
@Getter
@Setter
public class KitDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "idKit", nullable = false)
    private Kit kit;

    @OneToOne
    @JoinColumn(name = "idHardware", unique = true, nullable = false)
    private Hardware hardware;

    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false)
    private RolEquipo rol;

    @Column(nullable = false)
    private Boolean estado = true;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(length = 250)
    private String observaciones;
}
