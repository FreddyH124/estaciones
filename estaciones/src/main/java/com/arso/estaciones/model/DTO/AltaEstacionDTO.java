package com.arso.estaciones.model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO del alta de las estaciones")
public class AltaEstacionDTO {
    @Schema(description = "Nombre de la estación", example = "Estación Central")
    @NotNull
    @NotEmpty
    private String nombre;
    @Schema(description = "Número de puestos en la estación", example = "20")
    @NotNull
    private int puestos;
    @Schema(description = "Dirección de la estación", example = "Calle Principal 123")
    @NotNull
    @NotEmpty
    private String direccion;
    @Schema(description = "Latitud de la estación", example = "40.7128")
    @NotNull
    private double lat;
    @Schema(description = "Longitud de la estación", example = "-74.0060")
    @NotNull
    private double lng;
}
