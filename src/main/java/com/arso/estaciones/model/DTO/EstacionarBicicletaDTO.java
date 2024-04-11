package com.arso.estaciones.model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para estacionar una bicicleta")
public class EstacionarBicicletaDTO {
    @Schema(description = "ID de la estación donde se estacionará la bicicleta", example = "6616cee50a78571ff80a8ff3")
    @NotNull
    @NotBlank
    private String idEstacion;
    @Schema(description = "ID de la bicicleta que se estacionará", example = "660ee344ef8055670e95a1bb")
    @NotNull
    @NotBlank
    private String idBicicleta;
}
