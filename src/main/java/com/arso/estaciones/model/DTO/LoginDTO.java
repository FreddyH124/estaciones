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
@Schema(description = "DTO para iniciar sesión")
public class LoginDTO {
    @Schema(description = "Nombre de usuario", example = "usuario123")
    @NotNull
    @NotBlank
    private String nombre;
    @Schema(description = "Clave de acceso", example = "contraseña123")
    @NotNull
    @NotBlank
    private String clave;
}
