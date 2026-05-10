package test;

import static org.junit.Assert.*;
import org.junit.Test;
import domain.*;

/**
 * Pruebas unitarias para validar el movimiento fantasmal de la clase Shadow (Sombra).
 */
public class ShadowTest {

    @Test
    public void deberiaMoverseHaciaElNorte() {
        Forest forest = new Forest();
        Shadow sombra = new Shadow(forest, 10, 5);
        
        sombra.ticTac();
    
        assertNull("La posición original (10,5) debe quedar vacía", forest.getThing(10, 5));
        assertTrue("La sombra debe haberse movido a la fila 9", forest.getThing(9, 5) instanceof Shadow);
    }

    @Test
    public void deberiaAparecerEnElSurSiSalePorElNorte() {
        Forest forest = new Forest();
        Shadow sombra = new Shadow(forest, 0, 5);
        
        sombra.ticTac(); 
        
        int ultimaFila = forest.getSize() - 1;
        assertTrue("La sombra debe dar la vuelta y aparecer en el borde sur", forest.getThing(ultimaFila, 5) instanceof Shadow);
    }
}