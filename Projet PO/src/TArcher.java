import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class TArcher extends Tour{
    public TArcher(double[] coord){
        super(50, 50, 5, 1, 2, Element.NONE, 20, coord, Color.BLACK);
    }

    //Il vise l’ennemi qui se trouve à sa portée le plus avancé sur le chemin de la base.
    @Override
    public List<Enemy> cible(){
        List<Enemy> mostadvanced = new ArrayList<>();
        for (Enemy e : Game.spawnedEnemies){
            if (inRange(e)) mostadvanced.add(e);
        }
        if (!mostadvanced.isEmpty()) {
            mostadvanced.subList(1, mostadvanced.size()).clear();
        }
        return mostadvanced;
    }
}