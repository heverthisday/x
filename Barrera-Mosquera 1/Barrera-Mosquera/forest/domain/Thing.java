package domain;

import java.awt.Color;

/**
 * Interfaz base para todos los elementos del simulador.
 * Define las formas básicas y el comportamiento en cada ciclo de tiempo.
 */
public interface Thing {
    public static final int ROUND = 1;
    public static final int SQUARE = 2;
    public static final int TRIANGLE = 3;

    /**
     * Define la acción que realiza el objeto en cada ciclo de la simulación.
     */
    public void ticTac();

    /**
     * Retorna la forma geométrica del elemento.
     * @return Entero que representa la forma (por defecto SQUARE).
     */
    public default int shape() {
        return SQUARE;
    }

    /**
     * Retorna el color del elemento.
     * @return Color del objeto (por defecto negro).
     */
    public default Color getColor() {
        return Color.black;
    }

    /**
     * Indica si el objeto es la única cosa en esa posición.
     * @return true por defecto.
     */
    public default boolean isOnlyThing() {
        return true;
    }

    /**
     * Indica si el objeto es un ser vivo.
     * @return false por defecto para objetos inanimados.
     */
    public default boolean isLivingThing() {
        return false;
    }
}