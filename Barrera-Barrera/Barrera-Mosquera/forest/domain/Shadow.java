package domain;

import java.awt.Color;

/**
 * Representa una sombra mágica/fantasmal en el bosque.
 * Se desplaza constantemente hacia el norte y reaparece por el sur.
 */
public class Shadow implements Thing {
    private Forest forest;
    private int row;
    private int column;

    public Shadow(Forest forest, int row, int column) {
        this.forest = forest;
        this.row = row;
        this.column = column;
        this.forest.setThing(row, column, this);
    }

    @Override
    public int shape() { 
        return Thing.ROUND; 
    }

    @Override
    public Color getColor() { 
        return Color.BLACK; 
    }

    /**
     * Mueve la sombra una posición hacia el norte. Si sale del tablero, reaparece en la parte inferior.
     */
    @Override
    public void ticTac() {
        int nuevaFila = row - 1;
        
        if (nuevaFila < 0) {
            nuevaFila = forest.getSize() - 1;
        }

        if (forest.isEmpty(nuevaFila, column)) {
            forest.setThing(row, column, null); 
            row = nuevaFila;
            forest.setThing(row, column, this); 
        }
    }
}