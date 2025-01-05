import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Wave {

    public List<Enemy> createEnemies(String waveName, double[] c){
        List<Enemy> ens = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(waveName))) {
            String enemy;
            while ((enemy = reader.readLine())!=null){
                String[] timing = enemy.split("\\|");
                if (enemy.contains("Minion")){
                    ens.add(new EMinion(c.clone(), Double.parseDouble(timing[0])));
                }else if (enemy.contains("Wind Grognard")){
                    ens.add(new EWindGronard(c.clone(), Double.parseDouble(timing[0])));
                }else if (enemy.contains("Fire Grognard")){
                    ens.add(new EFireGrognard(c.clone(), Double.parseDouble(timing[0])));
                }else if (enemy.contains("Water Brute")){
                    ens.add(new EWaterBrute(c.clone(), Double.parseDouble(timing[0])));
                }else if (enemy.contains("Earth Brute")){
                    ens.add(new EEarthBrute(c.clone(), Double.parseDouble(timing[0])));
                }else if (enemy.contains("Boss")){
                    ens.add(new EBoss(c.clone(), Double.parseDouble(timing[0])));
                }else{
                    // UnknownEnemyExecption;
                }
            }
        }catch (IOException e) {
            System.out.println(e);
        }
        return ens;
    }
}