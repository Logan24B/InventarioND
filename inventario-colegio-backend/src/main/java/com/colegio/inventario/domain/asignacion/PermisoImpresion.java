package com.colegio.inventario.domain.asignacion;

import com.colegio.inventario.domain.equipo.Hardware;
import com.colegio.inventario.domain.equipo.Ordenador;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Permiso_Impresion")
@Getter
@Setter
public class PermisoImpresion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IdOrdenador", nullable = false)
    private Ordenador ordenador;

    @ManyToOne
    @JoinColumn(name = "IdImpresora", nullable = false)
    private Hardware impresora;

    @Column(name = "Estado", nullable = false)
    private Boolean estado = true;

    @Column(name = "Fecha_Alta", nullable = false)
    private LocalDateTime fechaAlta;

    @Column(name = "Fecha_Baja")
    private LocalDateTime fechaBaja;

    @Column(name = "Observaciones", length = 250)
    private String observaciones;

    @PrePersist
    protected void onCreate() {
        if (estado == null) {
            estado = true;
        }
        if (fechaAlta == null) {
            fechaAlta = LocalDateTime.now();
        }
    }
}
