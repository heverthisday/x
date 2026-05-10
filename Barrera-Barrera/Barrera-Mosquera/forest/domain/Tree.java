package domain;

import java.awt.Color;
import java.util.Random;

/**
 * Representa un árbol en la simulación.
 * Puede quemarse por el fuego, curarse con agua o reproducirse si está rodeado de tierra.
 */
public class Tree extends LivingThing implements Thing {
    private Forest forest;
    protected int row;
    protected int column;    
    protected Color color;
    private int season; 
    private int tictac;
    
    /**
     * Crea un nuevo árbol en el bosque.
     * @param forest El bosque donde reside.
     * @param row Fila de ubicación.
     * @param column Columna de ubicación.
     */
    public Tree(Forest forest, int row, int column) {
        super();
        this.forest = forest;
        this.row = row;
        this.column = column; 
        this.color = Color.PINK; 
        this.season = 0;
        this.tictac = 0;
        this.forest.setThing(row, column, this);    
    }
    
    public final int getRow() { 
        return row; 
    }
    
    public final int getColumn() { 
        return column; 
    }
    
    @Override
    public final Color getColor() { 
        return color; 
    }

    @Override
    public int shape() { 
        return Thing.ROUND; 
    }

    /**
     * Ejecuta las reglas del árbol en un ciclo de tiempo.
     * Respeta el orden de prioridad estricto: Fuego -> Agua -> Reproducción.
     */
    @Override
    public void ticTac() {
        
        if (forest.countNeighborsOfType(row, column, Fire.class) > 0) {
            this.energy -= 25; 
            if (this.energy < 10) { 
                new Fire(forest, row, column); 
            }
            return; 
        }

        
        if (forest.countNeighborsOfType(row, column, Water.class) > 0) {
            this.energy = 100; 
            
            
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;
                    int nr = row + i, nc = column + j;
                    if (nr >= 0 && nr < forest.getSize() && nc >= 0 && nc < forest.getSize()) {
                        if (forest.getThing(nr, nc) instanceof Water) {
                            new Earth(forest, nr, nc);
                            return; 
                        }
                    }
                }
            }
            return;
        }

 
        int vecinosTierra = forest.countNeighborsOfType(row, column, Earth.class);
        int vecinosTotales = forest.countValidNeighbors(row, column);
        
        if (vecinosTierra == vecinosTotales) { 

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;
                    int nr = row + i, nc = column + j;
                    if (nr >= 0 && nr < forest.getSize() && nc >= 0 && nc < forest.getSize()) {
                        if (forest.getThing(nr, nc) instanceof Earth) {
                            new Tree(forest, nr, nc); 
                            return; 
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Elimina el árbol del tablero.
     */
    public void die() {
        forest.setThing(row, column, null);
    }
}