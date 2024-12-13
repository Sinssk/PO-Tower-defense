import java.awt.Color;

public class CaseX extends Case{
    private final double[] coord;

    public CaseX(double[] coord, int[] xyPosition){
        super(coord, xyPosition);
        this.coord = coord;
    }

    @Override
    public void draw(){
        StdDraw.setPenColor(Color.GREEN.darker().darker());
        StdDraw.filledSquare(coord[0], coord[1], coord[2]-1);
    }
}
