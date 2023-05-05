import java.awt.BasicStroke;
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
import java.util.function.Consumer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JSlider;

public class Game extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    public static final int PREF_W = 800;
    public static final int PREF_H = 800;

    public static Random rand;
    public List<Boid> boids;

    private boolean doSep = true, doAli = true, doCoh = true;
    private int displayRadius = 100;

    private Timer t = new Timer(1000 / 20, e -> {
        for (Boid boid : boids) {
            boid.loadVisible(boids);
            if (boid.cansee.size() > 0) {
                if (doSep)
                    boid.ruleSeparation();
                if (doAli)
                    boid.ruleAlignment();
                if (doCoh)
                    boid.ruleCohesion();
            }
            boid.move();
            repaint();
        }
    });

    public Game() {
        this.setFocusable(true);
        this.setBackground(Color.WHITE);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);

        JSlider VIEWDISTANCESlider = new JSlider();
        JLabel VIEWDISTANCELabel = new JLabel("0.5");
        VIEWDISTANCESlider.setMaximum(500);
        VIEWDISTANCESlider.addMouseMotionListener(
                new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        Boid.VIEWDISTANCE = VIEWDISTANCESlider.getValue();
                        VIEWDISTANCELabel.setText(Boid.VIEWDISTANCE + "");
                        displayRadius = 1000;
                    }
                });

        JCheckBox doSepBox = new JCheckBox();
        JLabel doSepLabel = new JLabel("sep: " + doSep);
        doSepBox.addChangeListener(e -> {
            doSep = doSepBox.isSelected();
            doSepLabel.setText("sep: " + doSep);
        });

        JCheckBox doAliBox = new JCheckBox();
        JLabel doAliLabel = new JLabel("ali: " + doAli);
        doAliBox.addChangeListener(e -> {
            doAli = doAliBox.isSelected();
            doAliLabel.setText("ali: " + doAli);
        });

        JCheckBox doCohBox = new JCheckBox();
        JLabel doCohLabel = new JLabel("coh: " + doCoh);
        doCohBox.addChangeListener(e -> {
            doCoh = doCohBox.isSelected();
            doCohLabel.setText("coh: " + doCoh);
        });

        this.add(VIEWDISTANCESlider);
        this.add(VIEWDISTANCELabel);

        this.add(doSepBox);
        this.add(doSepLabel);
        this.add(doAliBox);
        this.add(doAliLabel);
        this.add(doCohBox);
        this.add(doCohLabel);

        boids = new ArrayList<Boid>();
        rand = new Random();
        for (int i = 0; i < 50; i++) {
            boids.add(new Boid(rand.nextInt(100, 200), rand.nextInt(100, 200)));
        }
        t.start();
    }

    public boolean first = true;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < boids.size(); i++) {
            Boid boid = boids.get(i);

            g2.rotate(-boid.rot, boid.x + boid.w / 2, boid.y + boid.h / 2);
            // g2.fill(boid.getBounds());
            g2.setColor(Color.BLACK);
            g2.fillPolygon(Geometry.transformArray(Geometry.triangleXValues, (int) boid.x),
                    Geometry.transformArray(Geometry.triangleYValues, (int) boid.y), 3);

            g2.rotate(boid.rot, boid.x + boid.w / 2, boid.y + boid.h / 2);
            g2.setColor(Color.GREEN);
            g2.fillOval((int) boid.centerOfMass.getX() - 2, (int) boid.centerOfMass.getY() - 2, 4, 4);
            // for(Boid visible : boid.cansee) {
            // g2.drawLine((int)boid.x, (int)boid.y, (int)visible.x, (int)visible.y);
            // }
            if (displayRadius > 0) {
                displayRadius--;
                g2.setColor(Color.RED);
                g2.drawOval((int)(boid.x - Boid.VIEWDISTANCE), (int)(boid.y
                        - Boid.VIEWDISTANCE), Boid.VIEWDISTANCE * 2, Boid.VIEWDISTANCE * 2);
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
