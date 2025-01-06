/**
 * La classe Player représente un joueur dans le jeu.
 * Elle contient des informations sur les points de vie et l'or du joueur,
 * ainsi que des méthodes pour accéder et modifier ces informations.
 */
public class Player {
    private int hp; // points de vie du joueur
    private int gold; // or du joueur

    /**
     * Construit un joueur avec des points de vie et de l'or par défaut.
     */
    public Player() {
        this.hp = 100;
        this.gold = 50;
    }

    /**
     * Vérifie si le joueur est en vie.
     * 
     * @return true si le joueur a des points de vie restants, sinon false
     */
    public boolean isAlive() {
        return hp > 0;
    }

    /**
     * Retourne les points de vie du joueur.
     * 
     * @return les points de vie du joueur
     */
    public int getHp() {
        return hp;
    }

    /**
     * Retourne l'or du joueur.
     * 
     * @return l'or du joueur
     */
    public int getGold() {
        return gold;
    }

    /**
     * Définit les points de vie du joueur.
     * 
     * @param hp les nouveaux points de vie du joueur
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Définit l'or du joueur.
     * 
     * @param gold le nouvel or du joueur
     */
    public void setGold(int gold) {
        this.gold = gold;
    }
}