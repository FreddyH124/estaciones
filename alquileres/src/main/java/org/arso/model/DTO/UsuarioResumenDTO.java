package org.arso.model.DTO;

import org.arso.model.Alquiler;
import org.arso.model.Reserva;

import java.util.List;

public class UsuarioResumenDTO {
    private List<Reserva> reservas;
    private List<Alquiler> alquileres;
    private boolean bloqueado;
    private long tiempoUsoHoy;
    private long tiempoUsoSemanal;


    public UsuarioResumenDTO(List<Reserva> reservas, List<Alquiler> alquileres, boolean bloqueado, long tiempoUsoHoy, long tiempoUsoSemanal) {
        this.reservas = reservas;
        this.bloqueado = bloqueado;
        this.alquileres = alquileres;
        this.tiempoUsoHoy = tiempoUsoHoy;
        this.tiempoUsoSemanal = tiempoUsoSemanal;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public List<Alquiler> getAlquileres() {
        return alquileres;
    }

    public void setAlquileres(List<Alquiler> alquileres) {
        this.alquileres = alquileres;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public long getTiempoUsoHoy() {
        return tiempoUsoHoy;
    }

    public void setTiempoUsoHoy(long tiempoUsoHoy) {
        this.tiempoUsoHoy = tiempoUsoHoy;
    }

    public long getTiempoUsoSemanal() {
        return tiempoUsoSemanal;
    }

    public void setTiempoUsoSemanal(long tiempoUsoSemanal) {
        this.tiempoUsoSemanal = tiempoUsoSemanal;
    }
}
