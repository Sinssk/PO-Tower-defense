import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class TWindCaster extends Tour{
    public TWindCaster(double[] coord){
        super(30, 30, 5, 1.5, 6, Element.WIND, 50, coord, new Color(242, 211, 0));
    }

    // vise l’ennemi qui se trouve à sa portée le plus proche de sa position.
    @Override
    
    public List<Enemy> cible(){
        Enemy cible = null;
        List<Enemy> enem = new ArrayList<>();
        double dist = 999999;
        for (Enemy e : Game.spawnedEnemies){
            double[] a = e.getCoord();
            double x = a[0] - super.getCoord()[0];
            double y = a[1] - super.getCoord()[1];
            double d = Math.sqrt(x*x + y*y);
            if (d < dist && d < (350/MapGame.scale)*2*super.getRange()){
                cible = e;
                dist = d;
            }
        }
        if (cible != null) enem.add(cible);
        return enem;
    }
}