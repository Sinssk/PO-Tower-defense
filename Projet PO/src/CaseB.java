
import java.awt.Color;

public class CaseB extends Case{
    private final double[] coord;
 
    public CaseB(double[] coord, int[] xyPosition){
        super(coord, xyPosition);
        this.coord = coord;
    }

    @Override
    public void draw(){
        StdDraw.setPenColor(Color.ORANGE);
        StdDraw.filledSquare(coord[0], coord[1], coord[2]-1);
    }
}
