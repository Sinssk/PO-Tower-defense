public class Player {
    private int hp;
    private int gold;

    public Player(){
        this.hp = 100;
        this.gold = 50;
    }

    public boolean isAlive(){
        return hp > 0;
    }

    public int getHp() {
        return hp;
    }
    public int getGold() {
        return gold;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
   
}