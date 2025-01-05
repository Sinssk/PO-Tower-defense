import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class EEarthBrute extends Enemy{

    public EEarthBrute(double[] coord, double spawn){
        super(30, 30, 5, 1, 1, 3, Element.EARTH, 3, 20,new Color(0, 167, 15), coord, spawn);
    }

    // vise la Tour qui se trouve à sa portée le plus proche de sa position.
    @Override
    public List<Tour> cible(){
        Tour cible = null;
        List<Tour> tour = new ArrayList<>();
        double dist = 999999;
        for (Tour t : Game.tours){
            double[] ct = t.getCoord();
            double x = ct[0] - super.getCoord()[0];
            double y = ct[1] - super.getCoord()[1];
            double d = Math.sqrt(x*x + y*y);
            if (d < dist && d < (350/MapGame.scale)*2*super.getRange()){
                cible = t;
                dist = d;
            }
        }
        if (cible != null) {
            tour.add(cible); 
            System.out.println(">>> " + super.getLastAttack());
        }
        return tour;
    }
}