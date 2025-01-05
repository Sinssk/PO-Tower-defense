public abstract class Case {
    private final double[] coord;
    private final int[] xyPosition;        

    public Case(double[] coord, int[] xyPosition){
        this.coord = coord;
        this.xyPosition = xyPosition;
    }

    public abstract void draw();
    
    public double[] getCoord(){
        return coord;
    }
    public int[] getXyPosition(){
        return xyPosition;
    }
    public void printCoord(){
        System.out.println(coord[0] + ":" + coord[1]);
    }
    @Override
    public String toString() {
        return "coord= [" + coord[0] + ", " + coord[1] + ", " + coord[2] + "]" + ", Position [x, y]= ["+xyPosition[0]+", "+xyPosition[1]+"]";
    }
}