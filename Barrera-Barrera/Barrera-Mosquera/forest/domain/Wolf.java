package domain;

import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;

/**
 * Representa un lobo en el simulador.
 * Se mueve en un radio cercano de 5x5 y tiene una esperanza de vida de 25 ciclos.
 */
public class Wolf extends LivingThing implements Thing {
    private Forest forest;
    private int row;
    private int column;
    private Color color;
    
    public Wolf(Forest forest, int row, int column) {
        super();
        this.forest = forest;
        this.row = row;
        this.column = column;
        this.color = new Color(0, 0, 0); 
        this.forest.setThing(row, column, this);
    }
    
    @Override
    public int shape() { 
        return Thing.SQUARE; 
    }
    
    @Override
    public Color getColor() { 
        return color; 
    }
    
    @Override
    public void ticTac() {
        years++; 
        // Aclara ligeramente su color oscuro al envejecer
        int r = Math.min(255, color.getRed() + 8);
        int g = Math.min(255, color.getGreen() + 8);
        int b = Math.min(255, color.getBlue() + 8);
        this.color = new Color(r, g, b);

        if (years >= 25) {
            die();
            return; 
        }

        // Movimiento Rango de 2 casillas
        ArrayList<int[]> lugaresCercanosVacios = new ArrayList<>();

        for (int f = row - 2; f <= row + 2; f++) {
            for (int c = column - 2; c <= column + 2; c++) {
                if (f >= 0 && f < forest.getSize() && c >= 0 && c < forest.getSize()) {
                    if (forest.isEmpty(f, c)) {
                        lugaresCercanosVacios.add(new int[]{f, c});
                    }
                }
            }
        }

        if (!lugaresCercanosVacios.isEmpty()) {
            Random rand = new Random();
            int[] destino = lugaresCercanosVacios.get(rand.nextInt(lugaresCercanosVacios.size()));
            
            forest.setThing(row, column, null);
            row = destino[0];
            column = destino[1];
            forest.setThing(row, column, this); 
        }
    }

    public void die() { 
        forest.setThing(row, column, null); 
    }
}