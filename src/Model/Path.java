package Model;

import View.Display;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Path {

    /** Distance horizontale entre chaque point du parcours */
    public static final int STEP = 75;
    /** Hauteur max entre 2 points */
    private static final int MAX_JUMP_HEIGHT = (STEP / Engine.SPEED) * Engine.FALL;

    Random rand = new Random();
    ArrayList<Point> path;

    /**
     * Constructeur Path, génère le parcours initial
     */
    public Path() {
        path = new ArrayList<Point>();
        // Ajoute les 2 premiers points (dont la hauteur est définie afin de créer une ligne droite pour le début du jeu)
        path.add(new Point(Engine.CIRCLE_HEIGHT/2 + Display.offsetX, Display.HEIGHT - Engine.CIRCLE_HEIGHT/2));
        path.add(new Point(Engine.CIRCLE_HEIGHT/2 + Display.offsetX + STEP, Display.HEIGHT - Engine.CIRCLE_HEIGHT/2));
        // Ajoute des points à notre parcours de manière à "remplir" la fenêtre d'affichage
        for (int i = Engine.CIRCLE_HEIGHT/2 + Display.offsetX + 2*STEP; i <= Display.WIDTH+STEP; i+=STEP) {
            path.add(new Point(i, randomY(path.get(path.size() - 1).y)));
        }
    }

    /**
     * Met à jour le parcours au fur et à mesure que la fenêtre d'affichage avance
     * @param currentX position d'horizontal de la fenêtre d'affichage
     */
    public void updatePath(int currentX){
        // Si le l'avant-dernier point est affiché
        if (path.get(path.size() - 1).x < currentX + Display.WIDTH){
            // Ajouté un point au parcours
            path.add(new Point(path.get(path.size() - 1).x + STEP, randomY(path.get(path.size() - 1).y)));
        }

        //Si le premier point n'est plus affiché
        if(path.get(1).x - currentX < 0){
            // Supprimer le premier point du parcours
            path.remove(0);
        }
    }

    /**
     * Renvoie une position y en fonction de celle du dernier point afin que la pente ne soit pas trop élevé
     * @param lastY position en y du dernier point du parcours
     * @return
     */
    private int randomY(int lastY){
        // Si le point est trop haut, on force une hauteur plus petite que le point précédent
        if (lastY - MAX_JUMP_HEIGHT < 0){
            return lastY + rand.nextInt(MAX_JUMP_HEIGHT);
        }
        // Si le point est trop bas, on force une hauteur plus grande que le point précédent
        else if(lastY + MAX_JUMP_HEIGHT > Display.HEIGHT){
            return lastY - rand.nextInt(MAX_JUMP_HEIGHT);
        }
        else {
            return lastY + rand.nextInt(MAX_JUMP_HEIGHT) - MAX_JUMP_HEIGHT/2;
        }
    }

    /**
     * Renvoie la liste des points de notre parcours
     */
    public ArrayList<Point> getList() {
        return path;
    }
}
