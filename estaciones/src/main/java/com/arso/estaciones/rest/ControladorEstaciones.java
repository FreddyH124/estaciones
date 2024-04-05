package com.arso.estaciones.rest;

import com.arso.estaciones.interfaces.IServicioEstaciones;
import com.arso.estaciones.model.DTO.BicicletaDTO;
import com.arso.estaciones.model.DTO.EstacionDTO;
import com.arso.estaciones.model.Estacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}/bicicletas")
    public PagedModel<EntityModel<BicicletaDTO>> getBicicletasByEstacion(@PathVariable String id, Pageable pageable) {
        Page<BicicletaDTO> result = servicioEstaciones.getAllBiciletas(id, pageable);
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
}
