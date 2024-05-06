package com.arso.pasarela.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyResponse {
    private String token;
    private String id;
    private String nombre;
    private Rol rol;
}
