import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.awt.Rectangle;
import java.awt.Point;

public class Boid {

    public double x, y; // x and y coordinates of the ant
    public int w, h; // width and height of the ant's bounding box
    public double rot; // rotation angle of the ant
    public List<Point> rays;

    public static int viewdist = 100;

    public Boid(int x, int y) {
        w = 8;
        h = 8;
        this.x = x;
        this.y = y;
        this.rot = Math.toRadians(Game.rand.nextInt(0,360));
        this.rays = new ArrayList<Point>();
        
    }

    public void move() {

        
        
        // find dx and dy to move
        double h = 2.0; // move 2 pixels
        // get the angle in quarter 1
        double o = (h * Math.sin(rot));
        double a = (h * Math.cos(rot));

        // if out of bounds, move to the other side
        if(this.x + o < 0) {
            this.x = Game.PREF_W - 1;
        } else if(this.x + o > Game.PREF_W) {
            this.x = 1;
        }
        if(this.y + a < 0) {
            this.y = Game.PREF_H - 1;
        } else if(this.y + a > Game.PREF_H) {
            this.y = 1;
        }
        this.x += o;
        this.y += a;
    }
    
    public void ruleAvoidance(List<Boid> boids) {
        // get all boids within my viewdist
        this.rays.clear();
        // for every boid:
        for(Boid b : boids) {
            if(b == this) continue;
            // get the distance
            double xDist = b.x - this.x;
            double yDist = b.y - this.y;
            double dist = Math.sqrt(
                Math.pow(xDist, 2) +
                Math.pow(yDist, 2)
            );
            
            if(dist <= viewdist) {

                // get the other angle
                double angle = Math.atan2(yDist, xDist);
                this.rays.add(new Point((int)(this.x + xDist), (int)(this.y + yDist)));

            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (this.x), (int) (this.y), this.w, this.h);
    }
    @Override
    public String toString() {
        return "Boid[x:" + this.x + " y:" + this.y + " rot:" + rot + "]"; 
    }
}
