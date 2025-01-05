import java.awt.Color;
import java.util.Collections;
import java.util.List;

public abstract class Enemy {
    private final int maxHp;
    private double hp;
    private final double atk;
    private final double atkSpeed;
    private final double speed;
    private final int range;
    private final Element e;
    private final int reward;
    private final double d;//diamètre de l'ennemi
    private final Color c;// couleur de l'ennemi
    private double[] coord;
    private final double spawn; // timing de spawn
    private int i;
    private double lastAttack;
    private Boolean show;
    private List<Enemy> mostAdvanced = Game.spawnedEnemies;

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
        this.i = 0;
        this.lastAttack = 0;
        this.show = false;
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
            this.show = true;
        }
    }

    public void moveTo(double[] nextC){//chatgpt
        double[] direction = {nextC[0] - coord[0], nextC[1] - coord[1]};
        double distance = Math.sqrt(direction[0] * direction[0] + direction[1] * direction[1]);
        if (distance > 0) {
            double scale = Math.min((speed/2 )/ distance, 1);
            coord[0] += direction[0] * scale;
            coord[1] += direction[1] * scale;
        }
    }

    public void updateAdvanced(){
        for (int k = 0; k <mostAdvanced.size()-1; k+=2){
            double[] e1 = mostAdvanced.get(k).getCoord();
            double[] e2 = mostAdvanced.get(k+1).getCoord();
            if (e2[0] - e1[0] == 0 && e2[1] - e2[1] < 0 || //comparer les positions sur l'axe des x
            (e2[1] - e1[1] == 0 && e2[0] - e2[0] < 0)) // sur l'axe des y

            Collections.swap(mostAdvanced, k, k+1);
        }
    }

    public Boolean inRange(Tour t){
        double[] a = t.getCoord();
        double x = (a[0] - coord[0]);
        double y = (a[1] - coord[1]);
        return Math.sqrt(x*x + y*y) < (350/MapGame.scale)*2*range;
    }

    public abstract List<Tour> cible();


    public Boolean isShow(){
        return show;
    }
    public void setShow(Boolean b){
        show = b;
    }

    public void setLastAttack(double deltatime){
        lastAttack = deltatime;
    }

    public double getLastAttack(){
        return lastAttack;
    }

    public void increment_i(){
        i += 1;
    }
    public int get_i(){
        return i;
    }
    public boolean alive(){
        return hp > 0;
    }
    public void setHp(double n){
        hp = n;
    }
    public int getMaxHp() {
        return maxHp;
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
}