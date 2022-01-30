package Control;

import Model.Engine;
import View.Display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Contrôleur de notre jeu, gère les actions sur la souris (event) des utilisateurs
 */
public class Controller implements MouseListener, KeyListener {

    Engine engine;

    /**
     * Créer le contrôleur et le lie au modèle et à l'affichage
     * @param engine le modèle du jeu
     */
    public Controller(Engine engine){
        this.engine = engine;
    }

    /**
     * Procédure appelée lorsque l'utilisateur appuie sur l'un des boutons de la souris
     * @param mouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (engine.isRunning()){
            engine.makeJump();
        }
    }

    /**
     * Procédure appelée lorsque l'utilisateur appuie sur une touche de son clavier
     * @param e la touche pressée
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            if (engine.isRunning()){
                engine.makeJump();
            }
        }

    }

    /* Fonctionnalités non utilisées ici, mais implémentées par l'interface MouseListener */

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
