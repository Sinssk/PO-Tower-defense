import java.awt.Color;

public abstract class Tour {
    private int hp;
    private final int maxHp;
    private final int atk;
    private final double atkSpeed;
    private final double range;
    private final Element e;
    private final int cost;
    private final double[] coord;
    private final Color c;

    public Tour(int hp, int maxHp, int atk, double atkSpeed, double range, Element e, int cost, double[] coord, Color c) {
        this.hp = hp;
        this.maxHp = maxHp;
        this.atk = atk;
        this.atkSpeed = atkSpeed;
        this.range = range;
        this.e = e;
        this.cost = cost;
        this.coord = coord;
        this.c = c;
    }

    public void draw(){
        StdDraw.setPenColor(c);
        StdDraw.filledCircle(coord[0], coord[1], 20);
        StdDraw.setPenColor(Color.BLACK);//bordure 
        StdDraw.filledRectangle(coord[0], coord[1]+25, 21, 4);
        StdDraw.setPenColor(Color.GREEN);//vie en vert
        StdDraw.filledRectangle(coord[0], coord[1]+25, 20, 3);
        if (hp < maxHp){//vie manquante en rouge
            double missingHp= (maxHp-hp)*100/maxHp;
            StdDraw.setPenColor(Color.RED);
            StdDraw.filledRectangle(coord[0]+20-(missingHp/5), coord[1]+40, missingHp/5, 3);
        }
    }

    public void setHp(int n){
        hp= n;
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
    public double getRange() {
        return range;
    }
    public Element getElement() {
        return e;
    }
    public int getCost() {
        return cost;
    }
    public double[] getCoord() {
        return coord;
    }
}