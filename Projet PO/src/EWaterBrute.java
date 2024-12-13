import java.awt.Color;

public class EWaterBrute extends Enemy{
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

    public EWaterBrute(double[] coord, double spawn){
        super(30, 30, 5, 1, 1, 3, Element.WATER, 3, 20, new Color(6, 0, 160), coord, spawn);
        this.coord = coord;
        this.spawn=spawn;
    }

    // vise la Tour qui a le moins de hp. 
    // dégats AoE sur les Tours a 1.5 case de la cible subissent les mêmes dégâts que la cible.

   
    @Override
    public String getType(){
        return "WaterBrute";
    }
    
}
