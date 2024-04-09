package com.arso.estaciones.model.DTO;

import com.arso.estaciones.model.Bicicleta;
import com.arso.estaciones.model.Estacion;

public class DTOHelper {

    public static EstacionDTO fromEntity(Estacion estacion) {
        return new EstacionDTO(estacion.getId(), estacion.getNombre(), estacion.getPuestos());
    }

    public static BicicletaDTO fromEntity(Bicicleta bicicleta) {
        return new BicicletaDTO(bicicleta.getId(), bicicleta.isDisponible(), bicicleta.getModelo());
    }
}
