package domain;

import java.util.Random;

/**
 * Representa la variante del bosque diseñada específicamente para el Forest Fire Model.
 * Se encarga de inicializar el tablero sin espacios vacíos.
 */
public class FireForest extends Forest {

    /**
     * Constructor del bosque de fuego.
     */
    public FireForest() {
        super();
        prepararEscenario();
    }

    /**
     * Genera el escenario base llenando todo con Tierra y una probabilidad de Árboles.
     */
    private void prepararEscenario() {
        Random rand = new Random();

        for (int r = 0; r < getSize(); r++) {
            for (int c = 0; c < getSize(); c++) {
                new Earth(this, r, c);
            }
        }

        for (int r = 0; r < getSize(); r++) {
            for (int c = 0; c < getSize(); c++) {
                if (rand.nextInt(100) < 30) { // 30% de probabilidad
                    new Tree(this, r, c);
                }
            }
        }
    }
}