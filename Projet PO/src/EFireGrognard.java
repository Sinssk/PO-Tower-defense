import java.awt.Color;

public class EFireGrognard extends Enemy{
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
    
    public EFireGrognard(double[] coord, double spawn){
        super(1, 1, 7, 2, 2, 3, Element.FIRE, 1, 10, new Color(184, 22, 1), coord, spawn);
        this.coord = coord;
        this.spawn=spawn;
    }

    // vise la Tour la plus proche. 
    //dégats AoE sur les Tours a 1.5 case max de la cible subissent les mêmes dégâts que la cible.
   
    @Override
    public String getType(){
        return "FireGrognard";
    }
}
