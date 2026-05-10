package test;

import static org.junit.Assert.*;
import org.junit.Test;
import java.awt.Color;
import domain.*;

/**
 * Pruebas unitarias para validar el comportamiento de la clase Wolf (Lobo).
 */
public class WolfTest {

    @Test
    public void deberiaNacerNegroYCuadrado() {
        Forest forest = new Forest();
        Wolf lobito = new Wolf(forest, 1, 1);
        
        assertEquals("El lobo debe ser SQUARE (2)", Thing.SQUARE, lobito.shape());
        assertEquals("El lobo debe nacer de color negro", Color.BLACK, lobito.getColor());
    }

    @Test
    public void deberiaVolverseGrisConElTiempo() {
        Forest forest = new Forest();
        Wolf lobito = new Wolf(forest, 1, 1);

        lobito.ticTac();
    
        Color nuevoColor = new Color(8, 8, 8); 
        assertEquals("El lobo debe aclararse un poco tras un año", nuevoColor, lobito.getColor());
    }

    @Test
    public void pruebaDeAceptacion_LoboMuereALos25Anios() {

        Forest forest = new Forest();
        Wolf lobo1 = new Wolf(forest, 5, 5);
        Wolf lobo2 = new Wolf(forest, 6, 6);
        
        for (int i = 0; i < 24; i++) {
            lobo1.ticTac();
            lobo2.ticTac();
        }
        
        boolean vivosEn24 = false;
        for (int r = 0; r < forest.getSize(); r++) {
            for (int c = 0; c < forest.getSize(); c++) {
                if (forest.getThing(r, c) == lobo1) vivosEn24 = true;
            }
        }
        assertTrue("En el año 24, el lobo debería seguir vivo", vivosEn24);
        
        
        lobo1.ticTac();
        lobo2.ticTac();
        
        boolean vivosEn25 = false;
        for (int r = 0; r < forest.getSize(); r++) {
            for (int c = 0; c < forest.getSize(); c++) {
                if (forest.getThing(r, c) == lobo1 || forest.getThing(r, c) == lobo2) {
                    vivosEn25 = true;
                }
            }
        }
        assertFalse("En el año 25, ambos lobos deberían haber muerto y desaparecido", vivosEn25);
    }
}