import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

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

    public Colony colony = new Colony(Color.BLUE, 100, PREF_W / 2, PREF_H / 2);
    public List<Chunk> chunks = new ArrayList<Chunk>();
    public Random rand = new Random();
    
    private Timer t = new Timer(1000 / 60, e->{
        for(Ant a : colony.citizens) {
            a.move();
            repaint();
        }
    });

    private Timer pheromoneTick = new Timer(1000 / 10, e->{
        // place a pheromone TO_HOME
        for(Ant a : colony.citizens) {
            a.dropPheromone(0);
        }
        // age each pheromone by one
        for(Chunk c : chunks) for(int i = 0; i < c.pheromones.size(); i++) {
            Pheromone p = c.pheromones.get(i);
            p.age -= 0.01f;
            if(p.age == 0) // 5s
                c.pheromones.remove(p);
        }
    });
    
    public Game() {
        this.setFocusable(true);
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        t.start();
        pheromoneTick.start();
        int w = 160;
        int h = 160;
        for(int x = 0; x < PREF_W; x += w) {
            for(int y = 0; y < PREF_H; y += h) {
                Chunk c = new Chunk(x,y,w,h);
                for(int i = 0; i < 10; i++) {
                    int pX = rand.nextInt(x, x+w);
                    int pY = rand.nextInt(y, y+h);
                    int type = rand.nextInt(0, 4);
                    c.pheromones.add(new Pheromone(pX, pY, type));
                }
                chunks.add(c);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(colony.color);
        for(Ant a : colony.citizens) {
            if(a.adventuring) {
                g2.rotate(a.rot + (Math.PI), a.x + a.w / 2, a.y + a.h / 2);
                g2.fill(a.getBounds());
                g2.rotate(-a.rot - (Math.PI), a.x + a.w / 2, a.y + a.h / 2);
            }
        }

        for(Chunk chunk : chunks) {
            for(Pheromone p : chunk.pheromones) {
                g2.setColor(Pheromone.PHEROMONE_COLORS[p.type]);
                int size = (int) (p.age * 10);
                g2.fillOval(p.x - size / 2, p.y - size / 2, size, size);
            }
        }

        g2.setColor(new Color(colony.color.getRed(), colony.color.getGreen(), colony.color.getBlue(), 100));
        g2.fillRect(colony.x - 20, colony.y - 20, 20, 20);
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
