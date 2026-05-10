package presentation;

import domain.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Interfaz gráfica principal del simulador.
 * Dibuja el tablero, los elementos y controla el paso del tiempo.
 * Incluye el menú Archivo con las opciones estándar de persistencia (Parte I-B).
 */
public class ForestGUI extends JFrame {

    public static final int SIDE = 20;
    public final int SIZE;

    private JButton ticTacButton;
    private PhotoForest photo;
    private Forest theForest;

    // ── Ítems del menú
    private JMenuItem menuItemNew;
    private JMenuItem menuItemOpen;
    private JMenuItem menuItemSaveAs;
    private JMenuItem menuItemImport;
    private JMenuItem menuItemExportAs;
    private JMenuItem menuItemExit;

    /**
     * Constructor principal de la ventana.
     * @param theForest El bosque que se va a simular.
     * @param title     Título de la ventana.
     */
    public ForestGUI(Forest theForest, String title) {
        this.theForest = theForest;
        this.SIZE = theForest.getSize();
        prepareElements();
        prepareElementsMenu();
        setTitle(title);
        prepareActions();
        prepareActionsMenu();
    }


    // VISTA


    /**
     * Ensambla los elementos visuales principales (panel de dibujo y botón).
     */
    private void prepareElements() {
        photo = new PhotoForest(this);
        ticTacButton = new JButton("Tic-tac");

        setLayout(new BorderLayout());
        add(photo, BorderLayout.NORTH);
        add(ticTacButton, BorderLayout.SOUTH);

        setSize(new Dimension(SIDE * SIZE + 15, SIDE * SIZE + 95));
        setResizable(false);
        photo.repaint();
    }

    /**
     * Construye el menú Archivo con las seis opciones estándar y sus separadores.
     * Nuevo | Abrir | Guardar como | ── | Importar | Exportar como | ── | Salir
     */
    private void prepareElementsMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        menuArchivo.setMnemonic(KeyEvent.VK_A);

        // Nuevo
        menuItemNew = new JMenuItem("Nuevo");
        menuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));

        // Abrir
        menuItemOpen = new JMenuItem("Abrir...");
        menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));

        // Guardar como
        menuItemSaveAs = new JMenuItem("Guardar como...");
        menuItemSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

        // Importar
        menuItemImport = new JMenuItem("Importar...");

        // Exportar como
        menuItemExportAs = new JMenuItem("Exportar como...");

        // Salir
        menuItemExit = new JMenuItem("Salir");
        menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

        // Ensamblar menú con separadores
        menuArchivo.add(menuItemNew);
        menuArchivo.addSeparator();
        menuArchivo.add(menuItemOpen);
        menuArchivo.add(menuItemSaveAs);
        menuArchivo.addSeparator();
        menuArchivo.add(menuItemImport);
        menuArchivo.add(menuItemExportAs);
        menuArchivo.addSeparator();
        menuArchivo.add(menuItemExit);

        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);
    }

    // CONTROLADOR


    /**
     * Registra los listeners del botón Tic-tac y del cierre de ventana.
     */
    private void prepareActions() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ticTacButton.addActionListener(e -> ticTacButtonAction());
    }

    /**
     * Registra los listeners de las seis opciones del menú Archivo.
     */
    private void prepareActionsMenu() {
        menuItemNew.addActionListener(e -> optionNew());
        menuItemOpen.addActionListener(e -> optionOpen());
        menuItemSaveAs.addActionListener(e -> optionSaveAs());
        menuItemImport.addActionListener(e -> optionImport());
        menuItemExportAs.addActionListener(e -> optionExportAs());
        menuItemExit.addActionListener(e -> optionExit());
    }

    //Acciones del menú

    /**
     * Crea un bosque nuevo descartando el estado actual.
     */
    private void optionNew() {
        theForest = new Forest();
        photo.repaint();
    }

    /**
     * Abre un archivo .dat y carga el forest serializado.
     * Por ahora el modelo lanza ForestException (opción en construcción).
     */
    private void optionOpen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Forest guardado (*.dat)", "dat"));
        int resultado = chooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                theForest.open(file);
                photo.repaint();
            } catch (ForestException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Guarda el estado actual del forest en un archivo .dat.
     * Por ahora el modelo lanza ForestException (opción en construcción).
     */
    private void optionSaveAs() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Forest guardado (*.dat)", "dat"));
        int resultado = chooser.showSaveDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().endsWith(".dat")) {
                file = new File(file.getAbsolutePath() + ".dat");
            }
            try {
                theForest.saveAs(file);
            } catch (ForestException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Importa el estado del forest desde un archivo de texto .txt.
     * Por ahora el modelo lanza ForestException (opción en construcción).
     */
    private void optionImport() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Forest texto (*.txt)", "txt"));
        int resultado = chooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                theForest.importFrom(file);
                photo.repaint();
            } catch (ForestException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Exporta el estado actual del forest a un archivo de texto .txt.
     * Por ahora el modelo lanza ForestException (opción en construcción).
     */
    private void optionExportAs() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Forest texto (*.txt)", "txt"));
        int resultado = chooser.showSaveDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }
            try {
                theForest.exportAs(file);
            } catch (ForestException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Termina la aplicación inmediatamente.
     */
    private void optionExit() {
        System.exit(0);
    }

    //Acción del botón

    private void ticTacButtonAction() {
        theForest.ticTac();
        photo.repaint();
    }

    /**
     * Retorna el bosque actual.
     * @return Objeto Forest.
     */
    public Forest getTheForest() {
        return theForest;
    }

    public static void main(String[] args) {
        ForestGUI guiAnimales = new ForestGUI(new Forest(), "Parte II: Animales");
        guiAnimales.setLocation(50, 50);
        guiAnimales.setVisible(true);

        ForestGUI guiFuego = new ForestGUI(new FireForest(), "Parte III: Forest Fire");
        guiFuego.setLocation(600, 50);
        guiFuego.setVisible(true);
    }
}

