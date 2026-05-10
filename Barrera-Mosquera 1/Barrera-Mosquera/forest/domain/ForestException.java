package domain;

/**
 * Excepción propia del dominio del simulador Forest.
 * Se lanza cuando ocurre un error en las operaciones de persistencia.
 */
public class ForestException extends Exception {

    /** Código que identifica el tipo de error. */
    private int errorCode;

    // ── Códigos de error ────────────────────────────────────────────────────
    /** Operación aún no implementada (maqueta). */
    public static final int OPTION_UNDER_CONSTRUCTION = 0;

    /** Error de entrada/salida al trabajar con archivos. */
    public static final int IO_ERROR = 1;

    /** Archivo no encontrado en la ruta indicada. */
    public static final int FILE_NOT_FOUND = 2;

    /** Sin permisos de lectura o escritura sobre el archivo. */
    public static final int PERMISSION_ERROR = 3;

    /** El archivo contiene una clase incompatible o desconocida. */
    public static final int INVALID_CLASS = 4;

    /** El archivo .dat está corrupto o no es un forest serializado. */
    public static final int CORRUPT_FILE = 5;

    /** El archivo .txt tiene una línea con formato inválido. */
    public static final int FORMAT_ERROR = 6;

    /**
     * Crea una ForestException con mensaje y código de error.
     * @param message Descripción del error.
     * @param errorCode Código que identifica el tipo de error.
     */
    public ForestException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Retorna el código de error asociado.
     * @return Código de error.
     */
    public int getErrorCode() {
        return errorCode;
    }
}