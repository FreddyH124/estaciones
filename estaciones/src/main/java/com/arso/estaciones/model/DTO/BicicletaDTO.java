package com.arso.estaciones.model.DTO;

import com.arso.estaciones.interfaces.IIdentificable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para representar una bicicleta")
public class BicicletaDTO implements IIdentificable {
    @Schema(description = "ID de la bicicleta", example = "660ee344ef8055670e95a1bb")
    private String id;
    @Schema(description = "Indica si la bicicleta está disponible", example = "true")
    private boolean disponible;
    @Schema(description = "Modelo de la bicicleta", example = "Mountain Bike")
    private String modelo;
}
