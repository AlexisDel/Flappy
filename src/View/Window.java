package View;
import javax.swing.*;

/**
 * Fenêtre d'affichage simple
 */
public class Window extends JFrame {

    private static Display display;

    /**
     * Création de la fenêtre d'affichage
     * @param name titre de la fênetre
     */
    public Window(String name) {
        this.setTitle(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Ajout d'un affichage à notre fenêtre
     * @param display un affichage
     */
    public void initWindow(Display display){
        this.add(display);
        this.pack();
        this.setVisible(true);
    }
}
