import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe TGoldDigger représente une tour de type Gold Digger dans le jeu.
 * Elle étend la classe Tour et fournit une implémentation spécifique pour cibler les ennemis.
 * Chaque fois que son attaque touche un ennemi, elle génère 1 pièce pour le joueur.
 */
public class TGoldDigger extends Tour {

    /**
     * Construit une tour Gold Digger avec les coordonnées spécifiées.
     * 
     * @param coord les coordonnées de la tour
     */
    public TGoldDigger(double[] coord) {
        super(200, 200, 1, 2, 10, Element.EARTH, 20, coord, new Color(241, 196, 15), true);
    }

    /**
     * Vise l'ennemi qui se trouve à sa portée le plus proche.
     * Chaque fois que son attaque touche un ennemi, elle génère 1 pièce pour le joueur.
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
            if (d < dist && d < MapGame.scale * super.getRange()) {
                cible = e;
                dist = d;
            }
        }
        if (cible != null) {
            enem.add(cible);
            Game.player.setGold(Game.player.getGold() + 1);
        }
        return enem;
    }
}