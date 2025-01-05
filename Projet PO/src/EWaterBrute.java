import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class EWaterBrute extends Enemy{

    public EWaterBrute(double[] coord, double spawn){
        super(30, 30, 5, 1, 1, 3, Element.WATER, 3, 20, new Color(6, 0, 160), coord, spawn);
    }

    //vise la Tour qui se trouve à sa portée ayant le moins de PV. Les Tours à une distance (euclidienne
    //entre les positions des deux éléments) inférieure à 1.5 case de la cible subissent les mêmes dommages que sa cible.

    @Override
    public List<Tour> cible(){
        Tour cible = null;
        List<Tour> aoe = new ArrayList<>();
        for (Tour t : Game.tours){
            if (inRange(t)){
                if (cible == null) cible = t;
                else if (t.getHp() < cible.getHp()) cible = t;
            }
        }
        if (cible != null){    
            aoe.add(cible);
            for (Tour t : Game.tours){
                double[] ec = t.getCoord();
                double x = ec[0] - cible.getCoord()[0];
                double y = ec[1] - cible.getCoord()[1];
                if (Math.sqrt(x*x + y*y) < (350/MapGame.scale)*2*super.getRange()) aoe.add(t);
            }
        }
        return aoe;
    }
}
