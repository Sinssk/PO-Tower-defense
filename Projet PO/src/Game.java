import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe principale du jeu de défense de tour.
 */
public class Game{
    private double deltaTimeSec;
    private long previousTime;
    private long currentTime;
    private Map<String, List<String>> mapsPaterns = new HashMap<>();
    //mapsPatern --> key : lvl
    //             value : patern map + waves
    private Map<String, List<String>> waves = new HashMap<>();
    //waves      --> key : wave name
    //             value : ordre spawn enemi(s)
    private final MapGame map = new MapGame();
    public static final Player player = new Player();
    private final Wave w = new Wave();
    private final Fight combats = new Fight();
    private List<double[]> enemiesPatern;

    private static List<Enemy> enemies = new ArrayList<>();
    public static List<Tour> tours = new ArrayList<>();
    public static List<Enemy> spawnedEnemies = new ArrayList<>();

    private int currentLvl = 1;
    private final int maxLvl = 3;
    private int currentWve = 1;
    private int maxWave = 1;
    private boolean running = true;
    private double totaltime = 0;

    public static final double[] b1 = {855, 570, 138, 31};// TArcher
    public static final double[] b2 = {855, 503, 138, 31};// TEarthCaster
    public static final double[] b3 = {855, 436, 138, 31};// TWaterCaster
    public static final double[] b4 = {855, 369, 138, 31};// TFireCaster
    public static final double[] b5 = {855, 302, 138, 31};// TWindCaster

    public static final double[] b6 = {855, 235, 138, 31};// IceCaster
    public static final double[] b7 = {855, 168, 138, 31};// PosionCaster
    public static final double[] b8 = {855, 101, 138, 31};// GoldDigger
    public static final double[] b9 = {855, 34, 138, 31};// Railgun

