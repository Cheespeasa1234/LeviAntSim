import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;
import java.awt.Point;
public class Boid {

    public double x, y; // x and y coordinates of the ant
    public int w, h; // width and height of the ant's bounding box
    public double rot; // rotation angle of the ant
    public double speed;
    public List<Boid> cansee;

    public static  int VIEWDISTANCE = 50;
    public static final int SPEED = 5;
    public static double ALIGNMENTFORCE = 0.05;
    public static double SEPARATIONFORCE = 0.1;
    public static double COMMUNISM = 0.5;
    public static double COHESIONFORCE = 0.1;

    public Point centerOfMass = new Point(0, 0);

    public Boid(int x, int y) {
        w = 8;
        h = 8;
        this.x = x;
        this.y = y;
        this.rot = Math.toRadians(Game.rand.nextInt(0, 360));
        this.cansee = new ArrayList<Boid>();
        this.speed = SPEED;
    }

    public void move() {

        // find dx and dy to move
        this.rot %= (Math.PI * 2) + (Math.random() - 0.5) * 2;
        // get the angle in quarter 1
        double o = (speed * Math.sin(rot));
        double a = (speed * Math.cos(rot));

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

        this.speed = SPEED;
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
            double angle = Math.atan2(yDist, xDist);

            // If the boid is behind me
            if (this.rot - angle > Math.PI * 2) {
                continue;
            }

            double dist = Math.sqrt(
                    Math.pow(xDist, 2) +
                            Math.pow(yDist, 2));

            // Check if the other Boid is within the view distance of the current Boid
            if (dist <= VIEWDISTANCE)
                this.cansee.add(b);

        }
    }

    public void ruleSeparation() {

        // Initialize the total turn amount
        double totalTurn = 0;

        // Loop through each visible boid
        for (Boid boid : this.cansee) {

            // Calculate the distance and angle between the current boid and the visible
            // boid
            double xDist = boid.x - this.x;
            double yDist = boid.y - this.y;
            double dist = Math.sqrt(xDist * xDist + yDist * yDist);
            double angle = this.rot - Math.atan2(yDist, xDist);

            // Calculate a normalized "urgency" value based on the distance to the other
            // boid
            double urgency = 1 - dist / VIEWDISTANCE;

            // Calculate a "delta" value based on the angle to the other boid
            double delta = angle / Math.abs(angle);

            // Multiply delta by urgency and a static constant, and add to total turn
            totalTurn += delta * urgency * SEPARATIONFORCE;

            // If the distance to the other boid is less than 10, move halfway towards it to
            // avoid collisions
            if (dist < 10) {
                this.x -= xDist / 2;
                this.y -= yDist / 2;
            }
        }

        // Update the rotation of the boid based on the total turn divided by the number
        // of visible boids
        this.rot += totalTurn / this.cansee.size();
    }

    public void ruleAlignment() {
        double totalTurn = 0;
        for (Boid boid : this.cansee) {
            // take average of directions
            totalTurn += this.rot - boid.rot;
        }
        if (this.cansee.size() > 0) {
            double aveRot = totalTurn / (double) this.cansee.size();
            double deltaRot = aveRot / Math.abs(aveRot);
            this.rot -= deltaRot * ALIGNMENTFORCE;
        }
    }

    public void ruleCohesion() {

        // get center of mass
        double totalX = this.x, totalY = this.y;
        for (Boid boid : this.cansee) {
            totalX += boid.x;
            totalY += boid.y;
        }
        // average the locations
        double centerX = totalX / (this.cansee.size() + 1);
        double centerY = totalY / (this.cansee.size() + 1);
        this.centerOfMass = new Point((int) centerX,(int) centerY);

        // get the angle to the center of mass
        double angle = Math.atan2(centerY - this.y, centerX - this.x);
        double viewAngle = angle - this.rot;
        
        // if right ahead of me, slow down
        if(Math.ab
        s(viewAngle) < Math.PI * 0.75)
            this.speed = SPEED - 5;
        
        if(Math.abs(viewAngle) > Math.PI * 1.25 && Math.abs(viewAngle) < Math.PI * 1.5)
            this.speed = SPEED + 5;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (this.x), (int) (this.y), this.w, this.h);
    }

    @Override
    public String toString() {
        return "Boid[x:" + this.x + " y:" + this.y + " rot:" + rot + " canseelen:" + cansee.size() + "]";
    }
}
