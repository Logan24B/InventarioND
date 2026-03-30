package com.colegio.inventario.application.dto.personal;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmpleadoDTO {

    private Long id;
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private LocalDate fechaNacimiento;
    private String cargo;
}
