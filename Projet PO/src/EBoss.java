import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class EBoss extends Enemy{

    public EBoss(double[] coord, double spawn){
        super(150, 150, 100, 10, 0.5, 2, Element.FIRE, 100, 40, new Color(142, 16, 0), coord, spawn);
    }

    // vise la Tour qui se trouve à sa portée le plus proche de sa position.
    @Override
    public List<Tour> cible(){
        List<Tour> lst = Game.tours;
        Tour cible = null;
        List<Tour> tour = new ArrayList<>();
        double dist = 999999;
        for (Tour t : lst){
            double[] a = t.getCoord();
            double x = a[0] - super.getCoord()[0];
            double y = a[1] - super.getCoord()[1];
            double d = Math.sqrt(x*x + y*y);
            if (d < dist && d <(350/MapGame.scale)*2*super.getRange()){
                cible = t;
                dist = d;
            }
        }
        if (cible != null) tour.add(cible); 
        return tour;
    }
}