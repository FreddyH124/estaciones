package org.arso.persistence.jpa;

import org.arso.interfaces.IIdentificable;
import org.arso.model.Reserva;
import org.arso.utils.DateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class ReservaEntidad implements IIdentificable {
    @Id
    @GeneratedValue
    private String id;
    @Column(name = "bicicleta")
    private String idBicicleta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creada;

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

    public Date getCreada() {
        return creada;
    }

    public void setCreada(Date creada) {
        this.creada = creada;
    }

    public Reserva toModelo(){
        Reserva reserva = new Reserva();
        reserva.setId(this.id);
        reserva.setIdBicicleta(this.idBicicleta);
        LocalDateTime dateTime = DateTimeConverter.convertToLocalDateTime(this.creada);
        reserva.setCreada(dateTime);
        reserva.setCaducidad(dateTime.plusMinutes(30));

        return reserva;
    }
}
