package test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.io.File;
import domain.*;

/**
 * Pruebas unitarias para los métodos de persistencia de Forest.
 * Cubre saveAs, open, exportAs e importFrom (Partes II y III del Lab 06).
 */
public class ForestPersistenceTest {

    private Forest forest;
    private File archivoDat;
    private File archivoTxt;

    // ── Configuración ────────────────────────────────────────────────────────

    @Before
    public void setUp() {
        forest = new Forest();
        archivoDat = new File("forestTest.dat");
        archivoTxt = new File("forestTest.txt");
    }

    @After
    public void tearDown() {
        if (archivoDat.exists()) archivoDat.delete();
        if (archivoTxt.exists()) archivoTxt.delete();
    }

    // ════════════════════════════════════════════════════════════════════════
    // PRUEBAS DE saveAs
    // ════════════════════════════════════════════════════════════════════════

    @Test
    public void saveAs_deberiaCrearElArchivoDat() throws ForestException {
        forest.saveAs(archivoDat);

        assertTrue("El archivo .dat debe existir en disco después de guardar",
                archivoDat.exists());
    }

    @Test
    public void saveAs_archivoDebeOcuparEspacioEnDisco() throws ForestException {
        forest.saveAs(archivoDat);

        assertTrue("El archivo .dat debe tener tamaño mayor a 0",
                archivoDat.length() > 0);
    }

    @Test(expected = ForestException.class)
    public void saveAs_deberiaLanzarExcepcionEnDirectorioInvalido() throws ForestException {
        File rutaInvalida = new File("/ruta/que/no/existe/bosque.dat");
        forest.saveAs(rutaInvalida);
    }

    // ════════════════════════════════════════════════════════════════════════
    // PRUEBAS DE open
    // ════════════════════════════════════════════════════════════════════════

    @Test
    public void open_deberiaRestaurarElementosGuardados() throws ForestException {
        // Colocar un árbol en posición conocida y vaciar otra
        new Tree(forest, 0, 0);
        forest.saveAs(archivoDat);

        // Crear forest limpio y abrir el archivo
        Forest forestNuevo = new Forest();
        // Limpiar el forest nuevo primero
        for (int r = 0; r < forestNuevo.getSize(); r++)
            for (int c = 0; c < forestNuevo.getSize(); c++)
                forestNuevo.setThing(r, c, null);

        forestNuevo.open(archivoDat);

        assertTrue("Debe haber un Tree en (0,0) tras abrir el archivo guardado",
                forestNuevo.getThing(0, 0) instanceof Tree);
    }

    @Test
    public void open_estadoVacioDeberiaRestaurarseVacio() throws ForestException {
        // Guardar un forest completamente vacío
        Forest forestVacio = new Forest();
        for (int r = 0; r < forestVacio.getSize(); r++)
            for (int c = 0; c < forestVacio.getSize(); c++)
                forestVacio.setThing(r, c, null);

        forestVacio.saveAs(archivoDat);

        Forest forestNuevo = new Forest();
        forestNuevo.open(archivoDat);

        // Verificar que todas las celdas estén vacías
        boolean todoVacio = true;
        for (int r = 0; r < forestNuevo.getSize(); r++)
            for (int c = 0; c < forestNuevo.getSize(); c++)
                if (forestNuevo.getThing(r, c) != null) todoVacio = false;

        assertTrue("Un forest vacío guardado y abierto debe seguir vacío", todoVacio);
    }

    @Test(expected = ForestException.class)
    public void open_deberiaLanzarExcepcionSiArchivoNoExiste() throws ForestException {
        File archivoFantasma = new File("noExiste.dat");
        forest.open(archivoFantasma);
    }

    @Test(expected = ForestException.class)
    public void open_deberiaLanzarExcepcionSiArchivoEsInvalido() throws ForestException {
        // Crear un .dat con contenido basura
        try {
            java.io.PrintWriter pw = new java.io.PrintWriter(archivoDat);
            pw.println("esto no es un objeto serializado");
            pw.close();
        } catch (Exception e) {
            fail("No se pudo preparar el archivo de prueba");
        }
        forest.open(archivoDat);
    }

    // ════════════════════════════════════════════════════════════════════════
    // PRUEBAS DE exportAs
    // ════════════════════════════════════════════════════════════════════════

