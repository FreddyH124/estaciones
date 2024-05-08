package com.arso.estaciones.rest;

import com.arso.estaciones.interfaces.IServicioEstaciones;
import com.arso.estaciones.model.DTO.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.arso.estaciones.repository.EntidadNoEncontrada;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("estaciones")
@Tag(name = "Estaciones", description = "API para operaciones relacionadas con estaciones de bicicletas")
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

    @Operation(
            summary = "Dar de alta una estación",
            description = "Crea una nueva estación con la información proporcionada en el DTO."
    )
    @PreAuthorize("hasAuthority('GESTOR')")
    @PostMapping(value = "/alta")
    public ResponseEntity<URI> altaEstacion(@Valid @RequestBody AltaEstacionDTO dto) {
        String id = servicioEstaciones.altaEstacion(dto);
        URI url = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(url).body(url);
    }

    @Operation(
            summary = "Dar de alta bicicletas",
            description = "Da de alta una bicicletas en la estación especificada."
    )
    @PreAuthorize("hasAuthority('GESTOR')")
    @PostMapping("/bicicletas/alta")
    public ResponseEntity<URI> altaBicicletas(@Valid @RequestBody AltaBicicletaDTO dto) throws EntidadNoEncontrada {
        String id = servicioEstaciones.altaBicicleta(dto);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(url).body(url);
    }

    @Operation(
            summary = "Dar de baja una bicicleta",
            description = "Da de baja una bicicleta en la estación especificada."
    )
    @PreAuthorize("hasAuthority('GESTOR')")
    @PostMapping("/bicicletas/{id}/baja")
    public ResponseEntity<Void> bajaBicicleta(
            @Parameter(description = "ID de la bicicleta", example = "660ee344ef8055670e95a1bb") @PathVariable String id,
            @Parameter(description = "Motivo de la baja", example = "Mantenimiento") @RequestParam String motivo) throws EntidadNoEncontrada, JsonProcessingException {

        servicioEstaciones.bajaBicicleta(id, motivo);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obtener bicicletas por estación",
            description = "Obtiene todas las bicicletas asociadas a la estación especificada."
    )
    @PreAuthorize("hasAuthority('GESTOR')")
    @GetMapping("/{id}/bicicletas")
    public PagedModel<EntityModel<BicicletaDTO>> getBicicletasByEstacion(
            @Parameter(description = "ID de la estación", example = "6616cee50a78571ff80a8ff3") @PathVariable String id,
            Pageable pageable) {
        Page<BicicletaDTO> result = servicioEstaciones.getAllBiciletas(id, pageable).map(bicicleta -> {
            BicicletaDTO dto =DTOHelper.fromEntity(bicicleta);
            return dto;
        });
        return pagedBicicletaResourcesAssembler.toModel(result, bicicletaDTO -> {
            EntityModel<BicicletaDTO> model = EntityModel.of(bicicletaDTO);
            try {
                model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                                .methodOn(ControladorEstaciones.class)
                                .bajaBicicleta(bicicletaDTO.getId(), ""))
                        .withRel("baja"));
            }catch (Exception e){
                e.printStackTrace();
            }
            return model;
        });
    }

    @Operation(
            summary = "Obtener todas las estaciones",
            description = "Obtiene todas las estaciones disponibles."
    )
    @PreAuthorize("hasAnyAuthority('GESTOR','NORMAL')")
    @GetMapping()
    public PagedModel<EntityModel<EstacionDTO>> getEstaciones(Pageable pageable) {
        Page<EstacionDTO> result = servicioEstaciones.getAllEstaciones(pageable).map(estacion -> {
            EstacionDTO dto =DTOHelper.fromEntity(estacion);
            return dto;
        });

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

    @Operation(
            summary = "Obtener una estación por ID",
            description = "Obtiene la información detallada de una estación por su ID."
    )
    @PreAuthorize("hasAnyAuthority('GESTOR','NORMAL')")
    @GetMapping("/{id}")
    public EntityModel<EstacionDTO> getEstacionById(
            @Parameter(description = "ID de la estación", example = "6616cee50a78571ff80a8ff3") @PathVariable String id) throws EntidadNoEncontrada {
        EstacionDTO dto = DTOHelper.fromEntity(servicioEstaciones.getEstacion(id));

        EntityModel<EstacionDTO> model =  EntityModel.of(dto);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(ControladorEstaciones.class)
                        .getEstacionById(id))
                .withSelfRel());
        return model;
    }

    @Operation(
            summary = "Obtener bicicletas disponibles por estación",
            description = "Obtiene todas las bicicletas disponibles asociadas a la estación especificada."
    )
    @PreAuthorize("hasAnyAuthority('GESTOR','NORMAL')")
    @GetMapping("/{id}/bicicletas/disponibles")
    public PagedModel<EntityModel<BicicletaDTO>> getBicicletasDisponiblesByEstacion(
            @Parameter(description = "ID de la estación", example = "6616cee50a78571ff80a8ff3") @PathVariable String id, Pageable pageable) {
        Page<BicicletaDTO> result = servicioEstaciones.getBicicletasDisponibles(id, pageable).map(bicicleta -> {
            BicicletaDTO dto =DTOHelper.fromEntity(bicicleta);
            return dto;
        });

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

    @Operation(
            summary = "Estacionar bicicleta",
            description = "Estaciona una bicicleta en la estación especificada."
    )
    @PostMapping("/estacionadas/nuevo")
    public ResponseEntity<Void> estacionar(@Valid @RequestBody EstacionarBicicletaDTO dto) throws EntidadNoEncontrada {
        servicioEstaciones.estacionarBicicleta(dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Retirar bicicleta",
            description = "Retira una bicicleta de la estación especificada"
    )
    @PostMapping("/bicicletas/retirar/{idBicicleta}")
    public ResponseEntity<Void> retirar(
            @Parameter(description = "ID de la bicicleta", example = "6616cee50a78571ff80a8ff3") @PathVariable String idBicicleta) throws EntidadNoEncontrada {
        servicioEstaciones.retirarBicicleta(idBicicleta);
        return ResponseEntity.noContent().build();
    }

}
