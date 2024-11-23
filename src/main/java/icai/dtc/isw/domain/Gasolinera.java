package icai.dtc.isw.domain;

import java.io.Serializable;
import java.util.Random;

public class Gasolinera implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String nombre;
    private float precio;
    private float posx;
    private float posy;
    private String email;
    private String telefono;
    private boolean servicio;
    private boolean cargador;

    public Gasolinera(String nombre, float precio, float posx, float posy, String email, String telefono, boolean servicio, boolean cargador) {
        this.nombre = nombre;
        this.precio = precio;
        this.posx = posx;
        this.posy = posy;
        this.email = email;
        this.telefono = telefono;
        this.servicio = servicio;
        this.cargador = cargador;
    }

    public float getDistancia(float posx, float posy) {
        return (float) Math.sqrt(Math.pow(this.posx - posx, 2) + Math.pow(this.posy - posy, 2));
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public float getPosx() {
        return posx;
    }

    public float getPosy() {
        return posy;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public boolean getServicio() {
        return servicio;
    }

    public boolean getCargador() {
        return cargador;
    }

    @Override
    public String toString() {
        return
                "Gasolinera: " + nombre + '\n' +
                "Dirección: " + email + '\n' +
                "Precio: " + precio + " €/L" + '\n' +
                "Teléfono: " + telefono + '\n' +
                "Distancia: ";
}
}