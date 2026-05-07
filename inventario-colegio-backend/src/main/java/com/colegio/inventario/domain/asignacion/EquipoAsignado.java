package com.colegio.inventario.domain.asignacion;

import com.colegio.inventario.domain.catalogo.ubicacion.Departamento;
import com.colegio.inventario.domain.equipo.Ordenador;
import com.colegio.inventario.domain.personal.Empleado;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Equipos_Asignados")
@Getter
@Setter
public class EquipoAsignado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IdOrdenador", nullable = false)
    private Ordenador ordenador;

    @ManyToOne
    @JoinColumn(name = "IdDepartamento", nullable = false)
    private Departamento departamento;

    @ManyToOne
    @JoinColumn(name = "IdUsuario", nullable = false)
    private Empleado usuario;

    @Column(name = "Estado", nullable = false)
    private Boolean estado = true;

    @Column(name = "Fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "Observaciones", length = 250)
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
