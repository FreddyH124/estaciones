package com.arso.estaciones.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstacionarBicicletaDTO {
    private String idEstacion;
    private String idBicicleta;
}
