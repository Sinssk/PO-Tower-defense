import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe EEarthBrute représente un ennemi de type Brute de Terre dans le jeu.
 * Elle étend la classe Enemy et fournit une implémentation spécifique pour cibler les tours.
 */
public class EEarthBrute extends Enemy {

    /**
     * Construit un EEarthBrute avec les coordonnées et le temps de spawn spécifiés.
     * 
     * @param coord les coordonnées de l'ennemi
     * @param spawn le temps de spawn de l'ennemi
     */
    public EEarthBrute(double[] coord, double spawn) {
        super(30, 30, 5, 1, 1, 3, Element.EARTH, 3, 20, new Color(0, 167, 15), coord, spawn);
    }

    /**
     * Vise la tour qui se trouve à sa portée et la plus proche de sa position.
     * 
     * @return une liste contenant la tour ciblée, ou une liste vide si aucune tour n'est à portée
     */
    @Override
    public List<Tour> cible() {
        Tour cible = null;
        List<Tour> tour = new ArrayList<>();
        double dist = 999999;
        for (Tour t : Game.tours) {
            double[] ct = t.getCoord();
            double x = ct[0] - super.getCoord()[0];
            double y = ct[1] - super.getCoord()[1];
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