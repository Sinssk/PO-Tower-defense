import java.util.List;

public class Fight {

    // Feu < Eau < Air < Terre < Feu < ...
    public void fight(double deltatime){
        for (Tour t : Game.tours){
            if (deltatime > t.getLastAttack() + t.getAtkSpeed()){
                List<Enemy> cibles = t.cible();

                if (!cibles.isEmpty()) {
                    for (Enemy e : cibles) tourAttaque(t, e, deltatime);
                }
            }
        }
        
        for (Enemy e : Game.spawnedEnemies){
            if (deltatime > e.getLastAttack() + e.getAtkSpeed()){
                List<Tour> cibles = e.cible();
                if (!cibles.isEmpty()) {

                    for (Tour t : cibles) enemyAttaque(e, t, deltatime);
                }
            }
        }
    }

    private void tourAttaque(Tour t, Enemy e, double deltatime){
        
        double multipli = 1;
        switch (e.getElement()) {
            case FIRE -> {
                switch (t.getElement()) {
                    case EARTH -> multipli = 0.5;
                    case WATER -> multipli = 1.5;
                }
            }
            case WATER -> {
                switch (t.getElement()) {
                    case FIRE -> multipli = 0.5;
                    case WIND -> multipli = 1.5;
                }
            }
            case WIND -> {
                switch (t.getElement()) {
                    case WATER -> multipli = 0.5;
                    case EARTH -> multipli = 1.5;
                }
            }
            case EARTH -> {
                switch (t.getElement()) {
                    case WIND -> multipli = 0.5;
                    case FIRE -> multipli = 1.5;
                }
            }
        }
        e.setHp(e.getHp()-t.getAtk()*multipli);
        t.setLastAttack(deltatime);
    }

    private void enemyAttaque(Enemy e, Tour t, double deltatime){
        double multipli = 1;
        switch (t.getElement()) {
            case FIRE -> {
                switch (e.getElement()) {
                    case EARTH -> multipli = 0.5;
                    case WATER -> multipli = 1.5;
                }
            }
            case WATER -> {
                switch (e.getElement()) {
                    case FIRE -> multipli = 0.5;
                    case WIND -> multipli = 1.5;
                }
            }
            case WIND -> {
                switch (e.getElement()) {
                    case WATER -> multipli = 0.5;
                    case EARTH -> multipli = 1.5;
                }
            }
            case EARTH -> {
                switch (e.getElement()) {
                    case WIND -> multipli = 0.5;
                    case FIRE -> multipli = 1.5;
                }
            }
        }
        t.setHp(t.getHp()-e.getAtk()*multipli);
        e.setLastAttack(deltatime);
    }
}
