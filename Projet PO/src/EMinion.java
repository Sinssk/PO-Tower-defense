import java.awt.Color;

public class EMinion extends Enemy{
    private int maxHp;
    private int hp;
    private int atk;
    private double atkSpeed;
    private double speed;
    private int range;
    private Element e;
    private int reward;
    private double d;//diamètre de l'ennemi
    private Color c;// couleur de l'ennemi
    private double[] coord;
    private double spawn;

    public EMinion(double[] coord, double spawn){
        super(10, 10, 3, 0, 1, 0, Element.NONE, 1, 7,  Color.BLACK, coord, spawn);
        this.coord = coord;
        this.spawn=spawn;
    }

    //n'attaque pas
    @Override
    public String getType(){
        return "Minion";
    }
}
