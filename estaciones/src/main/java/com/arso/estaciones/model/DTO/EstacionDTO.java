package com.arso.estaciones.model.DTO;

import com.arso.estaciones.interfaces.IIdentificable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para representar una estación")
public class EstacionDTO implements IIdentificable {
    @Schema(description = "ID de la estación", example = "6616cee50a78571ff80a8ff3")
    private String id;
    @Schema(description = "Nombre de la estación", example = "Estación Central")
    private String nombre;
    @Schema(description = "Número de puestos en la estación", example = "20")
    private int puestos;
    @Schema(description = "Latitud", example = "20")
    private double lat;
    @Schema(description = "Longitud", example = "20")
    private double lng;
}
