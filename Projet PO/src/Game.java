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
    private final MapGame map = new MapGame();
    private final Player player = new Player();
    private final Wave w = new Wave();
    private final Fight combats = new Fight();
    private List<double[]> enemiesPatern;

    private static List<Enemy> enemies = new ArrayList<>();
    public static List<Tour> tours = new ArrayList<>();
    public static List<Enemy> spawnedEnemies = new ArrayList<>();

    private int currentLvl = 1;
    private int currentWve = 1;
    private boolean running = true;
    private double totaltime = 0;

    private final double[] b1 = {855, 565, 138, 35};// TArcher
    private final double[] b2 = {855, 490, 138, 35};// TEarthCaster
    private final double[] b3 = {855, 415, 138, 35};
    private final double[] b4 = {855, 340, 138, 35};
    private final double[] b5 = {855, 265, 138, 35};

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
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(Color.GREEN);
            StdDraw.text(512, 360, "Victoire !");
        }else{
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(Color.RED);
            StdDraw.text(512, 360, "Perdu !");
        }
        StdDraw.show();
    }
    private boolean isGameRunning(){
        return running;
    }
    
    private void init(){
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
        enemies = w.createEnemies("resources/waves/waveMinion.wve", map.getCaseS().getCoord().clone());
        enemiesPatern = map.getEnnemyPatern();
    }
    
    private void update(double deltaTimeSec){
        totaltime += deltaTimeSec;
        if (player.isAlive()){
            if (StdDraw.isMousePressed()) {
                double[] coordMouse = {StdDraw.mouseX(), StdDraw.mouseY()};
                map.updateClick(coordMouse);
                if (map.getClikedCase()!=null){// vérifier si le joueur a cliqué sur un bouton du shop
                    if ((coordMouse[0] > b1[0]-b1[2])&&(coordMouse[0] < b1[0]+b1[2])&&
                    (coordMouse[1] > b1[1]-b1[3])&&(coordMouse[1] < b1[1]+b1[3])){//bouton 1 TArcher
                        if((player.getGold() >= 20)){
                            tours.add(new TArcher(map.getClikedCase().getCoord()));
                            player.setGold(player.getGold()-20);
                            map.updateHpGold(player.getHp(), player.getGold());
                            map.setClikedCaseNull();
                        }

                    } else if ((coordMouse[0] > b2[0]-b2[2])&&(coordMouse[0] < b2[0]+b2[2])&&
                    (coordMouse[1] > b2[1]-b2[3])&&(coordMouse[1] < b2[1]+b2[3])){//bouton 2 TEarthCaster
                        if(player.getGold() >= 100){
                            tours.add(new TEarthCaster(map.getClikedCase().getCoord()));
                            player.setGold(player.getGold()-100);
                            map.updateHpGold(player.getHp(), player.getGold());
                            map.setClikedCaseNull();
                        }

                    } else if ((coordMouse[0] > b3[0]-b3[2])&&(coordMouse[0] < b3[0]+b3[2])&&
                    (coordMouse[1] > b3[1]-b3[3])&&(coordMouse[1] < b3[1]+b3[3])){//bouton 3 TWaterCaster
                        if(player.getGold() >= 50){
                            tours.add(new TWaterCaster(map.getClikedCase().getCoord()));
                            player.setGold(player.getGold()-50);
                            map.updateHpGold(player.getHp(), player.getGold());
                            map.setClikedCaseNull();
                        }

                    } else if ((coordMouse[0] > b4[0]-b4[2])&&(coordMouse[0] < b4[0]+b4[2])&&
                    (coordMouse[1] > b4[1]-b4[3])&&(coordMouse[1] < b4[1]+b4[3])){//bouton 4 TFireCaster
                        if(player.getGold() >= 100){
                            tours.add(new TFireCaster(map.getClikedCase().getCoord()));
                            player.setGold(player.getGold()-100);
                            map.updateHpGold(player.getHp(), player.getGold());
                            map.setClikedCaseNull();
                        }

                    } else if ((coordMouse[0] > b5[0]-b5[2])&&(coordMouse[0] < b5[0]+b5[2])&&
                    (coordMouse[1] > b5[1]-b5[3])&&(coordMouse[1] < b5[1]+b5[3])){//bouton 5 TWindCaster
                        if(player.getGold() >= 50){
                            tours.add(new TWindCaster(map.getClikedCase().getCoord()));
                            player.setGold(player.getGold()-50);
                            map.updateHpGold(player.getHp(), player.getGold());
                            map.setClikedCaseNull();
                        }
                    }
                }
            }
            map.drawCases();
            for (Tour t : tours) t.draw();

            List<Enemy> enemToRemove = new ArrayList<>();
            List<Tour> tourToRemove = new ArrayList<>();
            combats.fight(totaltime);
            for (Tour t : tours){
                if (t.getHp() <= 0) tourToRemove.add(t);
            }
            for (Enemy e : enemies){
                if (totaltime >= e.getSpawn()) {
                    int i = e.get_i();
                    if ((Arrays.equals(e.getCoord(), enemiesPatern.get(i))) && i < enemiesPatern.size() - 1) {
                        e.increment_i(); // Mise à jour de l'indice de la case à atteindre
                    }
                    if (Arrays.equals(e.getCoord(), enemiesPatern.get(i)) && i == enemiesPatern.size() - 1) {
                        player.setHp(player.getHp() - (int)e.getAtk());
                        map.updateHpGold(player.getHp(), player.getGold());
                        enemToRemove.add(e);
                    }
                    if (e.alive()){
                        e.moveTo(enemiesPatern.get(i));
                        e.draw();
                    } else{
                        enemToRemove.add(e);
                        player.setGold(player.getGold()+e.getReward());
                    }
                    if (e.isShow() && !spawnedEnemies.contains(e)){
                        spawnedEnemies.add(e);
                    }
                }
            }
            if (!spawnedEnemies.isEmpty()){
                List<Enemy> enemToRemove2 = new ArrayList<>();
                for (Enemy e : spawnedEnemies){
                    if (!e.isShow()) enemToRemove2.add(e);
                }
                for (Enemy e : enemToRemove2) spawnedEnemies.remove(e);
            }
            // faire disparaitre tout les ennemis qui sont mort ou qui ont atteind la case B
            // faire disparaitre toutes les tours qui sont morte
            if (!enemToRemove.isEmpty()) {
                for (Enemy e : enemToRemove) {
                    enemies.remove(e);
                    e.setShow(false);
                }
            }
            if (!tourToRemove.isEmpty()) for (Tour t : tourToRemove) tours.remove(t);
        }
        else running = false;
        map.updateHpGold(player.getHp(), player.getGold());
        StdDraw.show();
    } 
}