package com.arso.estaciones.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BajaBicicletaDTO {
    private String idBicicleta;
    private String motivo;
}
