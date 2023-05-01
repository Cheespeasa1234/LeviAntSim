import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;

public class Boid {

    public double x, y; // x and y coordinates of the ant
    public int w, h; // width and height of the ant's bounding box
    public double rot; // rotation angle of the ant
    public double angleVis;

    public static int viewdist = 100;
    public static double laziness = 5;
    public static double speed = 5;

    public Boid(int x, int y) {
        w = 8;
        h = 8;
        this.x = x;
        this.y = y;
        this.rot = Math.toRadians(Game.rand.nextInt(0,360));
    }

    public void move() {

        // find dx and dy to move
        double h = speed; // move 2 pixels
        this.rot %= (Math.PI * 2);
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
        // this.rays.clear();
        // this.rays.add(new VisualRay(this.x + (o * viewdist / 2), this.y + (a * viewdist / 2), 1));
    }
    
    public void ruleAvoidance(List<Boid> boids) {

        // Initialize variables for calculating the total change and nearby Boid count
        double changeTotal = 0;
        int nearbyCount = 0;

        // Iterate through each Boid in the input list
        for (Boid b : boids) {
            // Skip the current Boid
            if (b == this)
                continue;

            // Calculate the distance between the current Boid and the other Boid
            double xDist = b.x - this.x;
            double yDist = b.y - this.y;
            double dist = Math.sqrt(
                    Math.pow(xDist, 2) +
                            Math.pow(yDist, 2));

            // Check if the other Boid is within the view distance of the current Boid
            if (dist <= viewdist) {
                // Calculate the angle to the other Boid
                double angle = Math.atan2(yDist, xDist);
                this.angleVis = angle;

                // Calculate the direction to turn to avoid the other Boid
                double headturn = Math.abs(angle - rot);
                if (headturn > Math.PI)
                    continue;
                double delta = -angle / Math.abs(angle);

                // Calculate the urgency of the turn based on the distance and laziness of the
                // current Boid
                double urgency = (1 - dist / viewdist) / laziness;

                // Add the change in direction to the total change and increment the nearby Boid
                // count
                changeTotal += delta * urgency;
                nearbyCount++;
            }
        }

        // If there are nearby Boids, calculate the average change needed and apply it
        // to the direction of the current Boid
        if (nearbyCount > 0) {
            double ave = changeTotal / (double) nearbyCount;
            this.rot += ave;
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
