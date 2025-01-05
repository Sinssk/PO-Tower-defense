import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class TEarthCaster extends Tour{
    public TEarthCaster(double[] coord){
        super(50, 50, 7, 0.5, 2.5, Element.FIRE, 100, coord, new Color(0, 167, 15));
    }

     // vise l’ennemi qui se trouve à sa portée ayant le plus grand nombre de PV. Les ennemis à moins de
    // 1.0 case de la cible subissent les mêmes dommages que sa cible.

    @Override
    public List<Enemy> cible(){
        Enemy cible = null;
        List<Enemy> aoe = new ArrayList<>();
        for (Enemy e : Game.spawnedEnemies){
            if (inRange(e)){
                if (cible == null) cible = e;
                else if (e.getHp() > cible.getHp())cible = e;
            }
        }
        if (cible != null){   
            aoe.add(cible);
            for (Enemy e : Game.spawnedEnemies){
                double[] ec = e.getCoord();
                double x = ec[0] - cible.getCoord()[0];
                double y = ec[1] - cible.getCoord()[1];
                if (Math.sqrt(x*x + y*y) < (350/MapGame.scale)*2) aoe.add(e);
            }
        }
        return aoe;
    }
}