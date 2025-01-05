import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class EFireGrognard extends Enemy{
    
    public EFireGrognard(double[] coord, double spawn){
        super(1, 1, 7, 2, 2, 3, Element.FIRE, 1, 10, new Color(184, 22, 1), coord, spawn);
    }

    //Il vise la Tour qui se trouve à sa portée la plus proche de sa position. Les Tours à une distance
    //(euclidienne entre les positions des deux éléments) inférieure à 1.5 case de la cible subissent les mêmes dommages que
    //sa cible.

    @Override
    public List<Tour> cible(){
        Tour cible = null;
        List<Tour> aoe = new ArrayList<>();
        double dist = 999999;
        for (Tour t : Game.tours){
            double[] a = t.getCoord();
            double x = a[0] - super.getCoord()[0];
            double y = a[1] - super.getCoord()[1];
            double d = Math.sqrt(x*x + y*y);
            if (d < dist && d < (350/MapGame.scale)*2*super.getRange()){
                cible = t;
                dist = d;
            }
        }
        if (cible != null){    
            aoe.add(cible);
            for (Tour t : Game.tours){
                double[] tc = t.getCoord();
                double x = tc[0] - cible.getCoord()[0];
                double y = tc[1] - cible.getCoord()[1];
                if (Math.sqrt(x*x + y*y) < (350/MapGame.scale)*2*1.5) aoe.add(t);
            }
        }
        return aoe;
    }
}