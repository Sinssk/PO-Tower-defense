import java.awt.Color;

public class CaseC extends Case{
    private final double[] coord;
    private Color c = Color.LIGHT_GRAY;

    public CaseC(double[] coord, int[] xyPosition){
        super(coord, xyPosition);
        this.coord = coord;
    }

    @Override
    public void draw(){
        StdDraw.setPenColor(c);
        StdDraw.filledSquare(coord[0], coord[1], coord[2]-1);
    }

    public void setColorDark(boolean t){
        if (t) c = Color.DARK_GRAY;
        else c = Color.LIGHT_GRAY;
    }
}
