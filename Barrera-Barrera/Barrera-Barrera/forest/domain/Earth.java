package domain;

import java.awt.Color;
import java.util.Random;

/**
 * Representa la tierra en el simulador Forest Fire.
 * Es el elemento base sobre el cual aparecen otros elementos y genera vida o destrucción por probabilidad.
 */
public class Earth implements Thing {
    private Forest forest;
    private int row;
    private int column;
    private static final Random rand = new Random();

    /**
     * Crea un nuevo bloque de tierra en el bosque.
     * @param forest El bosque donde reside.
     * @param row Fila de ubicación.
     * @param column Columna de ubicación.
     */
    public Earth(Forest forest, int row, int column) {
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
        return new Color(210, 180, 140); 
    }

    /**
     * Acción de tiempo de la tierra.
     * Evalúa las probabilidades de generar fuego o agua en cada ciclo.
     */
    @Override
    public void ticTac() {
        int prob = rand.nextInt(100); 
        
        // Regla: 10% de probabilidad de generar Fuego (0 al 9)
        if (prob < 10) {
            new Fire(forest, row, column); 
        } 
        // Regla: 5% de probabilidad de generar Agua (10 al 14)
        else if (prob < 15) {
            new Water(forest, row, column); 
        }
    }
}