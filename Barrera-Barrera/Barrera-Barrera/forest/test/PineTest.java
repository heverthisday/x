package test;

import static org.junit.Assert.*;
import org.junit.Test;
import java.awt.Color;
import domain.*;

/**
 * Pruebas unitarias para validar las características específicas del Pino.
 */
public class PineTest {

    @Test
    public void deberiaTenerFormaDeTriangulo() {
        Forest forest = new Forest();
        Pine pino = new Pine(forest, 0, 0);

        assertEquals("El pino debe tener forma TRIANGLE (3)", Thing.TRIANGLE, pino.shape());
    }

    @Test
    public void deberiaNacerColorVerdeOscuro() {
        Forest forest = new Forest();
        Pine pino = new Pine(forest, 0, 0);
        Color verdeOscuro = new Color(0, 100, 0);
        
        assertEquals("El pino debe nacer verde oscuro", verdeOscuro, pino.getColor());
    }

    @Test
    public void pruebaDeAceptacion_PinoPerenneNoCambiaDeColor() {

        Forest forest = new Forest();
        Color verdeOscuro = new Color(0, 100, 0);
        Pine pino1 = new Pine(forest, 1, 1);
        Pine pino2 = new Pine(forest, 2, 2);
        
        for(int i = 0; i < 5; i++) {
            pino1.ticTac();
            pino2.ticTac();
        }
        assertEquals("El pino 1 no debe cambiar de color al envejecer", verdeOscuro, pino1.getColor());
        assertEquals("El pino 2 no debe cambiar de color al envejecer", verdeOscuro, pino2.getColor());
    }
}