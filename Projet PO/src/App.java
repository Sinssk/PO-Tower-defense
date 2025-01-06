/**
 * La classe App sert de point d'entrée pour l'application de jeu.
 * Elle initialise et lance le jeu.
 */
public class App {
    /**
     * La méthode main est le point d'entrée de l'application.
     * Elle crée une instance de la classe Game et lance le jeu.
     * 
     * @param args arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        Game g = new Game();
        g.launch();
    }
}