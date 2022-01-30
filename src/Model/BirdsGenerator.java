package Model;

import View.ViewBird;

import java.util.Random;

public class BirdsGenerator extends Thread{

    private static final int probability = 10;

    ViewBird viewBird;
    Random random;

    /**
     * Constructeur du Thread qui sert à générer de nouveau oiseaux
     * @param viewBird
     */
    public BirdsGenerator(ViewBird viewBird) {
        this.viewBird = viewBird;
        this.random = new Random();
        this.start();
    }

    /**
     * Ajoute un oiseau avec une chance de 1/10 toutes les secondes
     */
    @Override
    public void run() {

        while (true){
            // 1 chance / 10 d'ajouter un oiseau
            if (random.nextInt(probability) == 0){
                viewBird.birds.add(new Bird(viewBird));
            }

            // Attends 1 seconde
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
