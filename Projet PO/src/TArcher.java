import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe TArcher représente une tour de type Archer dans le jeu.
 * Elle étend la classe Tour et fournit une implémentation spécifique pour cibler les ennemis.
 */
public class TArcher extends Tour {

    /**
     * Construit une tour Archer avec les coordonnées spécifiées.
     * 
     * @param coord les coordonnées de la tour
     */
    public TArcher(double[] coord) {
        super(300, 300, 5, 1, 2, Element.NONE, 20, coord, Color.BLACK, false);
    }

    /**
     * Vise l'ennemi qui se trouve à sa portée le plus avancé sur le chemin de la base.
     * 
     * @return une liste contenant l'ennemi le plus avancé, ou une liste vide si aucun ennemi n'est à portée
     */
    @Override
    public List<Enemy> cible() {
        List<Enemy> mostadvanced = new ArrayList<>();
        Enemy.updateAdvanced();
        for (Enemy e : Game.spawnedEnemies) {
            if (inRange(e)) mostadvanced.add(e);
        }
        if (!mostadvanced.isEmpty()) {
            mostadvanced.subList(1, mostadvanced.size()).clear();
        }
        return mostadvanced;
    }
}