package icai.dtc.isw.controler;

import java.util.ArrayList;

import icai.dtc.isw.dao.GasolineraDAO;
import icai.dtc.isw.domain.Gasolinera;

public class GasolineraController {
    GasolineraDAO gasolineraDAO=new GasolineraDAO();

    public void getGasolineras(ArrayList<Gasolinera> lista) {
        gasolineraDAO.getGasolineras(lista);
    }

    public Gasolinera getGasolinera(String nombre) {
        return(gasolineraDAO.getGasolinera(nombre));
    }

    public ArrayList<Gasolinera> getGasolinerasFiltradas(float distancia, float posx, float posy, float maxPrecio, boolean servicio, boolean cargador) {
        ArrayList<Gasolinera> lista=new ArrayList<Gasolinera>();
        gasolineraDAO.getGasolinerasFiltradas(lista, distancia, posx, posy, maxPrecio, servicio, cargador);
        return lista;
    }

}










