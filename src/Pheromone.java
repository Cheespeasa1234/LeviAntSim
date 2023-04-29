import java.awt.Color;

public class Pheromone {
    //HOME,    
    //FOOD,
    //DANGER,
    //NO_MORE_FOOD;
    public static final int TO_HOME = 0;
    public static final int TO_FOOD = 1;
    public static final int TO_DANGER = 2;
    public static final int NO_MORE_FOOD = 3;
    public static final Color[] PHEROMONE_COLORS = {
        Color.BLUE,
        Color.GREEN,
        Color.RED,
        Color.MAGENTA
    };
    public int type;

    public float age;
    public int x, y;

    public Pheromone(int x, int y, int type) {
        this.age = 1;
        this.x = x;
        this.y = y;
        this.type = type;
    }
}
