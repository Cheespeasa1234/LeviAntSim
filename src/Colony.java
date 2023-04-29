import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class Colony {
    public List<Ant> citizens;
    public Color color;
    public int x, y;

    public Colony(Color c, int antCount, int x, int y) {
        this.color = c;
        this.x = x;
        this.y = y;
        citizens = new ArrayList<Ant>();
        for(int i=0;i<antCount;i++) {
            citizens.add(new Ant(this));
        }
    }
}
