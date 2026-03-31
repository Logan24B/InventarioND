package com.colegio.inventario.domain.asignacion;

import com.colegio.inventario.domain.equipo.Ordenador;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "kits")
@Getter
@Setter
public class Kit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "idOrdenador", unique = true, nullable = false)
    private Ordenador ordenador;

    @Column(nullable = false)
    private Boolean estado = true;

    @Column(length = 250)
    private String observaciones;

    @JsonManagedReference
    @OneToMany(mappedBy = "kit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KitDetalle> detalles = new ArrayList<>();
}
