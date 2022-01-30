package Model;

public class Fly extends Thread{

    Engine engine;

    /**
     * COnstructeur Fly, thread responsable de la gravité
     * @param engine
     */
    public Fly(Engine engine) {
        this.engine = engine;
    }

    /**
     * Fait tomber le cercle à tout les ticks du jeu
     */
    @Override
    public void run() {
        while(engine.isRunning){
            // Fait tomber le cercle
            engine.moveDown();
            // Attends 1 tick
            try {
                sleep(Engine.TICK);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
