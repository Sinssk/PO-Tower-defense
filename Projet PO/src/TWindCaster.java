import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe TWindCaster représente une tour de type Caster de Vent dans le jeu.
 * Elle étend la classe Tour et fournit une implémentation spécifique pour cibler les ennemis.
 */
public class TWindCaster extends Tour {

    /**
     * Construit une tour Caster de Vent avec les coordonnées spécifiées.
     * 
     * @param coord les coordonnées de la tour
     */
    public TWindCaster(double[] coord) {
        super(300, 300, 5, 1.5, 6, Element.WIND, 50, coord, new Color(242, 211, 0), false);
    }

    /**
     * Vise l'ennemi qui se trouve à sa portée le plus proche de sa position.
     * 
     * @return une liste contenant l'ennemi ciblé, ou une liste vide si aucun ennemi n'est à portée
     */
    @Override
    public List<Enemy> cible() {
        Enemy cible = null;
        List<Enemy> enem = new ArrayList<>();
        double dist = 999999;
        for (Enemy e : Game.spawnedEnemies) {
            double[] a = e.getCoord();
            double x = a[0] - super.getCoord()[0];
            double y = a[1] - super.getCoord()[1];
            double d = Math.sqrt(x * x + y * y);
            if (d < dist && d < (350 / MapGame.scale) * 2 * super.getRange()) {
                cible = e;
                dist = d;
            }
        }
        if (cible != null) enem.add(cible);
        return enem;
    }
}