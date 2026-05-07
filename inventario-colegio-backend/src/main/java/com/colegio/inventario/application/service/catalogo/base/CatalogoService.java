package com.colegio.inventario.application.service.catalogo.base;

import com.colegio.inventario.domain.catalogo.base.CatalogoBase;
import com.colegio.inventario.domain.repository.catalogo.base.CatalogoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public abstract class CatalogoService<T extends CatalogoBase> {

    private final CatalogoRepository<T> repo;
    private final String nombreEntidad; // para mensajes

    protected CatalogoService(CatalogoRepository<T> repo, String nombreEntidad) {
        this.repo = repo;
        this.nombreEntidad = nombreEntidad;
    }

    public List<T> listar() {
        return repo.findByEstadoTrue();
    }

    public T obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, nombreEntidad + " no encontrada"));
    }

    public T guardar(T entidad) {
        if (entidad.getNombre() == null || entidad.getNombre().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El nombre de " + nombreEntidad.toLowerCase() + " es obligatorio");
        }

        String nombre = entidad.getNombre().trim();
        entidad.setNombre(nombre);

        if (repo.existsByNombreIgnoreCase(nombre)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Esta " + nombreEntidad.toLowerCase() + " ya existe");
        }

        return repo.save(entidad);
    }

    public T actualizar(Long id, T entidad) {
        T actual = obtenerPorId(id);

        if (entidad.getNombre() != null && !entidad.getNombre().trim().isEmpty()) {
            String nuevoNombre = entidad.getNombre().trim();

            boolean cambioNombre = actual.getNombre() == null
                    || !actual.getNombre().equalsIgnoreCase(nuevoNombre);

            if (cambioNombre && repo.existsByNombreIgnoreCase(nuevoNombre)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre existe");
            }
            actual.setNombre(nuevoNombre);
        }

        if (entidad.getDescripcion() != null) {
            actual.setDescripcion(entidad.getDescripcion());
        }

        if (entidad.getEstado() != null) {
            actual.setEstado(entidad.getEstado());
        }

        return repo.save(actual);
    }

    public void eliminar(Long id) {
        T actual = obtenerPorId(id);
        actual.setEstado(false);
        repo.save(actual);
    }

    public T cambiarEstado(Long id, Boolean estado) {
        if (estado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado es obligatorio");
        }
        T actual = obtenerPorId(id);
        actual.setEstado(estado);
        return repo.save(actual);
    }
}
