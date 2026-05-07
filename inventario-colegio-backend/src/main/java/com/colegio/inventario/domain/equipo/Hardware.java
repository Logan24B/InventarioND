package com.colegio.inventario.domain.equipo;

import com.colegio.inventario.domain.catalogo.hardware.CategoriaHardware;
import com.colegio.inventario.domain.catalogo.equipo.Modelo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "hardware")
@Getter
@Setter
public class Hardware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idCategoria", nullable = false)
    private CategoriaHardware categoria;

    @ManyToOne
    @JoinColumn(name = "idModelo", nullable = false)
    private Modelo modelo;

    @Column(name = "serial_number", length = 50, unique = true, nullable = false)
    private String serialNumber;

    @Column(nullable = false)
    private Boolean estado = true;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(length = 250)
    private String observaciones;

    @PrePersist
    protected void onCreate() {
        if (estado == null) {
            estado = true;
        }
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }
}
