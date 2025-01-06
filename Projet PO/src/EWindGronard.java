import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe EWindGronard représente un ennemi de type Grognard de Vent dans le jeu.
 * Elle étend la classe Enemy et fournit une implémentation spécifique pour cibler les tours.
 */
public class EWindGronard extends Enemy {

    /**
     * Construit un EWindGronard avec les coordonnées et le temps de spawn spécifiés.
     * 
     * @param coord les coordonnées de l'ennemi
     * @param spawn le temps de spawn de l'ennemi
     */
    public EWindGronard(double[] coord, double spawn) {
        super(1, 1, 7, 2, 2, 5, Element.WIND, 1, 10, new Color(242, 211, 0), coord, spawn);
    }

    /**
     * Vise la tour qui se trouve à sa portée ayant le moins de PV.
     * 
     * @return une liste contenant la tour ciblée, ou une liste vide si aucune tour n'est à portée
     */
    @Override
    public List<Tour> cible() {
        double minPV = 9999999;
        Tour cible = null;
        List<Tour> lst = new ArrayList<>();
        for (Tour t : Game.tours) {
            if (inRange(t) && t.getHp() < minPV) {
                cible = t;
                minPV = t.getHp();
            }
        }
        if (cible != null) lst.add(cible);
        return lst;
    }
}