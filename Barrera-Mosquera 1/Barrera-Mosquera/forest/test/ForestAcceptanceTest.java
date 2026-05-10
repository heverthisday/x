package test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.io.File;
import domain.*;

/**
 * Pruebas de aceptación para los métodos de persistencia de Forest (Lab 06).
 *
 * Cada prueba simula un escenario real completo de uso:
 *   1. Se ejecuta la aplicación y se dan clics (ticTac).
 *   2. Se guarda / exporta.
 *   3. Se sale (nuevo forest vacío).
 *   4. Se abre / importa.
 *   5. Se verifica que el estado sea idéntico al guardado.
 */
public class ForestAcceptanceTest {

    private File archivoDat;
    private File archivoTxt;

    @Before
    public void setUp() {
        archivoDat = new File("forestOne.dat");
        archivoTxt = new File("forestOne.txt");
    }

    @After
    public void tearDown() {
        if (archivoDat.exists()) archivoDat.delete();
        if (archivoTxt.exists()) archivoTxt.delete();
    }

    // ════════════════════════════════════════════════════════════════════════
    // PRUEBA DE ACEPTACIÓN 1 — saveAs / open
    //
    // Escenario: el usuario ejecuta la aplicación, da dos clics en Tic-tac,
    // guarda el estado como forestOne.dat, crea un nuevo forest y abre el
    // archivo. El estado debe ser igual al que se guardó.
    // ════════════════════════════════════════════════════════════════════════

    @Test
    public void aceptacion_guardarDosClicsYAbrir() throws ForestException {

        // ── Paso 1: arrancar la aplicación y dar dos clics ───────────────
        Forest forestInicial = new Forest();
        forestInicial.ticTac();
        forestInicial.ticTac();

        // Tomar una "foto" del estado actual (posición de cada celda)
        int size = forestInicial.getSize();
        String[][] estadoGuardado = capturarEstado(forestInicial);

        // ── Paso 2: guardar como forestOne.dat ───────────────────────────
        forestInicial.saveAs(archivoDat);

        assertTrue("El archivo forestOne.dat debe existir en disco",
                archivoDat.exists());
        assertTrue("El archivo forestOne.dat debe ocupar espacio",
                archivoDat.length() > 0);

        // ── Paso 3: "salir" → crear un nuevo forest vacío ────────────────
        Forest forestNuevo = new Forest();
        // Simular Nuevo: limpiar todas las celdas
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                forestNuevo.setThing(r, c, null);

        // Verificar que el nuevo forest esté efectivamente vacío
        assertNull("Tras Nuevo, la celda (0,0) debe estar vacía",
                forestNuevo.getThing(0, 0));

        // ── Paso 4: abrir forestOne.dat ──────────────────────────────────
        forestNuevo.open(archivoDat);

        // ── Paso 5: verificar que el estado restaurado coincide ──────────
        String[][] estadoRestaurado = capturarEstado(forestNuevo);

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                assertEquals(
                    "La celda (" + r + "," + c + ") debe ser igual tras guardar/abrir",
                    estadoGuardado[r][c],
                    estadoRestaurado[r][c]
                );
            }
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // PRUEBA DE ACEPTACIÓN 2 — exportAs / importFrom
    //
    // Escenario: el usuario da tres clics en Tic-tac, exporta el estado como
    // forestOne.txt, sale, entra, crea un nuevo forest e importa el archivo.
    // Los elementos en el archivo deben aparecer en las mismas posiciones.
    // ════════════════════════════════════════════════════════════════════════

