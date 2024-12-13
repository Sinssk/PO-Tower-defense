import java.awt.Color;

public class CaseR extends Case{
    private final double[] coord;
        
    public CaseR(double[] coord, int[] xyPosition){
        super(coord, xyPosition);
        this.coord = coord;
    }

    @Override
    public void draw(){
        StdDraw.setPenColor(new Color(194, 178, 128));
        StdDraw.filledSquare(coord[0], coord[1], coord[2]-1);
    }
}
