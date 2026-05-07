package com.colegio.inventario.domain.personal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "empleados")
@Getter
@Setter
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nombre1;

    @Column(length = 100)
    private String nombre2;

    @Column(length = 100, nullable = false)
    private String apellido1;

    @Column(length = 100)
    private String apellido2;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "Idcargo", nullable = false)
    private Cargo cargo;

    @Column(nullable = false)
    private Boolean estado = true;
}