    @Test
    public void aceptacion_exportarTresClicsEImportar() throws ForestException {

        // ── Paso 1: arrancar y dar tres clics ────────────────────────────
        Forest forestInicial = new Forest();
        forestInicial.ticTac();
        forestInicial.ticTac();
        forestInicial.ticTac();

        String[][] estadoExportado = capturarEstado(forestInicial);
        int size = forestInicial.getSize();

        // ── Paso 2: exportar como forestOne.txt ──────────────────────────
        forestInicial.exportAs(archivoTxt);

        assertTrue("El archivo forestOne.txt debe existir en disco",
                archivoTxt.exists());

        // Verificar que el archivo tenga al menos una línea de contenido
        try {
            java.util.List<String> lineas =
                java.nio.file.Files.readAllLines(archivoTxt.toPath());
            assertFalse("El archivo exportado no debe estar vacío si hay elementos",
                    lineas.isEmpty());
            // Verificar que las líneas tengan el formato "Tipo fila, columna"
            for (String linea : lineas) {
                assertTrue(
                    "Cada línea debe tener formato 'Tipo fila, columna'. Línea: '" + linea + "'",
                    linea.matches("\\w+ \\d+, \\d+")
                );
            }
        } catch (java.io.IOException e) {
            fail("No se pudo leer el archivo exportado: " + e.getMessage());
        }

        // ── Paso 3: "salir" y "entrar" → nuevo forest ────────────────────
        Forest forestNuevo = new Forest();
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                forestNuevo.setThing(r, c, null);

        // ── Paso 4: importar forestOne.txt ───────────────────────────────
        forestNuevo.importFrom(archivoTxt);

        // ── Paso 5: verificar que las posiciones importadas coinciden ─────
        // Solo se verifica que los tipos exportados están presentes;
        // importFrom recrea los elementos, no sus atributos internos.
        String[][] estadoImportado = capturarEstado(forestNuevo);

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                assertEquals(
                    "La celda (" + r + "," + c + ") debe tener el mismo tipo tras exportar/importar",
                    estadoExportado[r][c],
                    estadoImportado[r][c]
                );
            }
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // PRUEBA DE ACEPTACIÓN 3 — archivo .txt escrito a mano e importado
    //
    // Escenario: el usuario escribe manualmente un archivo forestOne.txt
    // con tres elementos válidos y lo importa. Debe ver esos elementos
    // exactamente en las posiciones especificadas.
    // ════════════════════════════════════════════════════════════════════════

    @Test
    public void aceptacion_importarArchivoEscritoAMano() throws ForestException {

        // ── Paso 1: escribir el archivo manualmente ───────────────────────
        try {
            java.io.PrintWriter pw = new java.io.PrintWriter(archivoTxt);
            pw.println("Tree 5, 5");
            pw.println("Squirrel 10, 10");
            pw.println("Wolf 15, 15");
            pw.close();
        } catch (java.io.IOException e) {
            fail("No se pudo escribir el archivo de prueba: " + e.getMessage());
        }

        // ── Paso 2: crear un nuevo forest e importar ─────────────────────
        Forest forestNuevo = new Forest();
        forestNuevo.importFrom(archivoTxt);

        // ── Paso 3: verificar que los elementos están en su lugar ─────────
        assertTrue("Debe haber un Tree en (5,5)",
                forestNuevo.getThing(5, 5) instanceof Tree);
        assertTrue("Debe haber una Squirrel en (10,10)",
                forestNuevo.getThing(10, 10) instanceof Squirrel);
        assertTrue("Debe haber un Wolf en (15,15)",
                forestNuevo.getThing(15, 15) instanceof Wolf);

        // Verificar que las posiciones NO mencionadas en el archivo estén vacías
        assertNull("La celda (0,0) no fue importada y debe estar vacía",
                forestNuevo.getThing(0, 0));
        assertNull("La celda (24,24) no fue importada y debe estar vacía",
                forestNuevo.getThing(24, 24));
    }

    // ════════════════════════════════════════════════════════════════════════
    // Utilidad: captura el estado de la matriz como nombres de clase
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Devuelve una matriz de strings con el nombre simple de cada clase
     * presente en cada celda, o "null" si la celda está vacía.
     */
    private String[][] capturarEstado(Forest f) {
        int size = f.getSize();
        String[][] estado = new String[size][size];
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                Thing t = f.getThing(r, c);
                estado[r][c] = (t == null) ? "null" : t.getClass().getSimpleName();
            }
        }
        return estado;
    }
}