    @Test
    public void exportAs_deberiaCrearElArchivoTxt() throws ForestException {
        forest.exportAs(archivoTxt);

        assertTrue("El archivo .txt debe existir en disco después de exportar",
                archivoTxt.exists());
    }

    @Test
    public void exportAs_deberiaEscribirFormatoCorrectoParaTree() throws ForestException {
        // Forest limpio con un solo Tree en posición conocida
        Forest forestSimple = new Forest();
        for (int r = 0; r < forestSimple.getSize(); r++)
            for (int c = 0; c < forestSimple.getSize(); c++)
                forestSimple.setThing(r, c, null);
        new Tree(forestSimple, 3, 7);

        forestSimple.exportAs(archivoTxt);

        // Leer el archivo y verificar contenido
        try {
            java.util.List<String> lineas = java.nio.file.Files.readAllLines(archivoTxt.toPath());
            assertEquals("Debe haber exactamente una línea para un forest con un solo elemento",
                    1, lineas.size());
            assertEquals("El formato debe ser 'Tree fila, columna'",
                    "Tree 3, 7", lineas.get(0));
        } catch (java.io.IOException e) {
            fail("No se pudo leer el archivo exportado");
        }
    }

    @Test
    public void exportAs_forestVacioDeberiaProducirArchivoVacio() throws ForestException {
        Forest forestVacio = new Forest();
        for (int r = 0; r < forestVacio.getSize(); r++)
            for (int c = 0; c < forestVacio.getSize(); c++)
                forestVacio.setThing(r, c, null);

        forestVacio.exportAs(archivoTxt);

        assertEquals("Un forest vacío exportado debe generar un archivo de 0 bytes",
                0, archivoTxt.length());
    }

    @Test(expected = ForestException.class)
    public void exportAs_deberiaLanzarExcepcionEnRutaInvalida() throws ForestException {
        File rutaInvalida = new File("/ruta/invalida/bosque.txt");
        forest.exportAs(rutaInvalida);
    }

    // ════════════════════════════════════════════════════════════════════════
    // PRUEBAS DE importFrom
    // ════════════════════════════════════════════════════════════════════════

    @Test
    public void importFrom_deberiaCrearTreeEnPosicionCorrecta() throws ForestException {
        // Escribir un archivo .txt con formato válido
        try {
            java.io.PrintWriter pw = new java.io.PrintWriter(archivoTxt);
            pw.println("Tree 5, 10");
            pw.close();
        } catch (java.io.IOException e) {
            fail("No se pudo crear el archivo de prueba");
        }

        Forest forestNuevo = new Forest();
        forestNuevo.importFrom(archivoTxt);

        assertTrue("Debe haber un Tree en (5,10) después de importar",
                forestNuevo.getThing(5, 10) instanceof Tree);
    }

    @Test
    public void importFrom_deberiaCrearSquirrelEnPosicionCorrecta() throws ForestException {
        try {
            java.io.PrintWriter pw = new java.io.PrintWriter(archivoTxt);
            pw.println("Squirrel 2, 3");
            pw.close();
        } catch (java.io.IOException e) {
            fail("No se pudo crear el archivo de prueba");
        }

        Forest forestNuevo = new Forest();
        forestNuevo.importFrom(archivoTxt);

        assertTrue("Debe haber una Squirrel en (2,3) después de importar",
                forestNuevo.getThing(2, 3) instanceof Squirrel);
    }

    @Test
    public void importFrom_deberiaLimpiarElEstadoAnterior() throws ForestException {
        // Colocar un elemento en posición conocida
        new Wolf(forest, 0, 0);

        // Importar un archivo que no menciona esa posición
        try {
            java.io.PrintWriter pw = new java.io.PrintWriter(archivoTxt);
            pw.println("Tree 5, 5");
            pw.close();
        } catch (java.io.IOException e) {
            fail("No se pudo crear el archivo de prueba");
        }

        forest.importFrom(archivoTxt);

        assertNull("La posición (0,0) debe estar vacía después de importar",
                forest.getThing(0, 0));
    }

