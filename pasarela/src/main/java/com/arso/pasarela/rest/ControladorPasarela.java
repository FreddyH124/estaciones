package com.arso.pasarela.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pasarela")
public class ControladorPasarela {

    @GetMapping
    public ResponseEntity<String> findById() {
        return ResponseEntity.ok("todo ok");
    }
}
