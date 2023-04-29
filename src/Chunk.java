import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.awt.Rectangle;

public class Chunk {
    public List<Pheromone> pheromones;
    public List<Food> foods;
    public int x, y, w, h;
    public Chunk(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.pheromones = new ArrayList<Pheromone>();
    }
    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.w, this.h);
    }
}