    /**
     * Lance le jeu.
     */
    public void launch (){
        init();
        previousTime = System.currentTimeMillis();
        while(isGameRunning()){
            currentTime = System.currentTimeMillis();
            deltaTimeSec = (double)(currentTime - previousTime)/1000;

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

     /**
     * Initialise le jeu en chargeant les patterns de cartes et les vagues d'ennemis.
     */
    private boolean isGameRunning(){
        return running;
    }
    
     /**
     * Vérifie si le jeu est en cours d'exécution.
     * 
     * @return true si le jeu est en cours d'exécution, false sinon.
     */
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
        map.init();
        map.loadMap(mapsPaterns.get("level1").get(0));
        map.drawMap();
        map.updateLvl(currentLvl, maxLvl, currentWve, maxWave);
        map.drawStore();
        map.updateHpGold(player.getHp(), player.getGold());
        enemiesPatern = map.getEnnemyPatern();
        enemies = w.createEnemies("resources/waves/"+mapsPaterns.get("level1").get(currentWve)+".wve", map.getCaseS().getCoord().clone());
    }
    
    /**
     * Met à jour l'état du jeu.
     * 
     * @param deltaTimeSec Le temps écoulé depuis la dernière mise à jour, en secondes.
     */
    private void update(double deltaTimeSec){
        totaltime += deltaTimeSec;
        if (player.isAlive()){
            if (enemies.isEmpty()){
                spawnedEnemies.clear();
                if (currentWve < maxWave){
                    currentWve += 1;
                    map.updateLvl(currentLvl, maxLvl, currentWve, maxWave);
                    enemies = w.createEnemies("resources/waves/"+mapsPaterns.get("level" + currentLvl).get(currentWve)+".wve", map.getCaseS().getCoord().clone());
                } else{
                    if (currentLvl < maxLvl){
                        currentLvl += 1;
                        currentWve = 1;
                        if (currentLvl == 2) player.setGold(player.getGold()+200);//prime d'équilibrage
                        if (currentLvl == 3) player.setGold(player.getGold()+300);


                        String level = "level" + currentLvl;

                        map.loadMap(mapsPaterns.get(level).get(0));
                        map.drawMap();
                        map.updateLvl(currentLvl, mapsPaterns.size(), currentWve, mapsPaterns.get(level).size() - 1);
                        map.updateLvl(currentLvl, maxLvl, currentWve, maxWave);
                        map.drawStore();
                        map.updateHpGold(player.getHp(), player.getGold());
                        maxWave = mapsPaterns.get(level).size() - 1;
                        enemiesPatern = map.getEnnemyPatern();
                        tours.clear();
                        enemies = w.createEnemies("resources/waves/" + mapsPaterns.get("level" + currentLvl).get(currentWve) + ".wve", map.getCaseS().getCoord().clone());
                    } else{
                        running = false;
                    }
                }
                totaltime = 0;

            } else {
                map.drawCases();
                if (StdDraw.isMousePressed()) {
                    double[] coordMouse = {StdDraw.mouseX(), StdDraw.mouseY()};
                    map.updateClick(coordMouse);
                    if (map.getClikedCase()!=null){// vérifier si le joueur a cliqué sur un bouton du shop
                        Boolean caseVide = true;
                        for (Tour t : tours){//vérifer qu'il y a pas déjà une tour sur la case cliqué
                            if(t.getCoord() == map.getClikedCase().getCoord()) {
                                caseVide = false;
                                map.setClikedCaseNull();
                                //Exception
                                break;
                            }
                        }//on vérifie sur quel bouton on clique
                        if (caseVide){
                            if ((coordMouse[0] > b1[0]-b1[2])&&(coordMouse[0] < b1[0]+b1[2])&&
                            (coordMouse[1] > b1[1]-b1[3])&&(coordMouse[1] < b1[1]+b1[3])){//bouton 1 TArcher
                                if((player.getGold() >= 20)){
                                    tours.add(new TArcher(map.getClikedCase().getCoord()));
                                    player.setGold(player.getGold()-20);
                                    map.updateHpGold(player.getHp(), player.getGold());
                                    map.setClikedCaseNull();
                                }

                            } else if ((coordMouse[0] > b2[0]-b2[2])&&(coordMouse[0] < b2[0]+b2[2])&&
                            (coordMouse[1] > b2[1]-b2[3])&&(coordMouse[1] < b2[1]+b2[3])){//bouton 2 TWindCaster 
                                if(player.getGold() >= 50){
                                    tours.add(new TWindCaster(map.getClikedCase().getCoord()));
                                    player.setGold(player.getGold()-50);
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
                            (coordMouse[1] > b5[1]-b5[3])&&(coordMouse[1] < b5[1]+b5[3])){//bouton 5 TEarthCaster
                                if(player.getGold() >= 100){
                                    tours.add(new TEarthCaster(map.getClikedCase().getCoord()));
                                    player.setGold(player.getGold()-100);
                                    map.updateHpGold(player.getHp(), player.getGold());
                                    map.setClikedCaseNull();
                                }
                            } else if ((coordMouse[0] > b6[0]-b6[2])&&(coordMouse[0] < b6[0]+b6[2])&&
                            (coordMouse[1] > b6[1]-b6[3])&&(coordMouse[1] < b6[1]+b6[3])){//bouton 6 TIceCaster
                                if(player.getGold() >= 70){
                                    tours.add(new TIceCaster(map.getClikedCase().getCoord()));
                                    player.setGold(player.getGold()-70);
                                    map.updateHpGold(player.getHp(), player.getGold());
                                    map.setClikedCaseNull();
                                }
                            } else if ((coordMouse[0] > b7[0]-b7[2])&&(coordMouse[0] < b7[0]+b7[2])&&
                            (coordMouse[1] > b7[1]-b7[3])&&(coordMouse[1] < b7[1]+b7[3])){//bouton 7 TPoisonCaster
                                if(player.getGold() >= 80){
                                    tours.add(new TPoisonCaster(map.getClikedCase().getCoord()));
                                    player.setGold(player.getGold()-80);
                                    map.updateHpGold(player.getHp(), player.getGold());
                                    map.setClikedCaseNull();
                                }
                            } else if ((coordMouse[0] > b8[0]-b8[2])&&(coordMouse[0] < b8[0]+b8[2])&&
                            (coordMouse[1] > b8[1]-b8[3])&&(coordMouse[1] < b8[1]+b8[3])){//bouton 8 TGoldDigger
                                if(player.getGold() >= 20){
                                    tours.add(new TGoldDigger(map.getClikedCase().getCoord()));
                                    player.setGold(player.getGold()-20);
                                    map.updateHpGold(player.getHp(), player.getGold());
                                    map.setClikedCaseNull();
                                }
                            } else if ((coordMouse[0] > b9[0]-b9[2])&&(coordMouse[0] < b9[0]+b9[2])&&
                            (coordMouse[1] > b9[1]-b9[3])&&(coordMouse[1] < b9[1]+b9[3])){//bouton 9 TRailGun
                                if(player.getGold() >= 150){
                                    tours.add(new TRailGun(map.getClikedCase().getCoord()));
                                    player.setGold(player.getGold()-150);
                                    map.updateHpGold(player.getHp(), player.getGold());
                                    map.setClikedCaseNull();
                                }
                            }
                        }
                    }
                }
        
                for (Tour t : tours) t.draw();
                Enemy.updateAdvanced();
                List<Enemy> enemToRemove = new ArrayList<>();//aidé par chatgpt, corrigé les erreurs de supression d'élément dans une liste en train d'être lu par une boucle
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
                            enemToRemove.add(e);
                        }
                        if (e.alive()){
                            e.moveTo(enemiesPatern.get(i));
                            e.draw();
                        } else{
                            enemToRemove.add(e);
                            player.setGold(player.getGold()+e.getReward()*2);
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
                if (!enemToRemove.isEmpty()) {
                    for (Enemy e : enemToRemove) {
                        enemies.remove(e);
                        e.setShow(false);
                    }
                }
                // faire disparaitre toutes les tours qui sont morte
                if (!tourToRemove.isEmpty()) for (Tour t : tourToRemove) tours.remove(t);

                map.updateHpGold(player.getHp(), player.getGold());//mettre à jour les hp et gold du joueur
            }
        } else running = false; //le joueur est mort, la partie s'arrête
        
        StdDraw.show();
    }
}