import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.awt.Rectangle;

public class Ant {
    public float x, y; 
    public int w, h;
    public double rot;
    public double creativity;
    public Colony home;
    public boolean adventuring;
    public static Random r = new Random();

    public Ant(Colony home) {
        this.home = home;
        w = 8;
        h = 8;
        this.x = home.x;
        this.y = home.y;
        this.adventuring = true;
        this.rot = r.nextFloat() * (float) Math.PI * 2.0f;
        this.creativity = Math.random();
    }

    public List<Chunk> inChunks() {
        List<Chunk> chunks = new ArrayList<Chunk>();
        // for every chunk:
        for(Chunk c : Game.game.chunks) {
            if(c.getBounds().intersects(this.getBounds()))
                chunks.add(c);
        }
        return chunks;
    }

    public void dropPheromone(int type) {
        Chunk chunk = this.inChunks().get(0);
        chunk.pheromones.add(new Pheromone((int) this.x, (int) this.y, type));
    }

    public void move() {
        // find pheromones in my chunk
        List<Chunk> chunks = this.inChunks();
        
        // find the strongest one
        double strongestValue = Integer.MAX_VALUE;
        Pheromone strongestObj = null;
        for(Chunk c : chunks) for(Pheromone p : c.pheromones) {
            double strength = Math.sqrt(Math.pow(this.x-p.x, 2) + Math.pow(this.y-p.y, 2));
            if(strength < strongestValue) {
                strongestValue = strength;
                strongestObj = p;
            }
        }

        // get the angle to that one
        /*
                . = this.y - p.y
                |   
                |  o
                |
        .-------- = this.x - p.x
            a
        */
        // move rotation
        double angle = Math.atan((this.y - strongestObj.y)/(this.x - strongestObj.x));
        if(strongestValue < 1000 && Math.random() < 0.9) 
            rot += (Math.random() - 0.5f) * creativity / 2.0f;
        else {
            rot = angle;
            rot += (Math.random() - 0.5f) * creativity / 2.0f;
        }
        // find dx and dy to move
        float h = 2.0f; // move 2 pixels
        float o, a;
        // h * sin(theta) = o
        o = (float) (h * Math.sin(rot));
        a = (float) (h * Math.cos(rot));
        // if out of bounds:
        if(this.x + o < 0 || this.x + o > Game.PREF_W) {
            o = -o;
            rot += Math.PI;
        }
        if(this.y + a < 0 || this.y + a > Game.PREF_H) {
            a = -a;
            rot += Math.PI;
        }
        this.x += o;
        this.y += a;
    }
    public Rectangle getBounds() {
        return new Rectangle((int) (this.x), (int) (this.y), this.w, this.h);
    }
}
