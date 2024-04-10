package com.arso.estaciones.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AltaEstacionDTO {
    private String nombre;
    private int puestos;
    private String direccion;
    private double lat;
    private double lng;
}
