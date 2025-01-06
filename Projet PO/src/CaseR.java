import java.awt.Color;

/**
 * La classe CaseR représente un type spécifique de case sur la carte du jeu.
 * Elle étend la classe abstraite Case et fournit une implémentation pour la méthode draw.
 */
public class CaseR extends Case {
    private final double[] coord;

    /**
     * Construit une CaseR avec les coordonnées et la position spécifiées.
     * 
     * @param coord les coordonnées de la case
     * @param xyPosition la position de la case sous forme de valeurs x et y
     */
    public CaseR(double[] coord, int[] xyPosition) {
        super(coord, xyPosition);
        this.coord = coord;
    }

    /**
     * Dessine la case sous forme de carré rempli avec une couleur spécifique.
     */
    @Override
    public void draw() {
        StdDraw.setPenColor(new Color(194, 178, 128));
        StdDraw.filledSquare(coord[0], coord[1], coord[2] - 1);
    }
}