package com.arso.estaciones.model.DTO;

import com.arso.estaciones.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDTO {
    private String nombre;
    private String clave;
    private Rol rol;
}
