import java.awt.Color;

public class EBoss extends Enemy{
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

    public EBoss(double[] coord, double spawn){
        super(150, 150, 100, 10, 0.5, 2, Element.FIRE, 100, 40, new Color(142, 16, 0), coord, spawn);
        this.coord = coord;
        this.spawn=spawn;
    }

    // vise la Tour la plus proche. 

    @Override
    public String getType(){
        return "Boss";
    }
}
