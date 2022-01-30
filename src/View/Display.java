package View;

import Control.Controller;
import Model.Engine;
import Model.Fly;
import Model.Path;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * L'affichage de notre jeu
 */
public class Display extends JPanel {

    /** Hauteur de l'affichage */
    public static final int WIDTH = 600;
    /** Largeur de l'affichage */
    public static final int HEIGHT = 400;
    /** Décalage du dessin du cercle */
    public static final int offsetX = 50;

    private final ViewBird viewBird;
    public Engine engine;
    public Forward forward;
    public DisplayUpdater displayUpdater;

    // Position de la fenêtre d'affichage "virtuelle" (pour le mouvement)
    public int currentX = 0;

    /**
     * Création de notre affichage
     * @param engine moteur (model) de notre jeu
     */
    public Display(Engine engine) {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.engine = engine;
        this.viewBird = new ViewBird();

    }

    /**
     * Ajoute un Contrôleur à notre affichage
     * @param controller MouseController lié à l'affichage
     */
    public void setController(Controller controller){
        this.addMouseListener(controller);
        this.addKeyListener(controller);
    }

    /**
     * Lie le displayUpdater à l'affichage
     * @param displayUpdater
     */
    public void setDisplayUpdater(DisplayUpdater displayUpdater){
        this.displayUpdater = displayUpdater;
        this.forward = new Forward(this);
    }

    /**
     * Dessine sur notre affichage
     * @param g
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.drawOval(offsetX, engine.getPositionY(), Engine.CIRCLE_HEIGHT, Engine.CIRCLE_HEIGHT);

        g.drawString("Score = "+(currentX/Path.STEP), 25, 25);

        // Dessine la ligne brisée
        g.setColor(Color.RED);
        ArrayList<Point> list = engine.getPath().getList();
        for(int i = 1; i < list.size(); i++){
            g.drawLine(list.get(i-1).x-currentX, list.get(i-1).y, list.get(i).x-currentX, list.get(i).y);
        }

        try {
            viewBird.draw(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
