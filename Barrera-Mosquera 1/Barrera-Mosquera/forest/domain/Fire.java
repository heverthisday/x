package domain;

import java.awt.Color;

/**
 * Representa el fuego en el simulador Forest Fire.
 * Consume los árboles y puede extinguirse por el agua o el tiempo.
 */
public class Fire implements Thing {
    private Forest forest;
    private int row;
    private int column;
    private int ciclosVida;

    /**
     * Crea un nuevo fuego en el bosque.
     * @param forest El bosque donde reside.
     * @param row Fila de ubicación.
     * @param column Columna de ubicación.
     */
    public Fire(Forest forest, int row, int column) {
        this.forest = forest;
        this.row = row;
        this.column = column;
        this.ciclosVida = 0;
        this.forest.setThing(row, column, this);
    }

    @Override 
    public int shape() { 
        return Thing.SQUARE; 
    }
    
    @Override 
    public Color getColor() { 
        return Color.RED; 
    }

    /**
     * Acción de tiempo del fuego. Verifica si debe extinguirse por agua o por aislamiento.
     */
    @Override
    public void ticTac() {

        if (forest.countNeighborsOfType(row, column, Water.class) > 0) {
            new Earth(forest, row, column); 
            return; 
        }

        int vecinosTierra = forest.countNeighborsOfType(row, column, Earth.class);
        int vecinosTotales = forest.countValidNeighbors(row, column);

        if (vecinosTierra == vecinosTotales) {
            ciclosVida++;
            if (ciclosVida >= 5) { 
                new Earth(forest, row, column); 
            }
        } else {
            
            ciclosVida = 0; 
        }
    }
}