package View;

import Model.Bird;
import Model.BirdsGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ViewBird {

    public ArrayList<Bird> birds;

    /**
     * Constructeur de la vue responsable des oiseaux
     */
    public ViewBird() {
        this.birds = new ArrayList<>();
        // Ajoute un oiseau pour ne pas faire attendre le prof ;)
        birds.add(new Bird(this));
        new BirdsGenerator(this);

    }

    /**
     * Dessine les oiseaux à l'écran
     * @param g
     * @throws IOException
     */
    void draw(Graphics g) throws IOException {
        for (Bird bird : birds){
            g.drawImage(ImageIO.read(new File("ressources/"+bird.getBirdState()+".png")).getScaledInstance(Bird.SIZE, Bird.SIZE, Image.SCALE_SMOOTH), bird.getPositionX(), bird.getHeight(), null);
        }
    }
}
