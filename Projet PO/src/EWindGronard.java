import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class EWindGronard extends Enemy{

    public EWindGronard(double[] coord, double spawn){
        super(1, 1, 7, 2, 2, 5, Element.WIND, 1,10,new Color(242, 211, 0), coord, spawn);
    }

    //vise la Tour qui se trouve à sa portée ayant le moins de PV
    @Override
    public List<Tour> cible(){
        double minPV = 9999999;
        Tour cible = null;
        List<Tour> lst = new ArrayList<>();
        for (Tour t : Game.tours){
            if (inRange(t) && t.getHp()<minPV){
                cible = t;
                minPV = t.getHp();
            }
        }
        if (cible != null) lst.add(cible);
        return lst;
    }
}
