import Control.Controller;
import Model.Engine;
import View.Display;
import View.DisplayUpdater;
import View.Window;

/**
 * Crée, initialise et lie tous les composants de notre jeu (affichage, modèle, contrôleur, fenêtre d'affichage)
 */
public class Main {

    public static void main(String[] args) {
        //Création des différents composants de notre jeu
        Engine engine = new Engine();
        Display display = new Display(engine);
        DisplayUpdater displayUpdate = new DisplayUpdater(engine, display);
        Controller controller = new Controller(engine);

        //Liaison entre le rafraichissement de l'affichage et l'affichage
        display.setDisplayUpdater(displayUpdate);
        //Liaison entre le MouseController (click souris) et notre affichage
        display.setController(controller);
        //Liaison entre l'affichage et le model
        engine.setDisplay(display);

        //Création de la fenêtre d'affichage
        Window window = new Window("Flappy Bird");
        // Ajout de l'affichage à la fenêtre
        window.initWindow(display);

        // Lance le thread responsable de la gravité
        engine.startFly();
    }
}