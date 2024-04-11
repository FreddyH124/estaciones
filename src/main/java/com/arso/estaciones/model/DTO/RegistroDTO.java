package com.arso.estaciones.model.DTO;

import com.arso.estaciones.model.Rol;
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
@Schema(description = "DTO para el registro de un usuario")
public class RegistroDTO {
    @Schema(description = "Nombre de usuario", example = "usuario123")
    @NotNull
    @NotBlank
    private String nombre;
    @Schema(description = "Clave de acceso", example = "contraseña123")
    @NotNull
    @NotBlank
    private String clave;
    @Schema(description = "Rol del usuario", example = "GESTOR")
    @NotNull
    @NotBlank
    private Rol rol;
}
