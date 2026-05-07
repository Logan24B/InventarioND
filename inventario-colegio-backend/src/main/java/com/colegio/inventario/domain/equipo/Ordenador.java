package com.colegio.inventario.domain.equipo;

import com.colegio.inventario.domain.catalogo.equipo.Marca;
import com.colegio.inventario.domain.catalogo.equipo.Modelo;
import com.colegio.inventario.domain.catalogo.hardware.*;
import com.colegio.inventario.domain.catalogo.software.Ofimatica;
import com.colegio.inventario.domain.catalogo.software.SO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordenadores")
@Getter
@Setter
public class Ordenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idTipo", nullable = false)
    private CategoriaOrdenador tipo;

    @ManyToOne
    @JoinColumn(name = "idMarca", nullable = false)
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "idModelo", nullable = false)
    private Modelo modelo;

    @Column(name = "numeroSerie", length = 50)
    private String numeroSerie;

    @ManyToOne
    @JoinColumn(name = "idProcesador")
    private Procesador procesador;

    @ManyToOne
    @JoinColumn(name = "idRam")
    private Ram ram;

    @ManyToOne
    @JoinColumn(name = "idRom")
    private Rom rom;

    @ManyToOne
    @JoinColumn(name = "idSO")
    private SO so;

    @ManyToOne
    @JoinColumn(name = "idOfimatica")
    private Ofimatica ofimatica;

    @Column(name = "antivirus")
    private Boolean antivirus;

    @Column(name = "macAdress", length = 50, unique = true)
    private String macAdress;

    @Column(name = "dominio")
    private Boolean dominio;

    @Column(name = "idNDIS", length = 30, unique = true)
    private String idNDIS;

    @Column(name = "hostName", length = 50, unique = true)
    private String hostName;

    @Column(name = "mantenimiento")
    private LocalDate mantenimiento;

    @Column(name = "proximoMantenimiento")
    private LocalDate proximoMantenimiento;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "observaciones", length = 250)
    private String observaciones;

    @Column(name = "fecha")
    private LocalDateTime fecha;

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
