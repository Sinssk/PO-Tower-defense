import java.awt.Color;
import java.util.List;

public abstract class Tour {
    private double hp;
    private final int maxHp;
    private final double atk;
    private final double atkSpeed;
    private final double range;
    private final Element e;
    private final int cost;
    private final double[] coord;
    private final Color c;
    private double lastAttack;

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
        this.lastAttack = 0;
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
            StdDraw.filledRectangle(coord[0]+20-(missingHp/5), coord[1]+25, missingHp/5, 3);
        }
    }

    public abstract List<Enemy> cible();

    // AB = sqrt( (Xb - Xa)² + (Yb - Ya)² )
    public Boolean inRange(Enemy enem){
        double[] a = enem.getCoord();
        double x = (a[0] - coord[0]);
        double y = (a[1] - coord[1]);
        return Math.sqrt(x*x + y*y) < (350/MapGame.scale)*2*range;
    }

    public void setLastAttack(double deltatime){
        lastAttack = deltatime;
    }

    public double getLastAttack(){
        return lastAttack;
    }

    public void setHp(double n){
        hp= n;
    }
    public double getHp() {
        return hp;
    }
    public double getAtk() {
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