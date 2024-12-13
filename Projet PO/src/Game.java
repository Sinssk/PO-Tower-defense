import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game{
    private Map<String, List<String>> mapsPaterns = new HashMap<>();
    //mapsPatern --> key : lvl
    //             value : patern map + waves
    private Map<String, List<String>> waves = new HashMap<>();
    //waves      --> key : wave name
    //             value : ordre spawn enemi(s)
    private Map<Enemy, Integer> enemies = new HashMap<>();
    //enemies    --> key : Ennemi 
    //             value : indice i de la prochaine case à atteidre
    private final MapGame map = new MapGame();
    private final Player player = new Player();
    private final Wave w = new Wave();
    private List<double[]> enemiesPatern;
    private List<Tour> tours = new ArrayList<>();

    private int enemyDestCase = 0;
    private int currentLvl = 1;
    private int currentWve = 0;
    private boolean running = true;
    private double totaltime = 0;

    private double[] b1 = {855, 565, 138, 35};
    private double[] b2 = {855, 490, 138, 35};
    private double[] b3 = {855, 415, 138, 35};
    private double[] b4 = {855, 340, 138, 35};
    private double[] b5 = {855, 265, 138, 35};

    double[] cc = {105, 525};
    Tour tTest = new TArcher(cc);

    private int test = 0;

    public void launch (){
        init();
        long previousTime = System.currentTimeMillis();
        while(isGameRunning()){
            long currentTime = System.currentTimeMillis();
            double deltaTimeSec = (double)(currentTime - previousTime)/1000;
            previousTime = currentTime;

            update(deltaTimeSec);
        }
        if (player.getHp() > 0){
            StdDraw.clear();
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.text(512, 360, "Victoire !");
            StdDraw.setPenColor(Color.GREEN);
            StdDraw.setPenRadius(0.002);
            StdDraw.text(512, 360, "Victoire !");
        }else{
            StdDraw.clear();
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.text(512, 360, "Perdu !");
            StdDraw.setPenColor(Color.RED);
            StdDraw.setPenRadius(0.002);
            StdDraw.text(512, 360, "Perdu !");
        }
        StdDraw.show();
    }
    private boolean isGameRunning(){
        return running;
    }
    
    private void init(){
        //Case S -> x=105.0 | y=595.0
        String filePath = "resources/games/game.g";
        try (BufferedReader readerLvl = new BufferedReader(new FileReader(filePath))) {//ouvrir fichier game.g
            String lvl;
            
            while ((lvl = readerLvl.readLine()) != null) {// récupérer tout les patern de map + waves par level
                try (BufferedReader reader = new BufferedReader(new FileReader("resources/levels/"+lvl+".lvl"))) {
                    //key : lvl, value : patern de la map en indice 0 puis les waves
                    String line;
                    mapsPaterns.put(lvl, new ArrayList<>());
                    while ((line = reader.readLine()) != null) {
                        mapsPaterns.get(lvl).add(line);
                        if ((!waves.containsKey(line))&&(!line.contains("-"))){
                            try (BufferedReader readerWave = new BufferedReader(new FileReader("resources/waves/"+line+".wve"))) {
                                String wave;
                                List<String> l = new ArrayList<>();
                                while ((wave = readerWave.readLine()) != null){
                                    l.add(wave);
                                }
                                waves.put(line, l);
                            } catch (IOException e) {
                                System.out.println("Echec de la recherche du fichier wave : " + e);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Echec de la recherche du fichier level : "+e);
                }
            }
        } catch (IOException e) {
            System.out.println("Echec de la recherche du fichier game : " + e);
        }
    
        map.init(mapsPaterns.get("level1").get(0));    
        map.updateLvl(1, mapsPaterns.size(), 1, mapsPaterns.get("level1").size()-1);
        map.updateHpGold(100, 50);
        map.drawStore();
        List<Enemy> ene = w.createEnemies("resources/waves/waveBoss.wve", map.getCaseS().getCoord().clone());
        for (Enemy e : ene) enemies.put(e, 0);
        enemiesPatern = map.getEnnemyPatern();
        player.setHp(9999);
        List<double[]> testt = map.getEnnemyPatern();
        
    }
    

    private void update(double deltaTimeSec){
        totaltime += deltaTimeSec;
        if (player.isAlive()){
            if (StdDraw.isMousePressed()) {
                double[] coordMouse = {StdDraw.mouseX(), StdDraw.mouseY()};
                map.updateClick(coordMouse);
                if (map.getClikedCase()!=null){
                    if ((coordMouse[0] > b1[0]-b1[2])&&(coordMouse[0] < b1[0]+b1[2])&&
                    (coordMouse[1] > b1[1]-b1[3])&&(coordMouse[1] < b1[1]+b1[3])){
                        if(player.getGold() >= 0){
                            tours.add(new TArcher(map.getClikedCase().getCoord()));
                            player.setGold(player.getGold()-20);
                            map.updateHpGold(player.getHp(), player.getGold());
                            map.setClikedCaseNull();
                        }
                    } else if ((coordMouse[0] > b2[0]-b2[2])&&(coordMouse[0] < b2[0]+b2[2])&&
                    (coordMouse[1] > b2[1]-b2[3])&&(coordMouse[1] < b2[1]+b2[3])){

                    } else if ((coordMouse[0] > b3[0]-b3[2])&&(coordMouse[0] < b3[0]+b3[2])&&
                    (coordMouse[1] > b3[1]-b3[3])&&(coordMouse[1] < b3[1]+b3[3])){

                    } else if ((coordMouse[0] > b4[0]-b4[2])&&(coordMouse[0] < b4[0]+b4[2])&&
                    (coordMouse[1] > b4[1]-b4[3])&&(coordMouse[1] < b4[1]+b4[3])){

                    } else if ((coordMouse[0] > b5[0]-b5[2])&&(coordMouse[0] < b5[0]+b5[2])&&
                    (coordMouse[1] > b5[1]-b5[3])&&(coordMouse[1] < b5[1]+b5[3])){
                    }
                }
            }
            map.drawCases();
            for (Tour t : tours){
                t.draw();
            }
            List<Enemy> enemToRemove = new ArrayList<>();
            for (Enemy e : enemies.keySet()){
                if (totaltime >= e.getSpawn()) {
                    int i = enemies.get(e);
                    if (Arrays.equals(e.getCoord(), enemiesPatern.get(i)) && i < enemiesPatern.size() - 1) {
                        enemies.put(e, enemies.get(e) + 1); // Mise à jour de l'indice de la case à atteindre
                    }
                    if (Arrays.equals(e.getCoord(), enemiesPatern.get(i)) && i == enemiesPatern.size() - 1) {
                        player.setHp(player.getHp() - e.getAtk());
                        map.updateHpGold(player.getHp(), player.getGold());
                        enemToRemove.add(e);
                    }
                    if (e.alive()){
                        e.moveTo(enemiesPatern.get(i));
                        e.draw();
                    }
                }
            }
            if (!enemToRemove.isEmpty()) {
                for (Enemy e : enemToRemove) enemies.remove(e);
            }
            if (enemies.isEmpty()){
                currentWve +=1;
            }
        }
        else running = false;
        
        map.drawStore();
        StdDraw.show();
    } 
}