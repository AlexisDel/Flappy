package View;

import Model.Engine;

public class Forward extends Thread{

    Display display;

    /**
     * Constructeur de Forward, qui permet l'animation de la ligne brisée
     * @param display
     */
    public Forward(Display display) {
        this.display = display;
        this.start();
    }

    /**
     * Met à jour les points de la ligne brisée et déclenche le rafraichissement de l'affichage
     */
    @Override
    public void run() {
        while (display.engine.isRunning()){
            // Met à jour la position de la fenêtre d'affichage virtuelle
            display.currentX += Engine.SPEED;
            // Met à jour le parcours par rapport à la position de la fenêtre
            display.engine.getPath().updatePath(display.currentX);

            display.displayUpdater.refresh();

            try {
                sleep(Engine.TICK);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
