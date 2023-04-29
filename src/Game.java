import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Point;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Game extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    public static final int PREF_W = 800;
    public static final int PREF_H = 800;

    public static Random rand;
    public List<Boid> boids;

    private Timer t = new Timer(1000/60, e->{
        for(Boid boid : boids) {
            boid.move();
            boid.ruleAvoidance(boids);
            System.out.println(boid.toString());
            repaint();
        }
    });
    
    public Game() {
        this.setFocusable(true);
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);

        boids = new ArrayList<Boid>();
        rand = new Random();
        for(int i = 0; i < 100; i++) {
            boids.add(new Boid(rand.nextInt(100, 200), rand.nextInt(100,200)));
        }
        t.start();
    }

    public boolean first = true;
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        for(int i = 0; i < boids.size(); i++) {
            Boid boid = boids.get(i);
            g2.rotate(boid.rot + (Math.PI), boid.x + boid.w / 2, boid.y + boid.h / 2);
            g2.fill(boid.getBounds());
            g2.rotate(-boid.rot - (Math.PI), boid.x + boid.w / 2, boid.y + boid.h / 2);
            // g2.drawString(boid.rot+"", (int) boid.x, (int) boid.y);
            if(!first && i == 0) {
                for(VisualRay ray : boid.rays) {
                    double s = (255 * ray.strength);
                    g2.setColor(new Color(0, 0, 0, (int) s));
                    System.out.println(s);
                    g2.drawLine((int) boid.x + boid.w / 2, (int) boid.y + boid.h / 2, ray.x, ray.y);
                }
                g2.setColor(Color.BLACK);
            }
        }
        first = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void mouseDragged(MouseEvent e) {
    }
    @Override
    public void mouseMoved(MouseEvent e) {
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
    /* METHODS FOR CREATING JFRAME AND JPANEL */

    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

    public static Game game;
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("You're Mother");
        game = new Game();
        JPanel gamePanel = game;

        frame.getContentPane().add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
