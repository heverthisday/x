package test;

import static org.junit.Assert.*;
import org.junit.Test;
import java.awt.Color;
import domain.*;

/**
 * Pruebas unitarias para validar el comportamiento de la clase Squirrel (Ardilla).
 */
public class SquirrelTest {

    @Test
    public void deberiaCambiarDeColorAlHacerTicTac() {
        Forest forest = new Forest();
        Squirrel scrat = new Squirrel(forest, 5, 5);
        Color colorInicial = scrat.getColor();
        scrat.ticTac();

        assertNotEquals("La ardilla debería cambiar de color al envejecer tras un ticTac", colorInicial, scrat.getColor());
    }

    @Test
    public void deberiaMorirALos10Anios() {
        
        Forest forest = new Forest();
        Squirrel scrat = new Squirrel(forest, 5, 5); 
        
        for (int i = 0; i < 10; i++) {
            scrat.ticTac();
        }
        
        boolean ardillaEncontrada = false;
        for (int r = 0; r < forest.getSize(); r++) {
            for (int c = 0; c < forest.getSize(); c++) {
                if (forest.getThing(r, c) == scrat) {
                    ardillaEncontrada = true;
                }
            }
        }
        assertFalse("La ardilla debería haber desaparecido (muerto) tras 10 años", ardillaEncontrada);
    }
}