/**
 * Panel interno encargado de dibujar gráficamente la matriz del bosque y sus elementos.
 */
class PhotoForest extends JPanel {
    private ForestGUI gui;

    public PhotoForest(ForestGUI gui) {
        this.gui = gui;
        setBackground(Color.white);
        setPreferredSize(new Dimension(ForestGUI.SIDE * gui.SIZE + 10, ForestGUI.SIDE * gui.SIZE + 10));         
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Forest theForest = gui.getTheForest();
         
        
        for (int c = 0; c <= theForest.getSize(); c++) {
            g.drawLine(c * ForestGUI.SIDE, 0, c * ForestGUI.SIDE, theForest.getSize() * ForestGUI.SIDE);
        }
        for (int f = 0; f <= theForest.getSize(); f++) {
            g.drawLine(0, f * ForestGUI.SIDE, theForest.getSize() * ForestGUI.SIDE, f * ForestGUI.SIDE);
        }       

        for (int f = 0; f < theForest.getSize(); f++) {
            for (int c = 0; c < theForest.getSize(); c++) {
                Thing currentThing = theForest.getThing(f, c);
                
                if (currentThing != null) {
                    g.setColor(currentThing.getColor());
                    
                    
                    if (currentThing.shape() == Thing.SQUARE) {                  
                        g.fillRoundRect(ForestGUI.SIDE * c + 1, ForestGUI.SIDE * f + 1, ForestGUI.SIDE - 2, ForestGUI.SIDE - 2, 2, 2);   
                    } else if (currentThing.shape() == Thing.TRIANGLE) {
                        int[] xPoints = {ForestGUI.SIDE * c + ForestGUI.SIDE / 2, ForestGUI.SIDE * c + 1, ForestGUI.SIDE * c + ForestGUI.SIDE - 1};
                        int[] yPoints = {ForestGUI.SIDE * f + 1, ForestGUI.SIDE * f + ForestGUI.SIDE - 1, ForestGUI.SIDE * f + ForestGUI.SIDE - 1};
                        g.fillPolygon(xPoints, yPoints, 3);
                    } else { 
                        g.fillOval(ForestGUI.SIDE * c + 1, ForestGUI.SIDE * f + 1, ForestGUI.SIDE - 2, ForestGUI.SIDE - 2);
                    }
                    
                    if (currentThing.getClass().getSimpleName().equals("Shadow")) {
                        Color sombraTransparente = new Color(0, 0, 0, 80); 
                        g.setColor(sombraTransparente);
                        g.fillRect(0, ForestGUI.SIDE * f, theForest.getSize() * ForestGUI.SIDE, ForestGUI.SIDE);
                    }
                    
                    if (currentThing.isLivingThing()) {
                        g.setColor(Color.red);
                        if (((LivingThing) currentThing).getEnergy() >= 50) {
                            g.drawString("+", ForestGUI.SIDE * c + 6, ForestGUI.SIDE * f + 15);
                        } else {
                            g.drawString("~", ForestGUI.SIDE * c + 6, ForestGUI.SIDE * f + 17);
                        }
                    }    
                }
            }
        }
    }
}