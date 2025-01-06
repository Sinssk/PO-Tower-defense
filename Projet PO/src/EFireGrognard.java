import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe EFireGrognard représente un ennemi de type Grognard de Feu dans le jeu.
 * Elle étend la classe Enemy et fournit une implémentation spécifique pour cibler les tours.
 */
public class EFireGrognard extends Enemy {
    
    /**
     * Construit un EFireGrognard avec les coordonnées et le temps de spawn spécifiés.
     * 
     * @param coord les coordonnées de l'ennemi
     * @param spawn le temps de spawn de l'ennemi
     */
    public EFireGrognard(double[] coord, double spawn) {
        super(1, 1, 7, 2, 2, 3, Element.FIRE, 1, 10, new Color(184, 22, 1), coord, spawn);
    }

    /**
     * Vise la tour qui se trouve à sa portée et la plus proche de sa position.
     * Les tours à une distance inférieure à 1.5 case de la cible subissent les mêmes dommages que sa cible.
     * 
     * @return une liste contenant la tour ciblée et les tours à proximité, ou une liste vide si aucune tour n'est à portée
     */
    @Override
    public List<Tour> cible() {
        Tour cible = null;
        List<Tour> aoe = new ArrayList<>();
        double dist = 999999;
        for (Tour t : Game.tours) {
            double[] a = t.getCoord();
            double x = a[0] - super.getCoord()[0];
            double y = a[1] - super.getCoord()[1];
            double d = Math.sqrt(x * x + y * y);
            if (d < dist && d < (350 / MapGame.scale) * 2 * super.getRange()) {
                cible = t;
                dist = d;
            }
        }
        if (cible != null) {    
            aoe.add(cible);
            for (Tour t : Game.tours) {
                double[] tc = t.getCoord();
                double x = tc[0] - cible.getCoord()[0];
                double y = tc[1] - cible.getCoord()[1];
                if (Math.sqrt(x * x + y * y) < (350 / MapGame.scale) * 2 * 1.5) {
                    aoe.add(t);
                }
            }
        }
        return aoe;
    }
}