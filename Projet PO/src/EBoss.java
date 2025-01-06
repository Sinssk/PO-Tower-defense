import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe EBoss représente un ennemi de type Boss dans le jeu.
 * Elle étend la classe Enemy et fournit une implémentation spécifique pour cibler les tours.
 */
public class EBoss extends Enemy {

    /**
     * Construit un EBoss avec les coordonnées et le temps de spawn spécifiés.
     * 
     * @param coord les coordonnées de l'ennemi
     * @param spawn le temps de spawn de l'ennemi
     */
    public EBoss(double[] coord, double spawn) {
        super(150, 150, 100, 10, 0.5, 2, Element.FIRE, 100, 40, new Color(142, 16, 0), coord, spawn);
    }

    /**
     * Vise la tour qui se trouve à sa portée et la plus proche de sa position.
     * 
     * @return une liste contenant la tour ciblée, ou une liste vide si aucune tour n'est à portée
     */
    @Override
    public List<Tour> cible() {
        List<Tour> lst = Game.tours;
        Tour cible = null;
        List<Tour> tour = new ArrayList<>();
        double dist = 999999;
        for (Tour t : lst) {
            double[] a = t.getCoord();
            double x = a[0] - super.getCoord()[0];
            double y = a[1] - super.getCoord()[1];
            double d = Math.sqrt(x * x + y * y);
            if (d < dist && d < (350 / MapGame.scale) * 2 * super.getRange()) {
                cible = t;
                dist = d;
            }
        }
        if (cible != null) tour.add(cible);
        return tour;
    }
}