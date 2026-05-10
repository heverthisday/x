package domain;

import java.util.ArrayList;

/**
 * Representa el tablero principal de la simulación (El Bosque).
 * Maneja la matriz de celdas y la lógica general de conteo y ejecución.
 */
public class Forest {
    private static final int SIZE = 25;
    private Thing[][] places;

    /**
     * Constructor del bosque. Inicializa la matriz y coloca los elementos iniciales.
     */
    public Forest() {
        places = new Thing[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                places[r][c] = null;
            }
        }
        someThings();
    }

    /**
     * Retorna el tamaño del bosque.
     * @return Tamaño de la matriz (SIZE).
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Obtiene el elemento ubicado en una fila y columna específica.
     * @param r Fila.
     * @param c Columna.
     * @return El objeto Thing en esa posición.
     */
    public Thing getThing(int r, int c) {
        return places[r][c];
    }

    /**
     * Coloca un elemento en una fila y columna específica.
     * @param r Fila.
     * @param c Columna.
     * @param e El objeto Thing a colocar.
     */
    public void setThing(int r, int c, Thing e) {
        places[r][c] = e;
    }

    /**
     * Población inicial estática para el bosque de animales (Parte II).
     */
    public void someThings() {
        Tree beard = new Tree(this, 10, 10);
        Tree soul = new Tree(this, 15, 15);
        Squirrel scrat = new Squirrel(this, 5, 5);
        Squirrel sandy = new Squirrel(this, 5, 7);
        Shadow thief = new Shadow(this, SIZE - 11, 3);
        Shadow lass = new Shadow(this, SIZE - 1, 7);
        Pine mosquera = new Pine(this, 3, 10);
        Pine martinez = new Pine(this, 3, 12);
        Wolf daniel = new Wolf(this, 11, 13);
        Wolf camilo = new Wolf(this, 8, 17);
    }

    /**
     * Cuenta cuántos vecinos del MISMO TIPO tiene una celda alrededor.
     */
    public int neighborsEquals(int r, int c) {
        int num = 0;
        if (inForest(r, c) && places[r][c] != null) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if ((dr != 0 || dc != 0) && inForest(r + dr, c + dc) &&
                        (places[r + dr][c + dc] != null) &&
                        (places[r][c].getClass() == places[r + dr][c + dc].getClass())) {
                        num++;
                    }
                }
            }
        }
        return num;
    }

    /**
     * Verifica si una celda está completamente vacía (null).
     */
    public boolean isEmpty(int r, int c) {
        return (inForest(r, c) && places[r][c] == null);
    }

    /**
     * Verifica si unas coordenadas están dentro de los límites del tablero.
     */
    private boolean inForest(int r, int c) {
        return ((0 <= r) && (r < SIZE) && (0 <= c) && (c < SIZE));
    }

    /**
     * Acción principal de tiempo. Ejecuta el ticTac de todos los elementos presentes.
     */
    public void ticTac() {
        
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (places[r][c] instanceof Squirrel) {
                    ((Squirrel) places[r][c]).resetReproduccion();
                }
            }
        }

        ArrayList<Thing> losQueActuan = new ArrayList<>();
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (places[r][c] != null) {
                    losQueActuan.add(places[r][c]);
                }
            }
        }

        for (Thing elemento : losQueActuan) {
            elemento.ticTac();
        }
    }

    /**
     * Cuenta cuántos vecinos de una clase específica (ej. Earth.class) tiene alrededor.
     */
    public int countNeighborsOfType(int r, int c, Class<?> tipo) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int nr = r + i, nc = c + j;
                if (nr >= 0 && nr < getSize() && nc >= 0 && nc < getSize()) {
                    if (tipo.isInstance(getThing(nr, nc))) count++;
                }
            }
        }
        return count;
    }

    /**
     * Cuenta cuántos vecinos válidos (dentro del tablero) tiene una celda (3, 5 u 8).
     */
    public int countValidNeighbors(int r, int c) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                if (r + i >= 0 && r + i < getSize() && c + j >= 0 && c + j < getSize()) count++;
            }
        }
        return count;
    }
}