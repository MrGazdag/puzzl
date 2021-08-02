package me.mrgazdag.programs.puzzl;

import me.mrgazdag.programs.puzzl.gui.DefaultPuzzleGUI;
import me.mrgazdag.programs.puzzl.gui.PuzzleGUI;
import me.mrgazdag.programs.puzzl.particles.ParticleManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class PuzzleFrame extends JFrame {
    private static final int PREFERRED_WIDTH = 800;
    private static final int PREFERRED_HEIGHT = 600;
    private PuzzleGUI gui;
    private final Timer timer;
    private final ParticleManager particleManager;

    private final JPanel contentPane;
    private boolean snow;
    private final Thread shutdownThread;
    public PuzzleFrame() throws IOException {
        this.contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics gr) {
                if (getGui() == null) return;
                Graphics2D g = (Graphics2D) gr;
                getGui().render(g);
                renderParticles(g);
            }
        };
        setContentPane(contentPane);
        this.particleManager = new ParticleManager(this);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screen.width/2)-(PREFERRED_WIDTH/2), (screen.height/2)-(PREFERRED_HEIGHT/2), PREFERRED_WIDTH, PREFERRED_HEIGHT);

        /*
        //Uncomment to start a fix image.

        //DEBUG start
        BufferedImage img = ImageIO.read(new File("imgs", "test_img1.png"));
        //BufferedImage img = ImageIO.read(new URL("https://www.wallpaperup.com/uploads/wallpapers/2012/12/14/24723/12f8a61b685b02866f66ab0e39fd23cd-700.jpg")); //snowy picture
        PuzzleContextBuilder builder = new PuzzleContextBuilder(img);
        builder.setType(PuzzleType.DEFAULT);
        //builder.setHorizontalCount(8);
        //builder.setVerticalCount(5);
        snow = false;

        builder.setHorizontalCount(3);
        builder.setVerticalCount(4);

        setGUI(new DefaultPuzzleGUI(this, builder.build()));

        //DEBUG end

        */
        nextImage();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (getGui() == null) return;
                if (snow) ((DefaultPuzzleGUI)getGui()).displaySnow();
                getGui().tickFade();
                tickParticles();
            }
        }, 100, 10);
        shutdownThread = new Thread(this::stop);
        Runtime.getRuntime().addShutdownHook(shutdownThread);
    }

    public int getContentPaneWidth() {
        return contentPane.getWidth();
    }
    public int getContentPaneHeight() {
        return contentPane.getHeight();
    }

    public void nextImage() {
        new Thread(() -> {
            System.out.println("Loading next image...");
            try {
                BufferedImage img = ImageIO.read(new URL("https://picsum.photos/1728/1080"));
                PuzzleContextBuilder builder = new PuzzleContextBuilder(img);
                builder.setType(PuzzleType.DEFAULT);
                builder.setHorizontalCount(8);
                builder.setVerticalCount(5);
                queueTask(new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("Got next image, applying...");

                        DefaultPuzzleGUI gui = new DefaultPuzzleGUI(PuzzleFrame.this, builder.build());
                        setGUI(gui);
                        gui.fadeIn(PuzzleFrame.this::repaint);
                        //snow = true;
                    }
                }, 1);

                //builder.setHorizontalCount(3);
                //builder.setVerticalCount(4);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void toggleSnow() {
        snow = !snow;
    }

    public void queueTask(TimerTask task, long delay) {
        this.timer.schedule(task, delay);
    }

    public void tickParticles() {
        particleManager.tick(gui);
    }

    public void stop() {
        if (Thread.currentThread() != shutdownThread) Runtime.getRuntime().removeShutdownHook(shutdownThread);
        setVisible(false);
        dispose();
        timer.cancel();
    }

    public PuzzleGUI getGui() {
        return gui;
    }

    public void setGUI(PuzzleGUI gui) {
        if (this.gui != null) {
            contentPane.removeKeyListener(this.gui);
            contentPane.removeMouseListener(this.gui);
            contentPane.removeMouseMotionListener(this.gui);
        }
        this.gui = gui;
        contentPane.addKeyListener(this.gui);
        contentPane.addMouseListener(this.gui);
        contentPane.addMouseMotionListener(this.gui);
        repaint();
    }

    public void renderParticles(Graphics2D g) {
        particleManager.render(gui, g);
    }

    public ParticleManager getParticleManager() {
        return particleManager;
    }
}
