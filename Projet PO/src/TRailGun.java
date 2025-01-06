import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe TRailGun représente une tour de type Rail Gun dans le jeu.
 * Elle étend la classe Tour et fournit une implémentation spécifique pour cibler les ennemis.
 */
public class TRailGun extends Tour {

    /**
     * Construit une tour Rail Gun avec les coordonnées spécifiées.
     * 
     * @param coord les coordonnées de la tour
     */
    public TRailGun(double[] coord) {
        super(200, 200, 1, 0.05, 0, Element.FIRE, 150, coord, Color.GRAY, true);
    }

    /**
     * Vise l'ennemi qui se trouve à sa portée lorsque la souris est pressée.
     * 
     * @return une liste contenant l'ennemi ciblé, ou une liste vide si aucun ennemi n'est à portée
     */
    @Override
    public List<Enemy> cible() {
        List<Enemy> cible = new ArrayList<>();
        if (StdDraw.mousePressed()) {
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            for (Enemy e : Game.spawnedEnemies) {
                double dX = x - e.getCoord()[0];
                double dY = y - e.getCoord()[1];
                if (Math.sqrt(dX * dX + dY * dY) < e.getD()) cible.add(e);
            }
        }
        return cible;
    }
}