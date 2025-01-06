/**
 * La classe abstraite Case représente une case (ou tuile) sur la carte du jeu.
 * Elle contient des informations sur les coordonnées et la position et fournit des méthodes
 * pour accéder à ces détails et les afficher.
 */
public abstract class Case {
    private final double[] coord;
    private final int[] xyPosition;

    /**
     * Construit une Case avec les coordonnées et la position spécifiées.
     * 
     * @param coord les coordonnées de la case
     * @param xyPosition la position de la case sous forme de valeurs x et y
     */
    public Case(double[] coord, int[] xyPosition) {
        this.coord = coord;
        this.xyPosition = xyPosition;
    }

    /**
     * Dessine la case. Cette méthode doit être implémentée par les sous-classes.
     */
    public abstract void draw();

    /**
     * Retourne les coordonnées de la case.
     * 
     * @return les coordonnées de la case
     */
    public double[] getCoord() {
        return coord;
    }

    /**
     * Retourne la position de la case sous forme de valeurs x et y.
     * 
     * @return la position de la case
     */
    public int[] getXyPosition() {
        return xyPosition;
    }
}