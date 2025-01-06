import java.awt.Color;

/**
 * La classe CaseC représente un type spécifique de case sur la carte du jeu.
 * Elle étend la classe abstraite Case et fournit des implémentations pour la méthode draw
 * et une méthode pour changer la couleur de la case.
 */
public class CaseC extends Case {
    private final double[] coord;
    private Color c = Color.LIGHT_GRAY;

    /**
     * Construit une CaseC avec les coordonnées et la position spécifiées.
     * 
     * @param coord les coordonnées de la case
     * @param xyPosition la position de la case sous forme de valeurs x et y
     */
    public CaseC(double[] coord, int[] xyPosition) {
        super(coord, xyPosition);
        this.coord = coord;
    }

    /**
     * Dessine la case sous forme de carré rempli avec la couleur actuelle.
     */
    @Override
    public void draw() {
        StdDraw.setPenColor(c);
        StdDraw.filledSquare(coord[0], coord[1], coord[2] - 1);
    }

    /**
     * Définit la couleur de la case en gris foncé ou gris clair.
     * 
     * @param t si vrai, définit la couleur en gris foncé ; sinon, en gris clair
     */
    public void setColorDark(boolean t) {
        if (t) c = Color.DARK_GRAY.brighter().brighter();
        else c = Color.LIGHT_GRAY;
    }
}