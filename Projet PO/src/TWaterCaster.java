import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class TWaterCaster extends Tour{
    public TWaterCaster(double[] coord){
        super(30, 30, 7, 1, 4, Element.WATER, 50, coord, new Color(6, 0, 160));
    }

    //Il vise l’ennemi qui se trouve à sa portée le plus avancé sur le chemin de la base.
    @Override
    public List<Enemy> cible(){
        List<Enemy> mostadvanced = new ArrayList<>();
        for (Enemy e : Game.spawnedEnemies){
            if (inRange(e)) mostadvanced.add(e);
        }
        if (!mostadvanced.isEmpty())mostadvanced.subList(1, mostadvanced.size()).clear();
        return mostadvanced;
    }
}