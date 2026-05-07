package com.colegio.inventario.application.service.catalogo.base;

import com.colegio.inventario.domain.catalogo.base.CatalogoConEstadoBase;
import com.colegio.inventario.domain.repository.catalogo.base.CatalogoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class CatalogoConEstadoService<T extends CatalogoConEstadoBase>
        extends CatalogoService<T> {

    private final CatalogoRepository<T> repo;
    private final String nombreEntidad;

    protected CatalogoConEstadoService(CatalogoRepository<T> repo, String nombreEntidad) {
        super(repo, nombreEntidad);
        this.repo = repo;
        this.nombreEntidad = nombreEntidad;
    }

    @Override
    public T actualizar(Long id, T dto) {
        T actual = obtenerPorId(id);

        // Nombre (si viene)
        if (dto.getNombre() != null && !dto.getNombre().trim().isEmpty()) {
            String nuevoNombre = dto.getNombre().trim();

            boolean cambioNombre = actual.getNombre() == null
                    || !actual.getNombre().equalsIgnoreCase(nuevoNombre);

            if (cambioNombre && repo.existsByNombreIgnoreCase(nuevoNombre)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "El nombre existe en " + nombreEntidad);
            }

            actual.setNombre(nuevoNombre);
        }

        // Descripción (solo si viene)
        if (dto.getDescripcion() != null) {
            actual.setDescripcion(dto.getDescripcion());
        }

        // Estado (solo si viene)
        if (dto.getEstado() != null) {
            actual.setEstado(dto.getEstado());
        }

        return repo.save(actual);
    }
}

