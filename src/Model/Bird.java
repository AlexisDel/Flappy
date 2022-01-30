package Model;

import View.Display;
import View.ViewBird;

import java.util.Random;

public class Bird extends Thread {

    int delay;
    int state;
    int height;
    int positionX;

    public static final int SIZE = 100;

    Random random = new Random();
    ViewBird viewBird;

    /**
     * Constructeur de Bird, initialise les valeurs delay et height de manière aléatoire,
     * lance le thread responsable de la mise à jour de l'oiseau
     * @param viewBird
     */
    public Bird(ViewBird viewBird) {
        this.delay = random.nextInt(100) + 50;
        this.height = random.nextInt(Display.HEIGHT - SIZE);
        this.positionX = Display.WIDTH;
        this.viewBird = viewBird;
        this.start();
    }

    /**
     * Met à jour l'oiseau
     * SI encore afficher : met à jour sa position et son état (image à dessiner)
     * SINON supprime l'oiseau de la liste et arrête le thread
     */
    @Override
    public void run() {
        while (true){
            if (positionX > -SIZE){
                nextState();
                this.positionX -= 5;
                try {
                    sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                viewBird.birds.remove(this);
                this.stop();
            }
        }
    }

    /**
     * Renvoie l'état suivant (valeur allant de 0 à 7)
     */
    private void nextState(){
        state = (state+1)%8;
    }

    /* Getters */

    public int getBirdState() {
        return state;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getHeight() {
        return height;
    }
}

