package org.arso.communication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class StartupServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        ConsumidorEventos consumidor = new ConsumidorEventos();
        try {
            consumidor.Escuchar();
        } catch (Exception e) {
            throw new ServletException("Failed to start event consumer", e);
        }
    }
}
