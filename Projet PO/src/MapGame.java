import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapGame{
    private List<double[]> coordPatern = new ArrayList<>();
    private List<List<Case>> cases = new ArrayList<>();

    public static double scale;
    private double mX;
    private double mY;
    private double currentX;
    private double currentY;
    private Case clikedCase;

    private final double[] b1 = {855, 565, 138, 35};
    private final double[] b2 = {855, 490, 138, 35};
    private final double[] b3 = {855, 415, 138, 35};
    private final double[] b4 = {855, 340, 138, 35};
    private final double[] b5 = {855, 265, 138, 35};

    //cration de la fenêtre (x: [-12; 1012], y: [-10; 710]) --> 1024 X 720
    //taille map lvl --> x = patern.get(0).length() y = patern.size()
    //zone Map --> center: (350, 350), halfDist: (350, 350)
    //zone level --> center: (856, 688), halfDist: (144, 12)
    //zone player --> center: (856, 641), halfDist: (144, 25)
    //zone store --> center: (856, 303), halfDist: (144, 303)

    public void init(String mapFile){
        loadMap(mapFile);
        StdDraw.setCanvasSize(1024, 720);
        StdDraw.setXscale(-12, 1012);
        StdDraw.setYscale(-10, 710);
        StdDraw.enableDoubleBuffering();
        drawMap();
    }

    private void loadMap(String fileName){
        List<String> map = new ArrayList<>();
        String filePath = "resources/maps/" + fileName + ".mtp";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = reader.readLine()) != null){
                map.add(line);
            }
            mX = map.get(0).length();
            mY = map.size();

            scale = Math.max(mX, mY); //permet de determiner la taille des carré en fonction de la dimension de la map la plus grande

            currentX = 350/scale;
            currentY = 700-350/scale;

        } catch (IOException e){
            System.out.println("Fichier map non trouvé : " + e);
        }
        int yc = 0;
        for (String line : map){
            int xc = 0;
            cases.add(new ArrayList<>());
            for (char c : line.toCharArray()){
                double[] coord = new double[3];
                int[] xyCoord = new int[2];
                xyCoord[0] = xc;
                xyCoord[1] = yc;
                coord[0] = currentX;
                coord[1] = currentY;
                coord[2] = 350/scale;
                switch (c) {
                    case 'X' -> cases.get(yc).add(new CaseX(coord, xyCoord));
                    case 'C' -> cases.get(yc).add(new CaseC(coord, xyCoord));
                    case 'R' -> {
                        cases.get(yc).add(new CaseR(coord, xyCoord));
                    }
                    case 'S' -> {
                        cases.get(yc).add(new CaseS(coord, xyCoord));
                    }
                    case 'B' -> {
                        cases.get(yc).add(new CaseB(coord, xyCoord));
                    }
                    default -> {}//Execption
                }
                currentX+=700/scale;
                xc+=1;
            }
            yc+=1;
            currentX=350/scale;
            currentY-=(700/scale);
        }
        loadEnemyPatern();
    }

    private void loadEnemyPatern(){
        Case s = getCaseS();
        Case b = getCaseB();
        coordPatern.add(s.getCoord());
        int x = s.getXyPosition()[0];
        int y = s.getXyPosition()[1];
        double[] cLast = s.getCoord();

        while (coordPatern.getLast() != b.getCoord()){
            
            //on regarde les 4 cases adjacentes
            Case gauche = cases.get(y).get(x-1);
            Case haut   = cases.get(y-1).get(x);
            Case droite = cases.get(y).get(x+1);
            Case bas    = cases.get(y+1).get(x);

            //on vérifie qu'on ne revienne pas sur la case qui viens d'être parcourue
            if (coordPatern.size() > 1) cLast = coordPatern.get(coordPatern.size()-2); 

            if (gauche.getCoord() != cLast && (gauche instanceof CaseR || gauche instanceof CaseB)){
                coordPatern.add(gauche.getCoord());
                x -=1;
            } else if (haut.getCoord() != cLast && (haut instanceof CaseR || haut instanceof CaseB)){
                coordPatern.add(haut.getCoord());
                y -=1;
            } else if (droite.getCoord() != cLast && (droite instanceof CaseR || droite instanceof CaseB)){
                coordPatern.add(droite.getCoord());
                x +=1;
            } else if (bas.getCoord() != cLast && (bas instanceof CaseR || bas instanceof CaseB)){
                coordPatern.add(bas.getCoord());
                y +=1;
            }
        }
    }

    private void drawMap(){
        StdDraw.clear(new Color(202, 207, 210));//background
        StdDraw.setPenColor(new Color(44, 62, 80));
        StdDraw.filledRectangle(350, 350, 350, 350);//grille map

        StdDraw.setPenRadius(0.01);

        StdDraw.setPenColor(new Color(165, 105, 189));//violet
        StdDraw.rectangle(350, 350, 351, 351);//zone map

        StdDraw.setPenColor(new Color(236, 112,99));//rouge
        StdDraw.rectangle(856, 688, 144, 12);//zone level

        StdDraw.setPenColor(new Color(88, 214, 141));//vert
        StdDraw.rectangle(856, 641, 144, 25);//zone player
        
        StdDraw.setPenColor(new Color(93, 173, 226));//bleu
        StdDraw.rectangle(856, 303, 144, 303);//zone store

        StdDraw.setPenRadius(0.002);

        drawCases();
    }

    public void drawCases(){
        StdDraw.setPenColor(new Color(44, 62, 80));
        StdDraw.filledRectangle(350, 350, 350, 350);//grille map
        
        StdDraw.setPenRadius(0.002);
        for (int y=0; y<cases.size(); y++){
            for (int x=0; x<cases.get(y).size(); x++){
                cases.get(y).get(x).draw();
            }
        }
        if (mX != mY){//remplir l'espace vide
            StdDraw.setPenColor(new Color(119,136,153));
            if(mX < mY){// mX*(700/scale)+((mY-mX)*(700/scale))/2 = (700*(mX+mY))/(2*scale) 
                        // ((mY-mX)*(700/scale))/2 = (700*(mY-mX))/(2*scale)       --> simplification des formules avec chatgpt
                StdDraw.filledRectangle((700*(mX+mY))/(2*scale), 350, (700*(mY-mX))/(2*scale), 350);
            } else { StdDraw.filledRectangle(350,(700*(mX-mY))/(2*scale), 350, (700*(mX-mY))/(2*scale));}
        }
    }

    public void updateClick(double[] coordMouse){
        //(cx = 350, cy = 350, x+-351, y+-351) zone map
        if ((coordMouse[0]>=0)&&(coordMouse[0]<=700)&&(coordMouse[1]>=-1)&&(coordMouse[1]<=701)){
            for (int x=0; x<cases.size(); x++){
                for (int y=0; y<cases.get(x).size(); y++){
                    Case c = cases.get(x).get(y);
                    if (c instanceof CaseC caseC){
                        double[] caseCoord = c.getCoord();
                        double xMin = caseCoord[0]-caseCoord[2];
                        double xMax = caseCoord[0]+caseCoord[2];
                        double yMin = caseCoord[1]-caseCoord[2];
                        double yMax = caseCoord[1]+caseCoord[2];
                        if (((xMin < coordMouse[0]) && (coordMouse[0] < xMax)) && ((yMin < coordMouse[1]) && (coordMouse[1] < yMax))) {
                            if (clikedCase == null){
                                clikedCase = c;
                                caseC.setColorDark(true);
                                c.draw();
                            }else{
                                ((CaseC)clikedCase).setColorDark(false);
                                clikedCase.draw();
                                clikedCase = c;
                                caseC.setColorDark(true);
                                c.draw();
                            } 
                            return;
                        }
                    }
                }
            }
            if (clikedCase != null){
                ((CaseC)clikedCase).setColorDark(false);
                clikedCase.draw();
                clikedCase = null;
            }
        }
    }

    public void setClikedCaseNull(){
        ((CaseC)clikedCase).setColorDark(false);
        clikedCase.draw();
        clikedCase = null;
    }

    private void drawLogos(){
        StdDraw.setPenColor( new Color(44, 62, 80));
        double x = 975;
        double y = 641;
        double halfHeight = 16;
        double [] listXborder = new double []
            {
                x,
                x - halfHeight,
                x - halfHeight,
                x - 0.66 * halfHeight,
                x - 0.33 * halfHeight,
                x,
                x + 0.33 * halfHeight,
                x + 0.66 * halfHeight,
                x + halfHeight,
                x + halfHeight,
                };
        double [] listYborder = new double []
            {
                y - halfHeight,
                y,
                y + 0.5 * halfHeight,
                y + halfHeight,
                y + halfHeight,
                y + 0.5 * halfHeight,
                y + halfHeight,
                y + halfHeight,
                y + 0.5 * halfHeight,
                y,
            };
        StdDraw.filledPolygon(listXborder, listYborder);//contour du coeur
        halfHeight = 15.2;
        StdDraw.setPenColor( new Color(223, 75, 95));
        double [] listX = new double []
            {
                x,
                x - halfHeight,
                x - halfHeight,
                x - 0.66 * halfHeight,
                x - 0.33 * halfHeight,
                x,
                x + 0.33 * halfHeight,
                x + 0.66 * halfHeight,
                x + halfHeight,
                x + halfHeight,
            };
        double [] listY = new double []
            {
                y - halfHeight,
                y,
                y + 0.5 * halfHeight,
                y + halfHeight,
                y + halfHeight,
                y + 0.5 * halfHeight,
                y + halfHeight,
                y + halfHeight,
                y + 0.5 * halfHeight,
                y,
            };
        StdDraw.filledPolygon(listX, listY);//coeur

        StdDraw.setPenColor(new Color (44, 62, 80));//piece
        StdDraw.filledCircle(740,641,18*1.03);
        StdDraw.setPenColor(new Color (241,196 ,15));
        StdDraw.filledCircle(740,641,18);
        StdDraw.setPenColor(new Color (212,172 ,13));
        StdDraw.filledCircle(740,641,0.7 * 18);
    }

    public void updateLvl(int lvl, int maxLvl, int wave, int maxWave){
        StdDraw.setPenColor(new Color(202, 207, 210));
        StdDraw.filledRectangle(856, 688, 140, 8);//zone level
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(750, 686, "LVL: "+lvl+"/"+maxLvl);
        StdDraw.text(952, 686, "WAVE: "+wave+"/"+maxWave);
    }
    
    public void updateHpGold(int hp, int gold){
        StdDraw.setPenColor(new Color(202, 207, 210));
        StdDraw.filledRectangle(856, 641, 140, 22);//zone player
        drawLogos();
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(930, 639,""+hp);
        StdDraw.text(780, 639,""+gold);
    }

    public void drawStore(){
        StdDraw.setPenColor(new Color(202, 207, 210));
        StdDraw.filledRectangle(856, 303, 140, 300);//zone store

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.rectangle(b1[0], b1[1], b1[2], b1[3]);//bouton Archer Tour
        StdDraw.rectangle(b2[0], b2[1], b2[2], b2[3]);//bouton Earth Caster 
        StdDraw.rectangle(b3[0], b3[1], b3[2], b3[3]);//bouton Water Caster 
        StdDraw.rectangle(b4[0], b4[1], b4[2], b4[3]);//bouton Fire Caster 
        StdDraw.rectangle(b5[0], b5[1], b5[2], b5[3]);//bouton Wind Caster 

        StdDraw.filledCircle(745, 565, 20);//Archer (Color.BLACK)
        StdDraw.text(840, 585, "HP : 30 | ATK : 5");
        StdDraw.text(852, 565, "SPD : 1 | RANGE : 2");
        StdDraw.text(815, 545, "COST : 20");

        StdDraw.setPenColor(new Color(0, 167, 15));//
        StdDraw.filledCircle(745, 490, 20);//Earth Caster
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(843, 510, "HP : 50 | ATK : 7");
        StdDraw.text(868, 490, "SPD : 0.5 | RANGE : 2.5");
        StdDraw.text(820, 470, "COST : 100");

        StdDraw.setPenColor(new Color(6, 0, 160));
        StdDraw.filledCircle(745, 415, 20);//Water Caster
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(840, 435, "HP : 30 | ATK : 3");
        StdDraw.text(852, 415, "SPD : 1 | RANGE : 4");
        StdDraw.text(815, 395, "COST : 50");

        StdDraw.setPenColor(new Color(184, 22, 1));
        StdDraw.filledCircle(745, 340, 20);//Fire Caster
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(845, 360, "HP : 30 | ATK : 10");
        StdDraw.text(866, 340, "SPD : 0.5 | RANGE : 2.5");
        StdDraw.text(815, 320, "COST : 30");

        StdDraw.setPenColor(new Color(242, 211, 0));
        StdDraw.filledCircle(745, 265, 20);//Wind Caster 
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(840, 285, "HP : 30 | ATK : 5");
        StdDraw.text(858, 265, "SPD : 1.5 | RANGE : 6");
        StdDraw.text(813, 245, "COST : 50");
    }

    public Case getCaseS() {
        for (List<Case> line : cases) {
            for (Case c : line) {
                if (c instanceof CaseS) return c; 
            }
        }
        return null;//Exception
    }

    public Case getCaseB() {
        for (List<Case> line : cases) {
            for (Case c : line) {
                if (c instanceof CaseB) return c; 
            }
        }
        return null;//Exception
    }

    public List<double[]> getEnnemyPatern(){
        return coordPatern;
    }

    public Case getClikedCase(){
        return clikedCase;
    }
}