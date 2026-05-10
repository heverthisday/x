package presentation;

import domain.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Interfaz gráfica principal del simulador.
 * Dibuja el tablero, los elementos y controla el paso del tiempo mediante el botón Tic-Tac.
 */
public class ForestGUI extends JFrame {  
    public static final int SIDE = 20; 

    public final int SIZE;
    private JButton ticTacButton;
    private JPanel controlPanel;
    private PhotoForest photo;
    private Forest theForest;
   
    /**
     * Constructor principal de la ventana.
     * @param theForest El bosque (normal o de fuego) que se va a simular.
     * @param title El título que aparecerá en la ventana.
     */
    public ForestGUI(Forest theForest, String title) {
        this.theForest = theForest;
        this.SIZE = theForest.getSize(); 
        prepareElements();
        setTitle(title);
        prepareActions();
    }
    
    /**
     * Configura y ensambla los elementos visuales de la ventana (paneles y botones).
     */
    private void prepareElements() {
        setTitle("Schelling Forest");
        photo = new PhotoForest(this);
        ticTacButton = new JButton("Tic-tac");
        
        setLayout(new BorderLayout());
        add(photo, BorderLayout.NORTH);
        add(ticTacButton, BorderLayout.SOUTH);
        
        setSize(new Dimension(SIDE * SIZE + 15, SIDE * SIZE + 72)); 
        setResizable(false);
        photo.repaint();
    }

    /**
     * Configura los eventos de los botones (Listeners).
     */
    private void prepareActions() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);       
        ticTacButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ticTacButtonAction();
            }
        });
    }

    /**
     * Acción ejecutada al presionar el botón Tic-tac.
     */
    private void ticTacButtonAction() {
        theForest.ticTac();
        photo.repaint(); 
    }

    /**
     * Retorna el bosque actual que se está simulando.
     * @return Objeto Forest.
     */
    public Forest getTheForest() {
        return theForest;
    }

    /**
     * Ejecuta el bosque los dos tipos de bosques
     */
    public static void main(String[] args) {
        // 1. Abre la ventana de Animales
        ForestGUI guiAnimales = new ForestGUI(new Forest(), "Parte II: Animales");
        guiAnimales.setLocation(50, 50); 
        guiAnimales.setVisible(true);

        // 2. Abre la ventana de Fuego
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