package domain;

import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;

/**
 * Representa una ardilla en el simulador.
 * Puede reproducirse con otras ardillas y se mueve aleatoriamente por los espacios vacíos.
 */
public class Squirrel extends LivingThing implements Thing {
    private Forest forest;
    private int row;
    private int column;
    private Color color;
    private boolean yaFuePadreEnEsteTurno;

    /**
     * Crea una nueva ardilla.
     */
    public Squirrel(Forest forest, int row, int column) {
        super();
        this.forest = forest;
        this.row = row;
        this.column = column;
        this.color = new Color(139, 69, 19); 
        this.yaFuePadreEnEsteTurno = false;
        this.forest.setThing(row, column, this);
    }

    @Override
    public int shape() { 
        return Thing.TRIANGLE; 
    }

    @Override
    public Color getColor() { 
        return color; 
    }

    /**
     * Ejecuta las acciones de la ardilla: envejecimiento, reproducción y movimiento.
     */
    @Override
    public void ticTac() {
        years++; 
        int r = Math.min(255, color.getRed() + 15);
        int g = Math.min(255, color.getGreen() + 18);
        this.color = new Color(r, g, 0);
        
        if (years >= 10) {
            die();
            return; 
        }

        if (!this.yaFuePadreEnEsteTurno) {
            int[][] direcciones = {{-1,0}, {1,0}, {0,-1}, {0,1}}; 
            
            for (int[] dir : direcciones) {
                int filaVacia = row + dir[0];
                int colVacia = column + dir[1];
                int filaVecina = row + (dir[0] * 2); 
                int colVecina = column + (dir[1] * 2);
                
                if (filaVecina >= 0 && filaVecina < forest.getSize() && colVecina >= 0 && colVecina < forest.getSize()) {
                    Thing posiblePareja = forest.getThing(filaVecina, colVecina);
                    
                    if (forest.isEmpty(filaVacia, colVacia) && posiblePareja instanceof Squirrel) {
                        Squirrel pareja = (Squirrel) posiblePareja;
                        
                        if (!pareja.getYaFuePadre()) {
                            new Squirrel(forest, filaVacia, colVacia); 
                            this.yaFuePadreEnEsteTurno = true;
                            pareja.setYaFuePadre(true); 
                            break; 
                        }
                    }
                }
            }
        }

        
        ArrayList<int[]> todasLasVacias = new ArrayList<>();
        
        for (int f = 0; f < forest.getSize(); f++) {
            for (int c = 0; c < forest.getSize(); c++) {
                if (forest.isEmpty(f, c)) {
                    todasLasVacias.add(new int[]{f, c});
                }
            }
        }

        if (!todasLasVacias.isEmpty()) {
            Random rand = new Random();
            int[] destino = todasLasVacias.get(rand.nextInt(todasLasVacias.size()));
            
            forest.setThing(row, column, null); 
            row = destino[0];
            column = destino[1];
            forest.setThing(row, column, this); 
        }
    }
    
    public void setYaFuePadre(boolean estado) { 
        this.yaFuePadreEnEsteTurno = estado; 
    }
    
    public boolean getYaFuePadre() { 
        return yaFuePadreEnEsteTurno; 
    }   
    
    public void resetReproduccion() { 
        this.yaFuePadreEnEsteTurno = false; 
    }
    
    public void die() { 
        forest.setThing(row, column, null); 
    }
}