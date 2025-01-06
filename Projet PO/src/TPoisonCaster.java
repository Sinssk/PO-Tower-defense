import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe TPoisonCaster représente une tour de type Caster de Poison dans le jeu.
 * Elle étend la classe Tour et fournit une implémentation spécifique pour cibler les ennemis.
 */
public class TPoisonCaster extends Tour {

    /**
     * Construit une tour Caster de Poison avec les coordonnées spécifiées.
     * 
     * @param coord les coordonnées de la tour
     */
    public TPoisonCaster(double[] coord) {
        super(500, 500, 1, 2, 5, Element.WIND, 80, coord, new Color(35, 192, 33), true);
    }

    /**
     * Vise l'ennemi qui se trouve à sa portée le plus avancé sur le chemin de la base qui ne soit pas déjà empoisonné.
     * Son attaque fait des dommages directs et inflige un poison qui réitère les mêmes dégâts avec la même vitesse d'attaque.
     * 
     * @return une liste contenant l'ennemi ciblé, ou une liste vide si aucun ennemi n'est à portée
     */
    @Override
    public List<Enemy> cible() {
        List<Enemy> mostadvanced = new ArrayList<>();
        List<Enemy> cibles = new ArrayList<>();
        Enemy.updateAdvanced();
        for (Enemy e : Game.spawnedEnemies) {
            if (inRange(e)) mostadvanced.add(e);
        }
        if (!mostadvanced.isEmpty()) {
            for (Enemy e : mostadvanced) {
                if (e.getPoisoned() == 0) {
                    cibles.add(e);
                    break;
                }
            }
            if (!cibles.isEmpty()) cibles.get(0).setPoisoned(super.getAtk(), super.getAtkSpeed());
        }
        return cibles;
    }
}