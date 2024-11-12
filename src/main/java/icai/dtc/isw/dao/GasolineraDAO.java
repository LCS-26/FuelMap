package icai.dtc.isw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import icai.dtc.isw.domain.Gasolinera;

public class GasolineraDAO {

    public void getGasolineras(ArrayList<Gasolinera> lista) {
        Connection con=ConnectionDAO.getInstance().getConnection();
        try (PreparedStatement pst = con.prepareStatement("SELECT * FROM gasolineras");
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                lista.add(new Gasolinera(rs.getString(1),rs.getFloat(2), rs.getFloat(3), rs.getFloat(4), rs.getString(5), rs.getString(6), rs.getBoolean(7), rs.getBoolean(8)));
            }

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }

    }

    public void getGasolinerasFiltradas(ArrayList<Gasolinera> lista, float distancia, float posx, float posy, float maxPrecio, boolean servicio, boolean cargador) {
        Connection con=ConnectionDAO.getInstance().getConnection();
        String consulta = "SELECT * FROM gasolineras WHERE SQRT(POWER(posx - ?,2) + POWER(posy - ?,2)) <= ? AND precio <= ?";

        if (servicio) {
            consulta += " AND servicio = TRUE";
        }

        if (cargador) {
            consulta += " AND cargador = TRUE";
        }

        try (PreparedStatement pst = con.prepareStatement(consulta)) {
            pst.setString(1, String.valueOf(posx));
            pst.setString(2, String.valueOf(posy));
            pst.setString(3, String.valueOf(distancia));
            pst.setString(4, String.valueOf(maxPrecio));

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Gasolinera(rs.getString(1), rs.getFloat(2), rs.getFloat(3), rs.getFloat(4), rs.getString(5), rs.getString(6), rs.getBoolean(7), rs.getBoolean(8)));
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public Gasolinera getGasolinera(String nombre) {
        Connection con=ConnectionDAO.getInstance().getConnection();
        Gasolinera gas=null;
        String consulta = "SELECT * FROM gasolineras WHERE nombre = ?";

        try (PreparedStatement pst = con.prepareStatement(consulta)) {
            // Asignar el valor del parámetro
            pst.setString(1, nombre);  // El primer parámetro "?" se reemplaza por el valor de 'nombre'

            try (ResultSet rs = pst.executeQuery()) {
                // Procesar el resultado
                if (rs.next()) {
                    gas = new Gasolinera(rs.getString(1), rs.getFloat(2), rs.getFloat(3), rs.getFloat(4), rs.getString(5), rs.getString(6), rs.getBoolean(7), rs.getBoolean(8));  // Obtener los datos de la fila resultante
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return gas;
    }

    public static void main(String[] args) {

        GasolineraDAO gasolineraDAO=new GasolineraDAO();
        ArrayList<Gasolinera> lista= new ArrayList<>();
        gasolineraDAO.getGasolineras(lista);

    }

}

