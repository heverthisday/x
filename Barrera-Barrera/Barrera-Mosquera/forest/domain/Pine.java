package domain;

import java.awt.Color;

/**
 * Representa una variante de árbol (Pino).
 * Tiene un color diferente y forma triangular.
 */
public class Pine extends Tree {

    /**
     * Crea un nuevo pino.
     * @param forest El bosque donde reside.
     * @param row Fila de ubicación.
     * @param column Columna de ubicación.
     */
    public Pine(Forest forest, int row, int column) {
        super(forest, row, column);
        this.color = new Color(0, 100, 0); 
    }

    @Override
    public int shape() {
        return Thing.TRIANGLE;
    }

    @Override
    public void ticTac() {
        super.ticTac(); 
        this.color = new Color(0, 100, 0); 
    }
}