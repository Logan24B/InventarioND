package com.colegio.inventario.domain.catalogo.hardware;
import com.colegio.inventario.domain.catalogo.hardware.TipoRom;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rom")
@Getter
@Setter
public class Rom
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer tamano;   // tamaño en GB

    @ManyToOne
    @JoinColumn(name = "IdTipo", nullable = false)
    private TipoRom tipoRom;
}

