package com.colegio.inventario.application.service.equipo;

import com.colegio.inventario.application.dto.equipo.OrdenadorDTO;
import com.colegio.inventario.application.mapper.equipo.OrdenadorMapper;
import com.colegio.inventario.domain.repository.equipo.OrdenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenadorService {

    @Autowired
    private OrdenadorRepository repository;

    @Transactional(readOnly = true)
    public List<OrdenadorDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(OrdenadorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrdenadorDTO getById(Long id) {
        return repository.findById(id)
                .map(OrdenadorMapper::toDTO)
                .orElse(null);
    }
}
