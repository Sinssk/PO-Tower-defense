import java.awt.Color;

public class EWindGronard extends Enemy{
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

    public EWindGronard(double[] coord, double spawn){
        super(1, 1, 7, 2, 2, 5, Element.WIND, 1,10,new Color(242, 211, 0), coord, spawn);
        this.coord = coord;
        this.spawn=spawn;
    }

    //vise la Tour ayant le moins d'hp

    @Override
    public String getType(){
        return "WindGrognard";
    }
}
