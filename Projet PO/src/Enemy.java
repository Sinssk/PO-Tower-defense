import java.awt.Color;
import java.util.Collections;
import java.util.List;

public abstract class Enemy {
    private final int maxHp;
    private double hp;
    private final double atk;
    private double atkSpeed;
    private double speed;
    private final int range;
    private final Element e;
    private final int reward;
    private final double d;//diamètre de l'ennemi
    private final Color c;// couleur de l'ennemi
    private double[] coord;
    private final double spawn; // timing de spawn
    private int i;// index de l'ennemi
    private double lastAttack;// temps de la dernière attaque de l'ennemi
    private Boolean show;// visibilité de l'ennemi
    private double poisoned;// quantité de poison affectant l'ennemi
    private double speedPoison;// vitesse à laquelle l'ennemi est empoisonné
    private double lastHitPoison;// temps de la dernière application du poison

    /**
     * Construit un ennemi avec les attributs spécifiés.
     * 
     * @param maxHp les points de vie maximum de l'ennemi
     * @param hp les points de vie actuels de l'ennemi
     * @param atk les dégâts d'attaque de l'ennemi
     * @param atkSpeed la vitesse d'attaque de l'ennemi
     * @param speed la vitesse de déplacement de l'ennemi
     * @param range la portée de l'ennemi
     * @param e l'élément de l'ennemi
     * @param reward la récompense pour tuer l'ennemi
     * @param d le diamètre de l'ennemi
     * @param c la couleur de l'ennemi
     * @param coord les coordonnées de l'ennemi
     * @param spawn le temps de spawn de l'ennemi
     */
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
        this.poisoned = 0;
        this.speedPoison = 0;
        this.lastHitPoison = 0;
    }

    /**
     * Dessine l'ennemi sous forme de cercle avec une bordure et une barre de vie.
     */
    public void draw() {
        if (hp > 0) {
            // afficher l'ennemi (cercle)
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.filledCircle(coord[0], coord[1], d + 1); // bordure
            StdDraw.setPenColor(c);
            StdDraw.filledCircle(coord[0], coord[1], d); // cercle de couleur c et de diamètre d
            
            StdDraw.setPenColor(Color.BLACK); // bordure 
            StdDraw.filledRectangle(coord[0], coord[1] + d + 10, 21, 4);
            StdDraw.setPenColor(Color.GREEN); // vie en vert
            StdDraw.filledRectangle(coord[0], coord[1] + d + 10, 20, 3);
            if (hp < maxHp) { // vie manquante en rouge
                double missingHp = (maxHp - hp) * 100 / maxHp;
                StdDraw.setPenColor(Color.RED);
                StdDraw.filledRectangle(coord[0] + 20 - (missingHp / 5), coord[1] + d + 10, missingHp / 5, 3);
            }
            this.show = true;
        }
    }

    /**
     * Déplace l'ennemi vers les coordonnées spécifiées.
     * 
     * @param nextC les coordonnées vers lesquelles l'ennemi doit se déplacer
     */
    public void moveTo(double[] nextC) {
        double[] direction = {nextC[0] - coord[0], nextC[1] - coord[1]};
        double distance = Math.sqrt(direction[0] * direction[0] + direction[1] * direction[1]);
        if (distance > 0) {
            double scale = Math.min((speed / 2) / distance, 1);
            coord[0] += direction[0] * scale;
            coord[1] += direction[1] * scale;
        }
    }

    /**
     * Met à jour la liste des ennemis avancés en échangeant leurs positions si nécessaire.
     */
    public static void updateAdvanced() {
        for (int k = 0; k < Game.spawnedEnemies.size() - 1; k += 2) {
            double[] e1 = Game.spawnedEnemies.get(k).getCoord();
            double[] e2 = Game.spawnedEnemies.get(k + 1).getCoord();
            if ((e2[0] - e1[0] == 0 && e2[1] - e2[1] < 0) || // comparer les positions sur l'axe des x
                (e2[1] - e1[1] == 0 && e2[0] - e2[0] < 0)) // sur l'axe des y
                Collections.swap(Game.spawnedEnemies, k, k + 1);
        }
    }

    /**
     * Vérifie si une tour est à portée de l'ennemi.
     * 
     * @param t la tour à vérifier
     * @return true si la tour est à portée, sinon false
     */
    public Boolean inRange(Tour t) {
        double[] a = t.getCoord();
        double x = (a[0] - coord[0]);
        double y = (a[1] - coord[1]);
        return Math.sqrt(x * x + y * y) < (350 / MapGame.scale) * 2 * range;
    }

    /**
     * Méthode abstraite pour cibler les tours. Doit être implémentée par les sous-classes.
     * 
     * @return une liste de tours ciblées
     */
    public abstract List<Tour> cible();

    /**
     * Retourne la quantité de poison affectant l'ennemi.
     * 
     * @return la quantité de poison
     */
    public double getPoisoned() {
        return poisoned;
    }

    /**
     * Définit la quantité de poison et la vitesse à laquelle l'ennemi est empoisonné.
     * 
     * @param n la quantité de poison
     * @param speedP la vitesse du poison
     */
    public void setPoisoned(double n, double speedP) {
        poisoned = n;
        speedPoison = speedP;
    }

    /**
     * Applique les effets du poison à l'ennemi en fonction du temps écoulé.
     * 
     * @param deltatime le temps écoulé depuis la dernière mise à jour
     */
    public void poisoned(double deltatime) {
        if (deltatime > lastHitPoison + speedPoison) {
            hp -= poisoned;
            lastHitPoison = deltatime;
        }
    }
    /**
     * Vérifie si l'ennemi est visible.
     * 
     * @return true si l'ennemi est visible, sinon false
     */
    public Boolean isShow() {
        return show;
    }

    /**
     * Définit la visibilité de l'ennemi.
     * 
     * @param b true pour rendre l'ennemi visible, sinon false
     */
    public void setShow(Boolean b) {
        show = b;
    }

    /**
     * Définit le temps de la dernière attaque de l'ennemi.
     * 
     * @param deltatime le temps de la dernière attaque
     */
    public void setLastAttack(double deltatime) {
        lastAttack = deltatime;
    }

    /**
     * Retourne le temps de la dernière attaque de l'ennemi.
     * 
     * @return le temps de la dernière attaque
     */
    public double getLastAttack() {
        return lastAttack;
    }

    /**
     * Incrémente l'index de l'ennemi.
     */
    public void increment_i() {
        i += 1;
    }

    /**
     * Retourne l'index de l'ennemi.
     * 
     * @return l'index de l'ennemi
     */
    public int get_i() {
        return i;
    }

    /**
     * Vérifie si l'ennemi est en vie.
     * 
     * @return true si l'ennemi a des points de vie restants, sinon false
     */
    public boolean alive() {
        return hp > 0;
    }

    /**
     * Définit les points de vie de l'ennemi.
     * 
     * @param n les nouveaux points de vie de l'ennemi
     */
    public void setHp(double n) {
        hp = n;
    }

    /**
     * Retourne les points de vie maximum de l'ennemi.
     * 
     * @return les points de vie maximum de l'ennemi
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Retourne les points de vie actuels de l'ennemi.
     * 
     * @return les points de vie actuels de l'ennemi
     */
    public double getHp() {
        return hp;
    }
    /**
     * Retourne les points d'attaque actuels de l'ennemi.
     * 
     * @return les points d'attaque actuels de l'ennemi
     */
    public double getAtk() {
        return atk;
    }
    /**
     * Retourne la vitesse d'attaque actuels de l'ennemi.
     * 
     * @return la vitesse  d'attaque actuels de l'ennemi
     */
    public double getAtkSpeed() {
        return atkSpeed;
    }
    /**
     * Définit la vitesse d'attaque de l'ennemi.
     * 
     * @param n Définit la vitesse d'attaque de l'ennemi
     */
    public void setAtkSpeed(double n){
        atkSpeed = n;
    }
    /**
     * Retourne la vitesse actuel de l'ennemi.
     * 
     * @return la vitesse actuel de l'ennemi
     */
    public double getSpeed() {
        return speed;
    }
    /**
     * Définit la vitesse de l'ennemi.
     * 
     * @param n Définit la vitesse de l'ennemi
     */
    public void setSpeed(double n){
        speed = n;
    }
    /**
     * Retourne la portée actuel de l'ennemi.
     * 
     * @return la portée actuel de l'ennemi
     */
    public int getRange() {
        return range;
    }
    /**
     * Retourne l'élémment actuel de l'ennemi.
     * 
     * @return l'élémment actuel de l'ennemi
     */
    public Element getElement() {
        return e;
    }
    /**
     * Retourne la valeur de récompense de l'ennemi.
     * 
     * @return la valeur de récompense de l'ennemi
     */
    public int getReward() {
        return reward;
    }
    /**
     * Retourne le diamètre de l'ennemi.
     * 
     * @return le diamètre de l'ennemi
     */
    public double getD() {
        return d;
    }
    /**
     * Retourne la couleur de l'ennemi.
     * 
     * @return la couleur de l'ennemi
     */
    public Color getC() {
        return c;
    }
    /**
     * Retourne les coordonnées de l'ennemi.
     * 
     * @return les coordonnées de l'ennemi
     */
    public double[] getCoord(){
        return coord;
    }
    /**
     * Retourne le timming d'apparition de l'ennemi.
     * 
     * @return le timming d'apparition de l'ennemi
     */
    public double getSpawn(){
        return spawn;
    }
}