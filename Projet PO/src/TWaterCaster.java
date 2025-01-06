import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe TWaterCaster représente une tour de type Caster de l'Eau dans le jeu.
 * Elle étend la classe Tour et fournit une implémentation spécifique pour cibler les ennemis.
 */
public class TWaterCaster extends Tour {

    /**
     * Construit une tour Caster de l'Eau avec les coordonnées spécifiées.
     * 
     * @param coord les coordonnées de la tour
     */
    public TWaterCaster(double[] coord) {
        super(300, 300, 7, 1, 4, Element.WATER, 50, coord, new Color(6, 0, 160), false);
    }

    /**
     * Vise l'ennemi qui se trouve à sa portée le plus avancé sur le chemin de la base.
     * 
     * @return une liste contenant l'ennemi le plus avancé, ou une liste vide si aucun ennemi n'est à portée
     */
    @Override
    public List<Enemy> cible() {
        List<Enemy> mostadvanced = new ArrayList<>();
        for (Enemy e : Game.spawnedEnemies) {
            if (inRange(e)) mostadvanced.add(e);
        }
        if (!mostadvanced.isEmpty()) mostadvanced.subList(1, mostadvanced.size()).clear();
        return mostadvanced;
    }
}