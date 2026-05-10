package domain;

import java.awt.Color;

/**
 * Representa el agua en el simulador Forest Fire.
 * Cura los árboles y apaga el fuego. Se mueve hacia el sur si está aislada.
 */
public class Water implements Thing {
    private Forest forest;
    private int row;
    private int column;

    /**
     * Crea un nuevo bloque de agua en el bosque.
     * @param forest El bosque donde reside.
     * @param row Fila de ubicación.
     * @param column Columna de ubicación.
     */
    public Water(Forest forest, int row, int column) {
        this.forest = forest;
        this.row = row;
        this.column = column;
        this.forest.setThing(row, column, this);
    }

    @Override 
    public int shape() { 
        return Thing.SQUARE; 
    }
    
    @Override 
    public Color getColor() { 
        return Color.BLUE; 
    }

    /**
     * Acción de tiempo del agua.
     * Intenta moverse al sur si está rodeada únicamente de tierra.
     */
    @Override
    public void ticTac() {
        int vecinosTierra = forest.countNeighborsOfType(row, column, Earth.class);
        int vecinosTotales = forest.countValidNeighbors(row, column);

        if (vecinosTierra == vecinosTotales) {
            int targetRow = row + 1; 
            
            if (targetRow < forest.getSize()) {
                if (forest.getThing(targetRow, column) instanceof Earth) {
                    int oldRow = row;
                    int oldCol = column;
                    
                    this.row = targetRow;
                    forest.setThing(targetRow, column, this); 
                    
                    new Earth(forest, oldRow, oldCol); 
                }
            }
        }
    }
}