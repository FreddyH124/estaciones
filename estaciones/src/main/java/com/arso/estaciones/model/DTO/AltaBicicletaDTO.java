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
@Schema(description = "DTO para dar de alta una bicicleta")
public class AltaBicicletaDTO {
    @Schema(description = "Modelo de la bicicleta", example = "Bicicleta de montaña")
    @NotNull
    @NotBlank
    private String modelo;
    @Schema(description = "ID de la estación donde se dará de alta la bicicleta", example = "663b2ad6d295d90b4b0475f6")
    @NotNull
    @NotBlank
    private String idEstacion;
}
