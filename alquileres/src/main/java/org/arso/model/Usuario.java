package org.arso.model;

import org.arso.interfaces.IEntidadParseable;
import org.arso.interfaces.IIdentificable;
import org.arso.persistence.jpa.AlquilerEntidad;
import org.arso.persistence.jpa.ReservaEntidad;
import org.arso.persistence.jpa.UsuarioEntidad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Usuario implements IIdentificable, IEntidadParseable<UsuarioEntidad> {
    private String id;
    private List<Reserva> reservas;
    private List<Alquiler> alquileres;

    public Usuario(){
        reservas = new ArrayList<>();
        alquileres = new ArrayList<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void addReserva(Reserva reserva){ reservas.add(reserva);}

    public void removeReserva(Reserva reserva) { reservas.remove(reserva); }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public List<Alquiler> getAlquileres() {
        return alquileres;
    }

    public void addAlquiler(Alquiler alquiler) { alquileres.add(alquiler); }

    public void removeAlquiler(Alquiler alquiler) { alquileres.remove(alquiler); }

    public void setAlquileres(List<Alquiler> alquileres) {
        this.alquileres = alquileres;
    }

    public long getReservasCaducadas(){
        return reservas.stream()
                .filter(Reserva::isCaducada)
                .count();
    }

    public long getTiempoUsoHoy(){
        return alquileres.stream()
                .filter(alquiler -> alquiler.getInicio().equals(LocalDateTime.now()))
                .mapToLong(Alquiler::getTiempoAlquiler)
                .sum();
    }

    public long getTiempoUsoSemanal(){
        LocalDateTime haceUnaSemana = LocalDateTime.now().minusDays(6);
        LocalDateTime manana = LocalDateTime.now().plusDays(1);
        return alquileres.stream()
                .filter(alquiler -> alquiler.getInicio().isAfter(haceUnaSemana) && alquiler.getInicio().isBefore(manana))
                .mapToLong(Alquiler::getTiempoAlquiler)
                .sum();
    }

    public boolean superaTiempoUso(){
        return getTiempoUsoHoy() >= 60 || getTiempoUsoSemanal() >= 180;
    }

    public Reserva getReservaActiva(){
        Optional<Reserva> ultima = reservas.stream()
                .filter(Reserva::isActiva)
                .max(Comparator.comparing(Reserva::getCreada));

        return ultima.orElse(null);
    }

    public Alquiler getAlquilerActivo(){
        Optional<Alquiler> ultimo = alquileres.stream()
                .filter(Alquiler::isActivo)
                .max(Comparator.comparing(Alquiler::getInicio));

        return ultimo.orElse(null);
    }

    public boolean isBloqueado(){
        long count = reservas.stream()
                .filter(Reserva::isCaducada)
                .count();

        return count >= 3;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", reservas=" + reservas +
                ", alquileres=" + alquileres +
                '}';
    }


    @Override
    public UsuarioEntidad toEntidad() {
        UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
        usuarioEntidad.setId(this.id);

        List<ReservaEntidad> reservasEntidad = new ArrayList<>();
        for(Reserva reserva : reservas){
            reservasEntidad.add(reserva.toEntidad());
        }
        usuarioEntidad.setReservas(reservasEntidad);

        List<AlquilerEntidad> alquileresEntidad = new ArrayList<>();
        for(Alquiler alquiler : alquileres){
            alquileresEntidad.add(alquiler.toEntidad());
        }
        usuarioEntidad.setAlquileres(alquileresEntidad);

        return usuarioEntidad;
    }
}
