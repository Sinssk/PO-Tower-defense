import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe EMinion représente un ennemi de type Minion dans le jeu.
 * Elle étend la classe Enemy et fournit une implémentation spécifique pour cibler les tours.
 */
public class EMinion extends Enemy {

    /**
     * Construit un EMinion avec les coordonnées et le temps de spawn spécifiés.
     * 
     * @param coord les coordonnées de l'ennemi
     * @param spawn le temps de spawn de l'ennemi
     */
    public EMinion(double[] coord, double spawn) {
        super(10, 10, 3, 0, 1, 0, Element.NONE, 1, 7, Color.BLACK, coord, spawn);
    }
    
    /**
     * Ne cible aucune tour.
     * 
     * @return une liste vide car le Minion n'attaque pas
     */
    @Override
    public List<Tour> cible() {
        return new ArrayList<>();
    }
}