import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class EMinion extends Enemy{

    public EMinion(double[] coord, double spawn){
        super(10, 10, 3, 0, 1, 0, Element.NONE, 1, 7,  Color.BLACK, coord, spawn);
    }
    
    @Override
    public List<Tour> cible(){
        return new ArrayList<>();
    }
    //n'attaque pas
}
