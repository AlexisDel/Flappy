package View;

import Model.Engine;

public class DisplayUpdater extends Thread{

    private boolean needToBeRefresh;
    private Engine engine;
    private Display display;

    /**
     * Constructeur du displayUpdater, responsable du rafraichissement de l'affichage
     * @param engine
     * @param display
     */
    public DisplayUpdater(Engine engine, Display display) {
        this.needToBeRefresh = false;
        this.engine = engine;
        this.display = display;
        this.start();
    }

    /**
     * Redessine l'affichage à l'écran s'il a été modifier depuis la dernière fois
     */
    @Override
    public void run() {
        while (engine.isRunning()){
            // Rafraichissement si nécessaire
            if (needToBeRefresh){
                display.repaint();
                needToBeRefresh = false;
            }

            // Rafraichissement 24 fois par secondes
            try {
                sleep(1000/24);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void refresh(){
        this.needToBeRefresh = true;
    }
}
