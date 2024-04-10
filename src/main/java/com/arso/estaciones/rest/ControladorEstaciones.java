package com.arso.estaciones.rest;

import com.arso.estaciones.interfaces.IServicioEstaciones;
import com.arso.estaciones.model.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("estaciones")
public class ControladorEstaciones {
    private IServicioEstaciones servicioEstaciones;
    @Autowired
    private PagedResourcesAssembler<BicicletaDTO> pagedBicicletaResourcesAssembler;
    @Autowired
    private PagedResourcesAssembler<EstacionDTO> pagedEstacionResourcesAssembler;

    @Autowired
    public ControladorEstaciones(IServicioEstaciones servicioEstaciones) {
        this.servicioEstaciones = servicioEstaciones;
    }

    @PreAuthorize("hasAuthority('GESTOR')")
    @PostMapping("/alta")
    public ResponseEntity<Void> altaEstacion(@RequestBody AltaEstacionDTO dto) {
        String id = servicioEstaciones.altaEstacion(dto);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(url).build();
    }

    @PreAuthorize("hasAuthority('GESTOR')")
    @PostMapping("/bicicletas/alta")
    public ResponseEntity<Void> altaBicicletas(@RequestBody AltaBicicletaDTO dto) {
        String id = servicioEstaciones.altaBicicleta(dto);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(url).build();
    }

    @PreAuthorize("hasAuthority('GESTOR')")
    @PostMapping("/bicicletas/baja")
    public ResponseEntity<Void> bajaBicicleta(@RequestBody BajaBicicletaDTO dto) {
        servicioEstaciones.bajaBicicleta(dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('GESTOR')")
    @GetMapping("/{id}/bicicletas")
    public PagedModel<EntityModel<BicicletaDTO>> getBicicletasByEstacion(@PathVariable String id, Pageable pageable) {
        Page<BicicletaDTO> result = servicioEstaciones.getAllBiciletas(id, pageable);
        return pagedBicicletaResourcesAssembler.toModel(result, bicicletaDTO -> {
            EntityModel<BicicletaDTO> model = EntityModel.of(bicicletaDTO);
            try {
                model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                                .methodOn(ControladorEstaciones.class)
                                .bajaBicicleta(null))
                        .withSelfRel());
            }catch (Exception e){
                e.printStackTrace();
            }
            return model;
        });
    }

    @PreAuthorize("hasAnyAuthority('GESTOR','NORMAL')")
    @GetMapping()
    public PagedModel<EntityModel<EstacionDTO>> getEstaciones(Pageable pageable) {
        Page<EstacionDTO> result = servicioEstaciones.getAllEstaciones(pageable);
        return pagedEstacionResourcesAssembler.toModel(result, estacionDto -> {
            EntityModel<EstacionDTO> model = EntityModel.of(estacionDto);
            try {
                model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                                .methodOn(ControladorEstaciones.class)
                                .getEstacionById(estacionDto.getId()))
                        .withSelfRel());
            }catch (Exception e){
                e.printStackTrace();
            }
            return model;
        });
    }

    @PreAuthorize("hasAnyAuthority('GESTOR','NORMAL')")
    @GetMapping("/{id}")
    public EntityModel<EstacionDTO> getEstacionById(@PathVariable String id) {
        EstacionDTO dto = servicioEstaciones.getEstacion(id);

        EntityModel<EstacionDTO> model =  EntityModel.of(dto);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(ControladorEstaciones.class)
                        .getEstacionById(id))
                .withSelfRel());
        return model;
    }

    @PreAuthorize("hasAnyAuthority('GESTOR','NORMAL')")
    @GetMapping("/{id}/bicicletas/disponibles")
    public PagedModel<EntityModel<BicicletaDTO>> getBicicletasDisponiblesByEstacion(@PathVariable String id, Pageable pageable) {
        Page<BicicletaDTO> result = servicioEstaciones.getBicicletasDisponibles(id, pageable);
        return pagedBicicletaResourcesAssembler.toModel(result, bicicletaDTO -> {
            EntityModel<BicicletaDTO> model = EntityModel.of(bicicletaDTO);
            try {
                model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                                .methodOn(ControladorEstaciones.class)
                                .getEstacionById(bicicletaDTO.getId()))
                        .withSelfRel());
            }catch (Exception e){
                e.printStackTrace();
            }
            return model;
        });
    }

    @PreAuthorize("hasAnyAuthority('GESTOR','NORMAL')")
    @PostMapping("/estacionar")
    ResponseEntity<Void> estacionar(@RequestBody EstacionarBicicletaDTO dto) {
        servicioEstaciones.estacionarBicicleta(dto);
        return ResponseEntity.ok().build();
    }

}
