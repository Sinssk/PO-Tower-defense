import java.awt.Color;

public class CaseS extends Case{
    private final double[] coord;
        
    public CaseS(double[] coord, int[] xyPosition){
        super(coord, xyPosition);
        this.coord = coord;
    }

    @Override
    public void draw(){
        StdDraw.setPenColor(Color.RED);
        StdDraw.filledSquare(coord[0], coord[1], coord[2]-1);
    }
}
