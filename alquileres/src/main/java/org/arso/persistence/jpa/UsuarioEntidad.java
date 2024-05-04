package org.arso.persistence.jpa;

import org.arso.interfaces.IIdentificable;
import org.arso.model.Alquiler;
import org.arso.model.Reserva;
import org.arso.model.Usuario;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UsuarioEntidad implements IIdentificable {
    @Id
    private String id;
    @OneToMany(cascade = CascadeType.MERGE)
    private List<AlquilerEntidad> alquileres;
    @OneToMany(cascade = CascadeType.MERGE)
    private List<ReservaEntidad> reservas;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String _id) {
        this.id = _id;
    }

    public List<AlquilerEntidad> getAlquileres() {
        return alquileres;
    }

    public void setAlquileres(List<AlquilerEntidad> alquileres) {
        this.alquileres = alquileres;
    }

    public List<ReservaEntidad> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservaEntidad> reservas) {
        this.reservas = reservas;
    }

    public Usuario toModelo(){
        Usuario usuario = new Usuario();
        usuario.setId(this.id);

        List<Reserva> reservasModelo = new ArrayList<>();
        for(ReservaEntidad reserva : reservas){
            reservasModelo.add(reserva.toModelo());
        }
        usuario.setReservas(reservasModelo);

        List<Alquiler> alquileresModelo = new ArrayList<>();
        for(AlquilerEntidad alquiler : alquileres){
            alquileresModelo.add(alquiler.toModelo());
        }
        usuario.setAlquileres(alquileresModelo);

        return usuario;
    }
}
