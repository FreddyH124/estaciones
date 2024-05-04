package org.arso.communication.retrofit.estaciones.model;


import java.util.*;

public class Estacion {
    private String id;
    private String nombre;
    private int puestos;
    private String direccion;
    private Coordenada coordenadas;
    private Date fechaAlta;
    //private List<SitioTuristico> sitiosTuristicos;
    private List<Bicicleta> bicicletas;


    public Estacion(String nombre, int puestos, String direccion, Coordenada coordenadas) {
        this.nombre = nombre;
        this.puestos = puestos;
        this.direccion = direccion;
        this.coordenadas = coordenadas;
        this.fechaAlta = new Date();
        this.bicicletas = new ArrayList<>();
    }

    public Estacion() {
        this.fechaAlta = new Date();
        this.bicicletas = new ArrayList<>();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuestos() {
        return puestos;
    }

    public void setPuestos(int puestos) {
        this.puestos = puestos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Coordenada getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenada coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public List<Bicicleta> getBicicletas() {
        return bicicletas;
    }

    public void setBicicletas(List<Bicicleta> bicicletas) {
        this.bicicletas = bicicletas;
    }

    public void addBicicleta(Bicicleta bicicleta) {
        boolean bicicletaYaExiste = false;
        if(bicicleta.getId() != null){
            bicicletaYaExiste = bicicletas.stream()
                    .anyMatch(b -> b.getId().equals(bicicleta.getId()));
        }


        if (!bicicletaYaExiste) {
            bicicletas.add(bicicleta);
        }
    }


    public void removeBicicleta(Bicicleta bicicleta) {
        bicicletas.removeIf(b -> b.getId().equals(bicicleta.getId()));
    }



    public boolean hayHueco(){
        return bicicletas.size() < puestos-1;
    }
}
