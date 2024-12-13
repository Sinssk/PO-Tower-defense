
import java.awt.Color;

public abstract class Enemy {
    private final int maxHp;
    private int hp;
    private final int atk;
    private final double atkSpeed;
    private final double speed;
    private final int range;
    private final Element e;
    private final int reward;
    private final double d;//diamètre de l'ennemi
    private final Color c;// couleur de l'ennemi
    private double[] coord;
    private double spawn;

    public Enemy(int maxHp, int hp, int atk, double atkSpeed, double speed, int range, Element e, int reward, double d, Color c, double[] coord, double spawn){
        this.maxHp = maxHp;
        this.hp = hp;
        this.atk = atk;
        this.atkSpeed = atkSpeed;
        this.speed = speed;
        this.range = range;
        this.e = e;
        this.reward = reward;
        this.d = d;
        this.c = c;
        this.coord = coord;
        this.spawn = spawn;
    }

    public void draw(){
        if (hp > 0){
            //afficher l'ennemi (cercle)
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.filledCircle(coord[0], coord[1], d+1);//bordure
            StdDraw.setPenColor(c);
            StdDraw.filledCircle(coord[0], coord[1], d);//cerlce de couleur c et de diamètre d
            
            StdDraw.setPenColor(Color.BLACK);//bordure 
            StdDraw.filledRectangle(coord[0], coord[1]+d+10, 21, 4);
            StdDraw.setPenColor(Color.GREEN);//vie en vert
            StdDraw.filledRectangle(coord[0], coord[1]+d+10, 20, 3);
            if (hp < maxHp){//vie manquante en rouge
                double missingHp= (maxHp-hp)*100/maxHp;
                StdDraw.setPenColor(Color.RED);
                StdDraw.filledRectangle(coord[0]+20-(missingHp/5), coord[1]+d+10, missingHp/5, 3);
            }
        }
    }

    public void moveTo(double[] nextC){
        if      ((coord[0] == nextC[0])&&(coord[1] < nextC[1])) {coord[1] += speed;}//aller vers haut
        else if ((coord[0] == nextC[0])&&(coord[1] > nextC[1])) {coord[1] -= speed;}//aller vers bas
        else if ((coord[1] == nextC[1])&&(coord[0] < nextC[0])) {coord[0] += speed;}//aller vers droite
        else if ((coord[1] == nextC[1])&&(coord[0] > nextC[0])) {coord[0] -= speed;}//aller vers gauche
        
    }

    public boolean alive(){
        return hp > 0;
    }
    public void setHp(int n){
        hp = n;
    }
    public int getMaxHp() {
        return maxHp;
    }
    public int getHp() {
        return hp;
    }
    public int getAtk() {
        return atk;
    }
    public double getAtkSpeed() {
        return atkSpeed;
    }
    public double getSpeed() {
        return speed;
    }
    public int getRange() {
        return range;
    }
    public Element getElement() {
        return e;
    }
    public int getReward() {
        return reward;
    }
    public double getD() {
        return d;
    }
    public Color getC() {
        return c;
    }
    public double[] getCoord(){
        return coord;
    }
    public double getSpawn(){
        return spawn;
    }
    public abstract String getType();
    
}

