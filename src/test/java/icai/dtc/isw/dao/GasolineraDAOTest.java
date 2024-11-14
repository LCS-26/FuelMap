package icai.dtc.isw.dao;

import icai.dtc.isw.domain.Gasolinera;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class GasolineraDAOTest {
    @Mock
    private GasolineraDAO gasolineraDAO;  // Simulación de
    @InjectMocks
    private GasolineraDAOTest gasolineraDAOTest; // Inyectar mocks en esta clase

    @BeforeEach
    public void setUp() {
        // Inicializar los mocks
        MockitoAnnotations.openMocks(this);
        // Inicializar manualmente el gasolineraDAO
        gasolineraDAO = mock(GasolineraDAO.class);
    }

    @Test
    public void testGetGasolineraById() {
        // Crear un objeto de gasolinera simulado
        Gasolinera expectedGasolinera = new Gasolinera("nombre", 1,  0, 0, "email", "telefono", true, true);

        // Simular el comportamiento del método getGasolinera
        when(gasolineraDAO.getGasolinera("nombre")).thenReturn(expectedGasolinera);

        // Llamar al método
        Gasolinera actualGasolinera = gasolineraDAO.getGasolinera("nombre");

        // Verificar que el resultado es correcto
        assertNotNull(actualGasolinera);
        assertEquals("nombre", actualGasolinera.getNombre());
        assertEquals(1, actualGasolinera.getPrecio());
        assertEquals(0, actualGasolinera.getPosx());
        assertEquals(0, actualGasolinera.getPosy());
        assertEquals("email", actualGasolinera.getEmail());
        assertEquals("telefono", actualGasolinera.getTelefono());
        assertEquals(true, actualGasolinera.getServicio());
        assertEquals(true, actualGasolinera.getCargador());

        // Verificar que el método getGasolinera fue llamado
        verify(gasolineraDAO, times(1)).getGasolinera("nombre");
    }
}
