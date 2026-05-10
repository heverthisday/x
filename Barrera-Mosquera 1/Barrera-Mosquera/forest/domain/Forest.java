package domain;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Representa el tablero principal de la simulación (El Bosque).
 * Maneja la matriz de celdas y la lógica general de conteo y ejecución.
 */
public class Forest implements Serializable {

    private static final long serialVersionUID = 1L;
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

    // ── Métodos de persistencia ──────────────────────────────────────────────

    // ── Versiones 00 (maqueta original – Parte I-B) ──────────────────────────

    /**
     * Versión maqueta de open (Parte I-B). Solo lanza excepción.
     */
    public void open00(File file) throws ForestException {
        throw new ForestException(
            "Opción open en construcción. Archivo " + file.getName(),
            ForestException.OPTION_UNDER_CONSTRUCTION
        );
    }

    /**
     * Versión maqueta de saveAs (Parte I-B). Solo lanza excepción.
     */
    public void saveAs00(File file) throws ForestException {
        throw new ForestException(
            "Opción saveAs en construcción. Archivo " + file.getName(),
            ForestException.OPTION_UNDER_CONSTRUCTION
        );
    }

    /**
     * Versión maqueta de importFrom (Parte I-B). Solo lanza excepción.
     */
    public void import00(File file) throws ForestException {
        throw new ForestException(
            "Opción import en construcción. Archivo " + file.getName(),
            ForestException.OPTION_UNDER_CONSTRUCTION
        );
    }

    /**
     * Versión maqueta de exportAs (Parte I-B). Solo lanza excepción.
     */
    public void exportAs00(File file) throws ForestException {
        throw new ForestException(
            "Opción exportAs en construcción. Archivo " + file.getName(),
            ForestException.OPTION_UNDER_CONSTRUCTION
        );
    }

    // ── Versiones reales (Parte II) ──────────────────────────────────────────

    /**
     * Guarda el estado actual del forest serializado en un archivo .dat.
     * @param file Archivo de destino.
     * @throws ForestException Si ocurre un error de E/S.
     */
    public void saveAs(File file) throws ForestException {
        try (java.io.ObjectOutputStream oos =
                new java.io.ObjectOutputStream(new java.io.FileOutputStream(file))) {
            oos.writeObject(this.places);
        } catch (java.io.IOException e) {
            throw new ForestException(
                "Error al guardar. Archivo: " + file.getName() + ". " + e.getMessage(),
                ForestException.IO_ERROR
            );
        }
    }

    /**
     * Carga el estado del forest desde un archivo .dat serializado.
     * Reemplaza la matriz actual con la leída del archivo.
     * @param file Archivo de origen.
     * @throws ForestException Si ocurre un error de E/S o el archivo no es válido.
     */
    public void open(File file) throws ForestException {
        try (java.io.ObjectInputStream ois =
                new java.io.ObjectInputStream(new java.io.FileInputStream(file))) {
            this.places = (Thing[][]) ois.readObject();
        } catch (java.io.FileNotFoundException e) {
            throw new ForestException(
                "Archivo no encontrado: " + file.getName(),
                ForestException.IO_ERROR
            );
        } catch (java.io.IOException | ClassNotFoundException e) {
            throw new ForestException(
                "Error al abrir. Archivo: " + file.getName() + ". " + e.getMessage(),
                ForestException.IO_ERROR
            );
        }
    }