    @Test
    public void importFrom_deberiaImportarMultiplesElementos() throws ForestException {
        try {
            java.io.PrintWriter pw = new java.io.PrintWriter(archivoTxt);
            pw.println("Tree 1, 1");
            pw.println("Squirrel 2, 2");
            pw.println("Wolf 3, 3");
            pw.println("Shadow 4, 4");
            pw.close();
        } catch (java.io.IOException e) {
            fail("No se pudo crear el archivo de prueba");
        }

        Forest forestNuevo = new Forest();
        forestNuevo.importFrom(archivoTxt);

        assertTrue("Debe haber Tree en (1,1)",    forestNuevo.getThing(1, 1) instanceof Tree);
        assertTrue("Debe haber Squirrel en (2,2)", forestNuevo.getThing(2, 2) instanceof Squirrel);
        assertTrue("Debe haber Wolf en (3,3)",    forestNuevo.getThing(3, 3) instanceof Wolf);
        assertTrue("Debe haber Shadow en (4,4)",  forestNuevo.getThing(4, 4) instanceof Shadow);
    }

    @Test(expected = ForestException.class)
    public void importFrom_deberiaLanzarExcepcionSiArchivoNoExiste() throws ForestException {
        File archivoFantasma = new File("noExiste.txt");
        forest.importFrom(archivoFantasma);
    }

    @Test(expected = ForestException.class)
    public void importFrom_deberiaLanzarExcepcionConFormatoInvalido() throws ForestException {
        try {
            java.io.PrintWriter pw = new java.io.PrintWriter(archivoTxt);
            pw.println("esto no tiene formato correcto");
            pw.close();
        } catch (java.io.IOException e) {
            fail("No se pudo crear el archivo de prueba");
        }

        forest.importFrom(archivoTxt);
    }

    // ════════════════════════════════════════════════════════════════════════
    // PRUEBA DE ACEPTACIÓN — ciclo completo exportar/importar
    // ════════════════════════════════════════════════════════════════════════

    @Test
    public void pruebaDeAceptacion_exportarEImportarDebeReproducirElEstado()
            throws ForestException {
        // Forest limpio con elementos en posiciones fijas
        Forest forestOriginal = new Forest();
        for (int r = 0; r < forestOriginal.getSize(); r++)
            for (int c = 0; c < forestOriginal.getSize(); c++)
                forestOriginal.setThing(r, c, null);

        new Tree(forestOriginal, 2, 2);
        new Squirrel(forestOriginal, 4, 4);
        new Wolf(forestOriginal, 6, 6);

        // Exportar
        forestOriginal.exportAs(archivoTxt);

        // Importar en forest nuevo
        Forest forestRestaurado = new Forest();
        forestRestaurado.importFrom(archivoTxt);

        assertTrue("Tree (2,2) debe estar presente tras exportar/importar",
                forestRestaurado.getThing(2, 2) instanceof Tree);
        assertTrue("Squirrel (4,4) debe estar presente tras exportar/importar",
                forestRestaurado.getThing(4, 4) instanceof Squirrel);
        assertTrue("Wolf (6,6) debe estar presente tras exportar/importar",
                forestRestaurado.getThing(6, 6) instanceof Wolf);
    }

    @Test
    public void pruebaDeAceptacion_guardarYAbrirDebeReproducirElEstado()
            throws ForestException {
        // Forest limpio con elementos en posiciones fijas
        Forest forestOriginal = new Forest();
        for (int r = 0; r < forestOriginal.getSize(); r++)
            for (int c = 0; c < forestOriginal.getSize(); c++)
                forestOriginal.setThing(r, c, null);

        new Tree(forestOriginal, 1, 1);
        new Pine(forestOriginal, 3, 3);

        // Guardar
        forestOriginal.saveAs(archivoDat);

        // Abrir en forest nuevo vacío
        Forest forestRestaurado = new Forest();
        for (int r = 0; r < forestRestaurado.getSize(); r++)
            for (int c = 0; c < forestRestaurado.getSize(); c++)
                forestRestaurado.setThing(r, c, null);

        forestRestaurado.open(archivoDat);

        assertTrue("Tree (1,1) debe estar presente tras guardar/abrir",
                forestRestaurado.getThing(1, 1) instanceof Tree);
        assertTrue("Pine (3,3) debe estar presente tras guardar/abrir",
                forestRestaurado.getThing(3, 3) instanceof Pine);
    }
}
