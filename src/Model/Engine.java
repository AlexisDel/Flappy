package Model;

import View.Display;
import View.Forward;

import javax.swing.*;
import java.awt.*;

public class Engine {

    public static final int TICK = 100;

    /** Hauteur du saut (en pixels) */
    public static final int JUMP = 15;
    /** Taille du cercle (en pixels) */
    public static final int CIRCLE_HEIGHT = 50;
    /** Hauteur initiale du cercle */
    public static final int DEFAULT_HEIGHT = Display.HEIGHT - CIRCLE_HEIGHT;
    /** Distance de la chute */
    public static final int FALL = 4;
    /** Vitesse d'avancement du cercle*/
    public static final int SPEED = 2;
    /** Position en Y (hauteur) du cercle */
    private int positionY = DEFAULT_HEIGHT;
    /** Taille de la hitbox */
    private static final int HITBOX = 10;

    private Display display;
    private final Path path;
    private Fly fly;

    // Est-ce que le jeu est en cours (c.-à-d. Le joueur n'a pas perdu)
    boolean isRunning;

    float acceleration;

    /**
     * Constructeur de engine, lance le jeu
     */
    public Engine() {
        this.acceleration = 0;
        this.path = new Path();
        isRunning = true;

    }

    /**
     * Lance le Thread responsable de la gravité
     */
    public void startFly(){
        fly = new Fly(this);
        fly.start();
    }

    /**
     * Lie l'affichage au modèle
     * @param display
     */
    public void setDisplay(Display display){
        this.display = display;
    }

    /**
     * Getter du parcours
     * @return
     */
    public Path getPath() {
        return path;
    }

    /**
     * Fait sauter le cercle, si celui ci n'est pas trop haut, à savoir tout en haut de la fenêtre.
     */
    public void makeJump(){
        if(positionY > CIRCLE_HEIGHT/2){
            this.positionY -= JUMP;
            this.acceleration = 0;
            display.displayUpdater.refresh();
        }
        testPerdu(display.currentX);
    }

    /**
     * Fait tomber le cercle, s'il n'est pas trop bas, à savoir en en bas de la fenêtre.
     */
    public void moveDown(){
        if (positionY < Display.HEIGHT - CIRCLE_HEIGHT) {
            this.positionY += (FALL + acceleration);
            this.acceleration += 0.3;
            display.displayUpdater.refresh();
        }
        testPerdu(display.currentX);
    }

    /**
     * Test si le jeu est terminé, à savoir que le cercle n'est plus sur la ligne brisée
     * @param currentX
     */
    private void testPerdu(int currentX){
        Point a;
        Point b;

        // Lié au fait que lors du lancement du jeu le premier point de la liste n'est pas au début de l'affichage
        if (currentX < Path.STEP){
            a = path.getList().get(0);
            b = path.getList().get(1);
        } else {
            a = path.getList().get(1);
            b = path.getList().get(2);
        }
        // Hauteur de la courbe à la position verticale du cercle
        float yPath = a.y + (( (b.y - a.y) / (float)(b.x - a.x) ) * (currentX+75 - a.x));

        // Si la courbe est trop haute ou trop basse par rapport au cercle
        if (!((yPath > positionY - HITBOX) && (yPath < positionY + CIRCLE_HEIGHT + HITBOX ))){
            isRunning = false;
            JOptionPane.showMessageDialog(display, "Perdu");
        }
    }

    /**
     * Getter de la position vertical du cercle
     */
    public int getPositionY(){
        return positionY;
    }

    public boolean isRunning() {
        return isRunning;
    }
}