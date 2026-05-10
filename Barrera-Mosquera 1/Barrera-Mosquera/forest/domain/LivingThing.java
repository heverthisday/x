package domain;

import java.io.Serializable;

/**
 * Clase abstracta que representa a los seres vivos en el simulador.
 * Maneja propiedades comunes como la energía y la edad (años).
 */
public abstract class LivingThing implements Serializable {
    
    protected int years;
    protected int energy;

    /**
     * Crea un nuevo ser vivo con valores iniciales por defecto.
     */
    public LivingThing() {
        this.energy = 100;
        this.years = 0;
    }

    /**
     * Ejecuta un paso de vida del ser vivo, consumiendo energía.
     * @return true si logró consumir energía, false si ya no tiene.
     */
    final boolean step() {
        boolean ok = false;
        if (energy >= 1) {
            energy -= 1;
            ok = true;
        }
        return ok;
    }

    /**
     * Retorna la energía actual del ser vivo.
     * @return Nivel de energía.
     */
    public final int getEnergy() {
        return energy;
    }

    /**
     * Indica que este objeto es un ser vivo.
     * @return true siempre.
     */
    public final boolean isLivingThing() {
        return true;
    }
}