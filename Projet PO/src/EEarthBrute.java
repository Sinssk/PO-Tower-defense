import java.awt.Color;

public class EEarthBrute extends Enemy{
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

    public EEarthBrute(double[] coord, double spawn){
        super(30, 30, 5, 1, 1, 3, Element.EARTH, 3, 20,new Color(0, 167, 15), coord, spawn);
        this.coord = coord;
        this.spawn=spawn;
    }

    // vise la Tour la plus proche. 

    @Override
    public String getType(){
        return "EarthBrute";
    }
}
