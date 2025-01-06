import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe TEarthCaster représente une tour de type Caster de Terre dans le jeu.
 * Elle étend la classe Tour et fournit une implémentation spécifique pour cibler les ennemis.
 */
public class TEarthCaster extends Tour {

    /**
     * Construit une tour Caster de Terre avec les coordonnées spécifiées.
     * 
     * @param coord les coordonnées de la tour
     */
    public TEarthCaster(double[] coord) {
        super(500, 500, 7, 0.5, 2.5, Element.FIRE, 100, coord, new Color(0, 167, 15), false);
    }

    /**
     * Vise l'ennemi qui se trouve à sa portée ayant le plus grand nombre de PV.
     * Les ennemis à moins de 1.0 case de la cible subissent les mêmes dommages que sa cible.
     * 
     * @return une liste contenant l'ennemi ciblé et les ennemis à proximité, ou une liste vide si aucun ennemi n'est à portée
     */
    @Override
    public List<Enemy> cible() {
        Enemy cible = null;
        List<Enemy> aoe = new ArrayList<>();
        for (Enemy e : Game.spawnedEnemies) {
            if (inRange(e)) {
                if (cible == null) cible = e;
                else if (e.getHp() > cible.getHp()) cible = e;
            }
        }
        if (cible != null) {   
            aoe.add(cible);
            for (Enemy e : Game.spawnedEnemies) {
                double[] ec = e.getCoord();
                double x = ec[0] - cible.getCoord()[0];
                double y = ec[1] - cible.getCoord()[1];
                if (Math.sqrt(x * x + y * y) < (350 / MapGame.scale) * 2) aoe.add(e);
            }
        }
        return aoe;
    }
}