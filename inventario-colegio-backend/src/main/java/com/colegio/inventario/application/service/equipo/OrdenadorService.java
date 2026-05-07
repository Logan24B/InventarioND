package com.colegio.inventario.application.service.equipo;

import com.colegio.inventario.application.dto.equipo.OrdenadorDTO;
import com.colegio.inventario.application.mapper.equipo.OrdenadorMapper;
import com.colegio.inventario.domain.catalogo.base.CatalogoBase;
import com.colegio.inventario.domain.catalogo.equipo.Marca;
import com.colegio.inventario.domain.catalogo.equipo.Modelo;
import com.colegio.inventario.domain.catalogo.hardware.CategoriaOrdenador;
import com.colegio.inventario.domain.equipo.Ordenador;
import com.colegio.inventario.domain.repository.catalogo.equipo.MarcaRepository;
import com.colegio.inventario.domain.repository.catalogo.equipo.ModeloRepository;
import com.colegio.inventario.domain.repository.catalogo.hardware.CategoriaOrdenadorRepository;
import com.colegio.inventario.domain.repository.catalogo.hardware.ProcesadorRepository;
import com.colegio.inventario.domain.repository.catalogo.hardware.RamRepository;
import com.colegio.inventario.domain.repository.catalogo.hardware.RomRepository;
import com.colegio.inventario.domain.repository.catalogo.software.OfimaticaRepository;
import com.colegio.inventario.domain.repository.catalogo.software.SORepository;
import com.colegio.inventario.domain.repository.equipo.OrdenadorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrdenadorService {

    private final OrdenadorRepository repository;
    private final CategoriaOrdenadorRepository categoriaOrdenadorRepository;
    private final MarcaRepository marcaRepository;
    private final ModeloRepository modeloRepository;
    private final ProcesadorRepository procesadorRepository;
    private final RamRepository ramRepository;
    private final RomRepository romRepository;
    private final SORepository soRepository;
    private final OfimaticaRepository ofimaticaRepository;

    public OrdenadorService(
            OrdenadorRepository repository,
            CategoriaOrdenadorRepository categoriaOrdenadorRepository,
            MarcaRepository marcaRepository,
            ModeloRepository modeloRepository,
            ProcesadorRepository procesadorRepository,
            RamRepository ramRepository,
            RomRepository romRepository,
            SORepository soRepository,
            OfimaticaRepository ofimaticaRepository) {
        this.repository = repository;
        this.categoriaOrdenadorRepository = categoriaOrdenadorRepository;
        this.marcaRepository = marcaRepository;
        this.modeloRepository = modeloRepository;
        this.procesadorRepository = procesadorRepository;
        this.ramRepository = ramRepository;
        this.romRepository = romRepository;
        this.soRepository = soRepository;
        this.ofimaticaRepository = ofimaticaRepository;
    }

    @Transactional(readOnly = true)
    public List<OrdenadorDTO> getAll() {
        return repository.findByEstadoTrue()
                .stream()
                .map(OrdenadorMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrdenadorDTO getById(Long id) {
        return OrdenadorMapper.toDTO(obtenerEntidad(id));
    }

    @Transactional
    public OrdenadorDTO guardar(Ordenador ordenador) {
        validarFechasMantenimiento(ordenador);
        normalizarIdentificadores(ordenador);
        validarIdentificadoresUnicos(null, ordenador);
        aplicarReferenciasObligatorias(ordenador);
        aplicarReferenciasOpcionales(ordenador);

        if (ordenador.getEstado() == null) {
            ordenador.setEstado(true);
        }

        return OrdenadorMapper.toDTO(repository.save(ordenador));
    }

    @Transactional
    public OrdenadorDTO actualizar(Long id, Ordenador datos) {
        Ordenador actual = obtenerEntidad(id);

        if (datos.getTipo() != null) actual.setTipo(datos.getTipo());
        if (datos.getMarca() != null) actual.setMarca(datos.getMarca());
        if (datos.getModelo() != null) actual.setModelo(datos.getModelo());
        if (datos.getNumeroSerie() != null) actual.setNumeroSerie(datos.getNumeroSerie());
        if (datos.getProcesador() != null) actual.setProcesador(datos.getProcesador());
        if (datos.getRam() != null) actual.setRam(datos.getRam());
        if (datos.getRom() != null) actual.setRom(datos.getRom());
        if (datos.getSo() != null) actual.setSo(datos.getSo());
        if (datos.getOfimatica() != null) actual.setOfimatica(datos.getOfimatica());
        if (datos.getAntivirus() != null) actual.setAntivirus(datos.getAntivirus());
        if (datos.getMacAdress() != null) actual.setMacAdress(datos.getMacAdress());
        if (datos.getDominio() != null) actual.setDominio(datos.getDominio());
        if (datos.getIdNDIS() != null) actual.setIdNDIS(datos.getIdNDIS());
        if (datos.getHostName() != null) actual.setHostName(datos.getHostName());
        if (datos.getMantenimiento() != null) actual.setMantenimiento(datos.getMantenimiento());
        if (datos.getProximoMantenimiento() != null) actual.setProximoMantenimiento(datos.getProximoMantenimiento());
        if (datos.getEstado() != null) actual.setEstado(datos.getEstado());
        if (datos.getObservaciones() != null) actual.setObservaciones(datos.getObservaciones());

        validarFechasMantenimiento(actual);
        normalizarIdentificadores(actual);
        validarIdentificadoresUnicos(id, actual);
        aplicarReferenciasObligatorias(actual);
        aplicarReferenciasOpcionales(actual);

        return OrdenadorMapper.toDTO(repository.save(actual));
    }

    @Transactional
    public void eliminar(Long id) {
        Ordenador actual = obtenerEntidad(id);
        actual.setEstado(false);
        repository.save(actual);
    }

    @Transactional
    public OrdenadorDTO cambiarEstado(Long id, Boolean estado) {
        if (estado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado es obligatorio");
        }
        Ordenador actual = obtenerEntidad(id);
        actual.setEstado(estado);
        return OrdenadorMapper.toDTO(repository.save(actual));
    }

    private Ordenador obtenerEntidad(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ordenador no encontrado con id: " + id));
    }

    private void aplicarReferenciasObligatorias(Ordenador ordenador) {
        Long tipoId = idCatalogoObligatorio(ordenador.getTipo(), "El tipo de ordenador es obligatorio");
        Long marcaId = idCatalogoObligatorio(ordenador.getMarca(), "La marca es obligatoria");
        Long modeloId = idModeloObligatorio(ordenador.getModelo(), "El modelo es obligatorio");

        CategoriaOrdenador tipo = categoriaOrdenadorRepository.findById(tipoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El tipo de ordenador no existe"));
        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "La marca no existe"));
        Modelo modelo = modeloRepository.findById(modeloId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El modelo no existe"));

        if (modelo.getMarca() != null && !Objects.equals(modelo.getMarca().getId(), marca.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El modelo seleccionado no pertenece a la marca indicada");
        }

        ordenador.setTipo(tipo);
        ordenador.setMarca(marca);
        ordenador.setModelo(modelo);
    }

    private void aplicarReferenciasOpcionales(Ordenador ordenador) {
        if (ordenador.getProcesador() != null && ordenador.getProcesador().getId() != null) {
            ordenador.setProcesador(procesadorRepository.findById(ordenador.getProcesador().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El procesador no existe")));
        }
        if (ordenador.getRam() != null && ordenador.getRam().getId() != null) {
            ordenador.setRam(ramRepository.findById(ordenador.getRam().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La memoria RAM no existe")));
        }
        if (ordenador.getRom() != null && ordenador.getRom().getId() != null) {
            ordenador.setRom(romRepository.findById(ordenador.getRom().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El almacenamiento no existe")));
        }
        if (ordenador.getSo() != null && ordenador.getSo().getId() != null) {
            ordenador.setSo(soRepository.findById(ordenador.getSo().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El sistema operativo no existe")));
        }
        if (ordenador.getOfimatica() != null && ordenador.getOfimatica().getId() != null) {
            ordenador.setOfimatica(ofimaticaRepository.findById(ordenador.getOfimatica().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La ofimatica no existe")));
        }
    }

    private Long idCatalogoObligatorio(CatalogoBase entidad, String mensaje) {
        if (entidad == null || entidad.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensaje);
        }
        return entidad.getId();
    }

    private Long idModeloObligatorio(Modelo entidad, String mensaje) {
        if (entidad == null || entidad.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensaje);
        }
        return entidad.getId();
    }

    private void validarIdentificadoresUnicos(Long idActual, Ordenador ordenador) {
        if (ordenador.getNumeroSerie() != null) {
            validarUnico(idActual, repository.findByNumeroSerieIgnoreCase(ordenador.getNumeroSerie()), "El numero de serie ya esta registrado");
        }
        if (ordenador.getMacAdress() != null) {
            validarUnico(idActual, repository.findByMacAdressIgnoreCase(ordenador.getMacAdress()), "La direccion MAC ya esta registrada");
        }
        if (ordenador.getIdNDIS() != null) {
            validarUnico(idActual, repository.findByIdNDISIgnoreCase(ordenador.getIdNDIS()), "El ID NDIS ya esta registrado");
        }
        if (ordenador.getHostName() != null) {
            validarUnico(idActual, repository.findByHostNameIgnoreCase(ordenador.getHostName()), "El hostname ya esta registrado");
        }
    }

    private void validarUnico(Long idActual, Optional<Ordenador> existente, String mensaje) {
        if (existente.isPresent() && !Objects.equals(existente.get().getId(), idActual)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, mensaje);
        }
    }

    private void validarFechasMantenimiento(Ordenador ordenador) {
        if (ordenador.getMantenimiento() != null && ordenador.getProximoMantenimiento() == null) {
            ordenador.setProximoMantenimiento(ordenador.getMantenimiento().plusMonths(6));
        }
        if (ordenador.getMantenimiento() != null
                && ordenador.getProximoMantenimiento() != null
                && ordenador.getProximoMantenimiento().isBefore(ordenador.getMantenimiento())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El proximo mantenimiento no puede ser anterior al ultimo mantenimiento");
        }
    }

    private void normalizarIdentificadores(Ordenador ordenador) {
        ordenador.setNumeroSerie(limpiar(ordenador.getNumeroSerie()));
        ordenador.setMacAdress(limpiar(ordenador.getMacAdress()));
        ordenador.setIdNDIS(limpiar(ordenador.getIdNDIS()));
        ordenador.setHostName(limpiar(ordenador.getHostName()));
    }

    private String limpiar(String valor) {
        if (valor == null) {
            return null;
        }
        String limpio = valor.trim();
        return limpio.isEmpty() ? null : limpio;
    }
}
