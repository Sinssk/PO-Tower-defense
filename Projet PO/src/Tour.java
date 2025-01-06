import java.awt.Color;
import java.util.List;

/**
 * La classe Tour représente une tour dans le jeu.
 * Elle contient des informations sur les attributs de la tour et des méthodes pour dessiner la tour et vérifier si un ennemi est à portée.
 */
public abstract class Tour {
    private final int maxHp; // points de vie maximum de la tour
    private double hp; // points de vie actuels de la tour
    private final double atk; // dégâts d'attaque de la tour
    private final double atkSpeed; // vitesse d'attaque de la tour
    private final double range; // portée de la tour
    private final Element e; // élément de la tour
    private final int cost; // coût de la tour
    private final double[] coord; // coordonnées de la tour
    private final Color c; // couleur de la tour
    private double lastAttack; // temps de la dernière attaque de la tour
    private final boolean carre; // forme de la tour (carré ou cercle)

    /**
     * Construit une tour avec les attributs spécifiés.
     * 
     * @param maxHp les points de vie maximum de la tour
     * @param hp les points de vie actuels de la tour
     * @param atk les dégâts d'attaque de la tour
     * @param atkSpeed la vitesse d'attaque de la tour
     * @param range la portée de la tour
     * @param e l'élément de la tour
     * @param cost le coût de la tour
     * @param coord les coordonnées de la tour
     * @param c la couleur de la tour
     * @param carre la forme de la tour (carré ou cercle)
     */
    public Tour(int maxHp, int hp, int atk, double atkSpeed, double range, Element e, int cost, double[] coord, Color c, boolean carre) {
        this.maxHp = maxHp;
        this.hp = hp;
        this.atk = atk;
        this.atkSpeed = atkSpeed;
        this.range = range;
        this.e = e;
        this.cost = cost;
        this.coord = coord;
        this.c = c;
        this.lastAttack = 0;
        this.carre = carre;
    }

    /**
     * Dessine la tour sous forme de cercle ou de carré avec une bordure et une barre de vie.
     */
    public void draw() {
        if (!carre) {    
            StdDraw.setPenColor(c);
            StdDraw.filledCircle(coord[0], coord[1], 20);
        } else {
            StdDraw.setPenColor(c);
            StdDraw.filledSquare(coord[0], coord[1], 20);
        }
        StdDraw.setPenColor(Color.BLACK); // bordure 
        StdDraw.filledRectangle(coord[0], coord[1] + 25, 21, 4);
        StdDraw.setPenColor(Color.GREEN); // vie en vert
        StdDraw.filledRectangle(coord[0], coord[1] + 25, 20, 3);
        if (hp < maxHp) { // vie manquante en rouge
            double missingHp = (maxHp - hp) * 100 / maxHp;
            StdDraw.setPenColor(Color.RED);
            StdDraw.filledRectangle(coord[0] + 20 - (missingHp / 5), coord[1] + 25, missingHp / 5, 3);
        }
    }

    /**
     * Méthode abstraite pour cibler les ennemis. Doit être implémentée par les sous-classes.
     * 
     * @return une liste d'ennemis ciblés
     */
    public abstract List<Enemy> cible();

    /**
     * Vérifie si un ennemi est à portée de la tour.
     * 
     * @param enem l'ennemi à vérifier
     * @return true si l'ennemi est à portée, sinon false
     */
    public Boolean inRange(Enemy enem) {
        double[] a = enem.getCoord();
        double x = (a[0] - coord[0]);
        double y = (a[1] - coord[1]);
        return Math.sqrt(x * x + y * y) < (350 / MapGame.scale) * 2 * range;
    }

    /**
     * Définit le temps de la dernière attaque de la tour.
     * 
     * @param deltatime le temps de la dernière attaque
     */
    public void setLastAttack(double deltatime) {
        lastAttack = deltatime;
    }

    /**
     * Retourne le temps de la dernière attaque de la tour.
     * 
     * @return le temps de la dernière attaque
     */
    public double getLastAttack() {
        return lastAttack;
    }

    /**
     * Retourne les points de vie actuels de la tour.
     * 
     * @return les points de vie actuels de la tour
     */
    public double getHp() {
        return hp;
    }

    /**
     * Définit les points de vie actuels de la tour.
     * 
     * @param hp les nouveaux points de vie de la tour
     */
    public void setHp(double hp) {
        this.hp = hp;
    }

    /**
     * Retourne les dégâts d'attaque de la tour.
     * 
     * @return les dégâts d'attaque de la tour
     */
    public double getAtk() {
        return atk;
    }

    /**
     * Retourne la vitesse d'attaque de la tour.
     * 
     * @return la vitesse d'attaque de la tour
     */
    public double getAtkSpeed() {
        return atkSpeed;
    }

    /**
     * Retourne la portée de la tour.
     * 
     * @return la portée de la tour
     */
    public double getRange() {
        return range;
    }

    /**
     * Retourne l'élément de la tour.
     * 
     * @return l'élément de la tour
     */
    public Element getElement() {
        return e;
    }

    /**
     * Retourne le coût de la tour.
     * 
     * @return le coût de la tour
     */
    public int getCost() {
        return cost;
    }

    /**
     * Retourne les coordonnées de la tour.
     * 
     * @return les coordonnées de la tour
     */
    public double[] getCoord() {
        return coord;
    }

    /**
     * Retourne la couleur de la tour.
     * 
     * @return la couleur de la tour
     */
    public Color getColor() {
        return c;
    }

    /**
     * Retourne la forme de la tour (carré ou cercle).
     * 
     * @return true si la tour est un carré, sinon false
     */
    public boolean isCarre() {
        return carre;
    }
}