package icai.dtc.isw.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GasolineraTest {
    private Gasolinera gasolinera;

    @BeforeEach
    public void setUp() {
        gasolinera = new Gasolinera("nombre", 1,  0, 0, "email", "telefono", true, true);
    }
    @Test
    public void testGetNombre() {
        assertEquals("nombre", gasolinera.getNombre());
    }

    @Test
    public void testGetPrecio() {
        assertEquals(1, gasolinera.getPrecio());
    }

    @Test
    public void testGetPosx() {
        assertEquals(0, gasolinera.getPosx());
    }
    @Test
    public void testGetPosy() {
        assertEquals(0, gasolinera.getPosy());
    }

    @Test
    public void testGetEmail() {
        assertEquals("email", gasolinera.getEmail());
    }

    @Test
    public void testGetTelefono() {
        assertEquals("telefono", gasolinera.getTelefono());
    }

    @Test
    public void testGetServicio() {
        assertEquals(true, gasolinera.getServicio());
    }

    @Test
    public void testGetCargador() {
        assertEquals(true, gasolinera.getCargador());
    }

}
