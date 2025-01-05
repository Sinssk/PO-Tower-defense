import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class TFireCaster extends Tour{
    public TFireCaster(double[] coord){
        super(30, 30, 10, 0.5, 2.5, Element.FIRE, 100, coord, new Color(184, 22, 1));
    }
    
    //Il vise l’ennemi qui se trouve à sa portée le plus proche de sa position. Les ennemis à une distance
    //(euclidienne entre les positions des deux éléments) inférieure à 0.75 case de la cible subissent les mˆemes dommages
    //que sa cible.
    @Override
    public List<Enemy> cible(){
        Enemy cible = null;
        List<Enemy> aoe = new ArrayList<>();
        double dist = 999999;
        for (Enemy e : Game.spawnedEnemies){
            double[] a = e.getCoord();
            double x = a[0] - super.getCoord()[0];
            double y = a[1] - super.getCoord()[1];
            double d = Math.sqrt(x*x + y*y);
            if (d < dist && d < MapGame.scale*super.getRange()){
                cible = e;
                dist = d;
            }
        }
        if (cible != null){    
            aoe.add(cible);
            for (Enemy e : Game.spawnedEnemies){
                double[] ec = e.getCoord();
                double x = ec[0] - cible.getCoord()[0];
                double y = ec[1] - cible.getCoord()[1];
                if (Math.sqrt(x*x + y*y) < (350/MapGame.scale)*2*0.75) aoe.add(e);
            }
        }
        return aoe;
    }
}