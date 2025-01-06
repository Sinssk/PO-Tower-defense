import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe EWaterBrute représente un ennemi de type Brute d'Eau dans le jeu.
 * Elle étend la classe Enemy et fournit une implémentation spécifique pour cibler les tours.
 */
public class EWaterBrute extends Enemy {

    /**
     * Construit un EWaterBrute avec les coordonnées et le temps de spawn spécifiés.
     * 
     * @param coord les coordonnées de l'ennemi
     * @param spawn le temps de spawn de l'ennemi
     */
    public EWaterBrute(double[] coord, double spawn) {
        super(30, 30, 5, 1, 1, 3, Element.WATER, 3, 20, new Color(6, 0, 160), coord, spawn);
    }

    /**
     * Vise la tour qui se trouve à sa portée ayant le moins de PV.
     * Les tours à une distance inférieure à 1.5 case de la cible subissent les mêmes dommages que sa cible.
     * 
     * @return une liste contenant la tour ciblée et les tours à proximité, ou une liste vide si aucune tour n'est à portée
     */
    @Override
    public List<Tour> cible() {
        Tour cible = null;
        List<Tour> aoe = new ArrayList<>();
        for (Tour t : Game.tours) {
            if (inRange(t)) {
                if (cible == null) cible = t;
                else if (t.getHp() < cible.getHp()) cible = t;
            }
        }
        if (cible != null) {    
            aoe.add(cible);
            for (Tour t : Game.tours) {
                double[] ec = t.getCoord();
                double x = ec[0] - cible.getCoord()[0];
                double y = ec[1] - cible.getCoord()[1];
                if (Math.sqrt(x * x + y * y) < (350 / MapGame.scale) * 2 * super.getRange()) aoe.add(t);
            }
        }
        return aoe;
    }
}