    /**
     * Exporta el estado actual del forest a un archivo de texto .txt.
     * Formato por línea: NombreClase fila, columna
     * @param file Archivo de destino.
     * @throws ForestException Si ocurre un error de E/S.
     */
    public void exportAs(File file) throws ForestException {
        try (java.io.PrintWriter pw =
                new java.io.PrintWriter(new java.io.FileWriter(file))) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    if (places[r][c] != null) {
                        String tipo = places[r][c].getClass().getSimpleName();
                        pw.println(tipo + " " + r + ", " + c);
                    }
                }
            }
        } catch (java.io.IOException e) {
            throw new ForestException(
                "Error al exportar. Archivo: " + file.getName() + ". " + e.getMessage(),
                ForestException.IO_ERROR
            );
        }
    }

    /**
     * Importa el estado del forest desde un archivo de texto .txt.
     * Limpia la matriz actual y recrea los elementos según el formato:
     * NombreClase fila, columna
     * @param file Archivo de origen.
     * @throws ForestException Si ocurre un error de E/S o el formato no es válido.
     */
    public void importFrom(File file) throws ForestException {
        try (java.io.BufferedReader br =
                new java.io.BufferedReader(new java.io.FileReader(file))) {
            // Limpiar el tablero actual
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    places[r][c] = null;
                }
            }
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] partes = linea.split(" ");
                String tipo = partes[0];
                // El formato exportado es: "Tipo fila, columna"
                // partes[1] = "fila," y partes[2] = "columna"
                int r = Integer.parseInt(partes[1].replace(",", "").trim());
                int c = Integer.parseInt(partes[2].trim());
                crearElemento(tipo, r, c);
            }
        } catch (java.io.FileNotFoundException e) {
            throw new ForestException(
                "Archivo no encontrado: " + file.getName(),
                ForestException.IO_ERROR
            );
        } catch (java.io.IOException e) {
            throw new ForestException(
                "Error al importar. Archivo: " + file.getName() + ". " + e.getMessage(),
                ForestException.IO_ERROR
            );
        } catch (Exception e) {
            throw new ForestException(
                "Formato inválido en: " + file.getName() + ". " + e.getMessage(),
                ForestException.IO_ERROR
            );
        }
    }

    // ── Versiones 01 (Parte III) — excepciones detalladas ───────────────────

    /**
     * Versión 01 de saveAs. Detalla el tipo de error de E/S.
     * Copia de saveAs antes de perfeccionar (Parte III-A).
     */
    public void saveAs01(File file) throws ForestException {
        try (java.io.ObjectOutputStream oos =
                new java.io.ObjectOutputStream(new java.io.FileOutputStream(file))) {
            oos.writeObject(this.places);
        } catch (java.io.FileNotFoundException e) {
            throw new ForestException(
                "No se puede crear el archivo '" + file.getName() +
                "'. Verifique que la ruta '" + file.getParent() + "' exista y tenga permisos de escritura.",
                ForestException.FILE_NOT_FOUND
            );
        } catch (java.io.IOException e) {
            throw new ForestException(
                "Error de escritura en '" + file.getName() + "': " + e.getMessage(),
                ForestException.IO_ERROR
            );
        }
    }

    /**
     * Versión 01 de open. Distingue archivo no encontrado, formato inválido
     * y versión incompatible de clase.
     * Copia de open antes de perfeccionar (Parte III-A).
     */
    public void open01(File file) throws ForestException {
        if (!file.exists()) {
            throw new ForestException(
                "El archivo '" + file.getName() + "' no existe. " +
                "Verifique la ruta: " + file.getAbsolutePath(),
                ForestException.FILE_NOT_FOUND
            );
        }
        if (!file.canRead()) {
            throw new ForestException(
                "El archivo '" + file.getName() + "' no tiene permisos de lectura.",
                ForestException.PERMISSION_ERROR
            );
        }
        try (java.io.ObjectInputStream ois =
                new java.io.ObjectInputStream(new java.io.FileInputStream(file))) {
            this.places = (Thing[][]) ois.readObject();
        } catch (java.io.InvalidClassException e) {
            throw new ForestException(
                "El archivo '" + file.getName() + "' fue guardado con una versión " +
                "incompatible del forest y no puede abrirse.",
                ForestException.INVALID_CLASS
            );
        } catch (java.io.StreamCorruptedException e) {
            throw new ForestException(
                "El archivo '" + file.getName() + "' está corrupto o no es un archivo .dat válido.",
                ForestException.CORRUPT_FILE
            );
        } catch (ClassNotFoundException e) {
            throw new ForestException(
                "El archivo '" + file.getName() + "' contiene una clase desconocida: " + e.getMessage(),
                ForestException.INVALID_CLASS
            );
        } catch (java.io.IOException e) {
            throw new ForestException(
                "Error de lectura en '" + file.getName() + "': " + e.getMessage(),
                ForestException.IO_ERROR
            );
        }
    }

    /**
     * Versión 01 de exportAs. Detalla el tipo de error de E/S.
     * Copia de exportAs antes de perfeccionar (Parte III-B).
     */
    public void exportAs01(File file) throws ForestException {
        try (java.io.PrintWriter pw =
                new java.io.PrintWriter(new java.io.FileWriter(file))) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    if (places[r][c] != null) {
                        String tipo = places[r][c].getClass().getSimpleName();
                        pw.println(tipo + " " + r + ", " + c);
                    }
                }
            }
        } catch (java.io.FileNotFoundException e) {
            throw new ForestException(
                "No se puede crear el archivo '" + file.getName() +
                "'. Verifique que la ruta '" + file.getParent() + "' exista y tenga permisos de escritura.",
                ForestException.FILE_NOT_FOUND
            );
        } catch (java.io.IOException e) {
            throw new ForestException(
                "Error de escritura en '" + file.getName() + "': " + e.getMessage(),
                ForestException.IO_ERROR
            );
        }
    }

    /**
     * Versión 01 de importFrom. Distingue archivo no encontrado, permisos,
     * y formato inválido con detalle de línea y token problemático.
     * Copia de importFrom antes de perfeccionar (Parte III-B).
     */
    public void import01(File file) throws ForestException {
        if (!file.exists()) {
            throw new ForestException(
                "El archivo '" + file.getName() + "' no existe. " +
                "Verifique la ruta: " + file.getAbsolutePath(),
                ForestException.FILE_NOT_FOUND
            );
        }
        if (!file.canRead()) {
            throw new ForestException(
                "El archivo '" + file.getName() + "' no tiene permisos de lectura.",
                ForestException.PERMISSION_ERROR
            );
        }
        try (java.io.BufferedReader br =
                new java.io.BufferedReader(new java.io.FileReader(file))) {
            // Limpiar el tablero actual
            for (int r = 0; r < SIZE; r++)
                for (int c = 0; c < SIZE; c++)
                    places[r][c] = null;

            String linea;
            int numLinea = 0;
            while ((linea = br.readLine()) != null) {
                numLinea++;
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(" ");
                if (partes.length < 3) {
                    throw new ForestException(
                        "Formato inválido en '" + file.getName() +
                        "', línea " + numLinea + ": '" + linea +
                        "'. Se esperaba: 'Tipo fila, columna'.",
                        ForestException.FORMAT_ERROR
                    );
                }

                String tipo = partes[0];
                int r, c;
                try {
                    r = Integer.parseInt(partes[1].replace(",", "").trim());
                } catch (NumberFormatException e) {
                    throw new ForestException(
                        "Fila inválida en '" + file.getName() +
                        "', línea " + numLinea + ": el token '" + partes[1] +
                        "' no es un número entero.",
                        ForestException.FORMAT_ERROR
                    );
                }
                try {
                    c = Integer.parseInt(partes[2].trim());
                } catch (NumberFormatException e) {
                    throw new ForestException(
                        "Columna inválida en '" + file.getName() +
                        "', línea " + numLinea + ": el token '" + partes[2] +
                        "' no es un número entero.",
                        ForestException.FORMAT_ERROR
                    );
                }

                if (r < 0 || r >= SIZE || c < 0 || c >= SIZE) {
                    throw new ForestException(
                        "Coordenadas fuera de rango en '" + file.getName() +
                        "', línea " + numLinea + ": (" + r + ", " + c +
                        "). El tablero es de " + SIZE + "x" + SIZE + ".",
                        ForestException.FORMAT_ERROR
                    );
                }

                crearElemento(tipo, r, c);
            }
        } catch (ForestException e) {
            throw e; // re-lanzar sin envolver
        } catch (java.io.IOException e) {
            throw new ForestException(
                "Error de lectura en '" + file.getName() + "': " + e.getMessage(),
                ForestException.IO_ERROR
            );
        }
    }

    /**
     * Crea un elemento del tipo indicado en la posición dada.
     * @param tipo  Nombre simple de la clase (Tree, Squirrel, etc.).
     * @param r     Fila.
     * @param c     Columna.
     */
    private void crearElemento(String tipo, int r, int c) {
        switch (tipo) {
            case "Tree":     new Tree(this, r, c);     break;
            case "Pine":     new Pine(this, r, c);     break;
            case "Squirrel": new Squirrel(this, r, c); break;
            case "Wolf":     new Wolf(this, r, c);     break;
            case "Shadow":   new Shadow(this, r, c);   break;
            case "Earth":    new Earth(this, r, c);    break;
            case "Fire":     new Fire(this, r, c);     break;
            case "Water":    new Water(this, r, c);    break;
            default: break; // tipo desconocido: se ignora
        }
    }
}