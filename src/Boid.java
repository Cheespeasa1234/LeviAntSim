import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;

public class Boid {

    public double x, y; // x and y coordinates of the ant
    public int w, h; // width and height of the ant's bounding box
    public double rot; // rotation angle of the ant
    public List<Boid> cansee;

    public static int viewdist = 50;

    public static int speed = 2;
    public static double introvertedness = 0.1;

    public static double gullibility = 1.5;

    public Boid(int x, int y) {
        w = 8;
        h = 8;
        this.x = x;
        this.y = y;
        this.rot = Math.toRadians(Game.rand.nextInt(0, 360));
        this.cansee = new ArrayList<Boid>();
    }

    public void move() {

        // find dx and dy to move
        double h = speed; // move 2 pixels
        this.rot %= (Math.PI * 2);
        // get the angle in quarter 1
        double o = (h * Math.sin(rot));
        double a = (h * Math.cos(rot));

        // if out of bounds, move to the other side
        if (this.x + o < 0) {
            this.x = Game.PREF_W - 1;
        } else if (this.x + o > Game.PREF_W) {
            this.x = 1;
        }
        if (this.y + a < 0) {
            this.y = Game.PREF_H - 1;
        } else if (this.y + a > Game.PREF_H) {
            this.y = 1;
        }
        this.x += o;
        this.y += a;
    }

    public void loadVisible(List<Boid> boids) {

        this.cansee.clear();

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
            if (dist <= viewdist)
                this.cansee.add(b);
            
        }
    }

    public void ruleSeparation() {
        double totalTurn = 0;
        for(Boid boid: this.cansee) {
            // get distance and angle
            double xDist = boid.x - this.x;
            double yDist = boid.y - this.y;
            double dist = Math.sqrt(xDist * xDist + yDist * yDist);
            double angle = this.rot - Math.atan2(yDist, xDist);

            // normalized value
            double urgency = 1 - dist / viewdist;

            // get 1, 0, or -1
            double delta = angle / Math.abs(angle);

            // multiply it by the urgency and the static const
            totalTurn += delta * urgency * introvertedness;
        }
        // change rotation
        this.rot += this.cansee.size() > 0 ? (totalTurn / this.cansee.size()) : 0;
    }

    public void ruleAlignment() {
        double totalTurn = 0;
        for(Boid boid: this.cansee) {
            // take average of directions
            totalTurn += boid.rot;
        }
        if(this.cansee.size() > 0) {
            double aveRot = totalTurn / (double) this.cansee.size();
            double deltaRot = aveRot / Math.abs(aveRot);
            this.rot += deltaRot * gullibility;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (this.x), (int) (this.y), this.w, this.h);
    }

    @Override
    public String toString() {
        return "Boid[x:" + this.x + " y:" + this.y + " rot:" + rot + " canseelen:" + cansee.size() + "]";
    }
}
