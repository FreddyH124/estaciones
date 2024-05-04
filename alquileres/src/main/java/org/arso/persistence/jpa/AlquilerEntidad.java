package org.arso.persistence.jpa;

import org.arso.interfaces.IIdentificable;
import org.arso.model.Alquiler;
import org.arso.utils.DateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class AlquilerEntidad implements IIdentificable {
    @Id
    @GeneratedValue
    private String id;
    @Column(name = "bicicleta")
    private String idBicicleta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fin;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String _id) {
        this.id = _id;
    }

    public String getIdBicicleta() {
        return idBicicleta;
    }

    public void setIdBicicleta(String idBicicleta) {
        this.idBicicleta = idBicicleta;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public Alquiler toModelo(){
        Alquiler alquiler = new Alquiler();
        alquiler.setId(this.id);
        alquiler.setIdBicicleta(this.idBicicleta);
        alquiler.setInicio(DateTimeConverter.convertToLocalDateTime(this.inicio));
        if(this.fin != null){
            alquiler.setFin(DateTimeConverter.convertToLocalDateTime(this.fin));
        }

        return alquiler;
    }
}
