package me.mrgazdag.programs.puzzl.gui;

import me.mrgazdag.programs.puzzl.FadeState;
import me.mrgazdag.programs.puzzl.PuzzleFrame;
import me.mrgazdag.programs.puzzl.games.defaultpuzzle.DefaultPuzzleContext;
import me.mrgazdag.programs.puzzl.games.defaultpuzzle.DefaultPuzzlePiece;
import me.mrgazdag.programs.puzzl.particles.ConfettiParticle;
import me.mrgazdag.programs.puzzl.particles.SnowParticle;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.TimerTask;

public class DefaultPuzzleGUI extends PuzzleGUI {
    private static final int PADDING = 50;

    private final DefaultPuzzleContext context;
    private int selectedIndex;

    private boolean clicking;
    private int clickedIndex;

    private int lastMouseX;
    private int lastMouseY;

    private boolean showGrid;
    private boolean showCompleteImage;

    public DefaultPuzzleGUI(PuzzleFrame gui, DefaultPuzzleContext context) {
        super(gui);
        this.context = context;
        this.selectedIndex = -1;
        this.clicking = false;
        this.clickedIndex = -1;
        this.showGrid = true;
        this.showCompleteImage = false;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        clicking = true;
        frame.repaint();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (clickedIndex > -1) {
            if (selectedIndex == -1) {
                selectedIndex = clickedIndex;
            } else {
                if (context.isCompleted()) return;
                context.swap(selectedIndex, clickedIndex);
                selectedIndex = -1;
                clickedIndex = -1;
                if (context.isCompleted()) {
                    displayConfetti();
                    frame.queueTask(new TimerTask() {
                        @Override
                        public void run() {
                            nextImage();
                        }
                    }, 2000);
                }
            }
        }
        clicking = false;
        frame.repaint();
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        lastMouseX = e.getX();
        lastMouseY = e.getY();
        frame.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        lastMouseX = e.getX();
        lastMouseY = e.getY();
        frame.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            displayConfetti();
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            displaySnow();
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            nextImage();
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            frame.toggleSnow();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            showCompleteImage = true;
            frame.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            showGrid = false;
            frame.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            showCompleteImage = false;
            frame.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            showGrid = true;
            frame.repaint();
        }
    }

    private void displayConfetti() {
        int width = getWidth();
        int height = getHeight();
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            int confettiStartX = 1+(r.nextInt(10)-5);
            int confettiStartY = height-1+(r.nextInt(10)-5);
            double confettiVelX =  15 + (r.nextInt(20) - 10)*r.nextDouble();
            double confettiVelY = -15 + (r.nextInt(20) - 10)*r.nextDouble();
            double confettiScale = 2 * (r.nextDouble() + 0.3);
            frame.getParticleManager().spawnParticle(new ConfettiParticle(confettiStartX, confettiStartY, confettiVelX, confettiVelY, confettiScale, 0));
        }

        for (int i = 0; i < 100; i++) {
            int confettiStartX = width-1+(r.nextInt(10)-5);
            int confettiStartY = height-1+(r.nextInt(10)-5);
            double confettiVelX = -15 + (r.nextInt(20) - 10)*r.nextDouble();
            double confettiVelY = -15 + (r.nextInt(20) - 10)*r.nextDouble();
            double confettiScale = 2 * (r.nextDouble() + 0.3);
            frame.getParticleManager().spawnParticle(new ConfettiParticle(confettiStartX, confettiStartY, confettiVelX, confettiVelY, confettiScale, 0.5f));
        }
    }
    public void displaySnow() {
        int width = getWidth();
        Random r = new Random();
        for (int i = 0; i < 1; i++) {
            double confettiStartX = r.nextDouble()*width;
            double confettiStartY =    -50 + (r.nextDouble()*50);
            double confettiWindX = 1 * r.nextDouble();
            double confettiScale = 1 * (r.nextDouble() + 0.3);
            frame.getParticleManager().spawnParticle(new SnowParticle(confettiStartX, confettiStartY, confettiWindX, confettiScale));
        }
    }

    public void nextImage() {
        fadeOut(frame::nextImage);
    }

    @Override
    public void render(Graphics2D g) {
        //background
        g.setColor(new Color(54, 65, 69));
        g.fillRect(0,0,getWidth(), getHeight());
        Stroke oldStroke = g.getStroke();

        BasicStroke thicc = new BasicStroke(3);
        g.setStroke(thicc);


        //scale img
        int imgWidth = context.getSource().getWidth();
        int imgHeight = context.getSource().getHeight();

        int windowWidth = getWidth()-PADDING-PADDING;
        int windowHeight = getHeight()-PADDING-PADDING;

        double scaleX = (double)(windowWidth)/imgWidth;
        double scaleY = (double)(windowHeight)/imgHeight;
        double scale = Math.min(scaleX, scaleY);
        int scaledImgWidth = (int) (imgWidth*scale);
        int scaledImgHeight = (int) (imgHeight*scale);

        int startX = PADDING + (windowWidth/2) - (scaledImgWidth/2);
        int startY = PADDING + (windowHeight/2) - (scaledImgHeight/2);

        int xCount = context.getxCount();
        int yCount = context.getyCount();

        int elementWidth = (int) ((imgWidth/xCount) * scale);
        int elementHeight = (int) ((imgHeight/yCount) * scale);

        if (showCompleteImage) {
            g.drawImage(context.getSource(), startX, startY, startX+scaledImgWidth, startY+scaledImgHeight, 0, 0, imgWidth, imgHeight, null);
        } else {
            //draw elements

            Runnable r = null;
            for (int i = 0; i < context.getPieces().length; i++) {
                int x = i%xCount;
                int y = (i-x)/xCount;

                DefaultPuzzlePiece piece = context.getPieces()[i];
                int imgStartX = startX + (x*elementWidth);
                int imgStartY = startY + (y*elementHeight);

                int imgEndX = imgStartX+elementWidth;
                int imgEndY = imgStartY+elementHeight;

                g.drawImage(piece.getImg(), imgStartX, imgStartY, imgEndX, imgEndY, 0, 0, piece.getImg().getWidth(), piece.getImg().getHeight(), null);

                //border
                if (i == selectedIndex) {
                    r = () -> {
                        g.setColor(Color.YELLOW);
                        g.drawRect(imgStartX, imgStartY, elementWidth, elementHeight);
                    };
                } else if (showGrid) {
                    g.setColor(Color.BLACK);
                    g.drawRect(imgStartX, imgStartY, elementWidth, elementHeight);
                }

                //if mouse inside
                if (imgStartX < lastMouseX && imgEndX > lastMouseX && imgStartY < lastMouseY && imgEndY > lastMouseY) {
                    if (clicking) {
                        //click
                        g.setColor(new Color(255,255,255,128));
                        clickedIndex = i;
                    } else {
                        //hover
                        g.setColor(new Color(200,200,200,128));
                    }
                    g.fillRect(imgStartX,imgStartY,elementWidth,elementHeight);
                }
            }
            if (r != null) r.run();
        }

        g.setStroke(oldStroke);

        if (getFadeState() == FadeState.FADING_OUT) {
            g.setColor(new Color(0f,0f,0f, getFadeProgress()/100f));
            g.fillRect(0,0,getWidth(), getHeight());
        } else if (getFadeState() == FadeState.FADING_IN) {
            g.setColor(new Color(0f,0f,0f, (100-getFadeProgress())/100f));
            g.fillRect(0,0,getWidth(), getHeight());
        } else if (getFadeState() == FadeState.FADED_OUT) {
            g.setColor(new Color(0f,0f,0f, 1f));
            g.fillRect(0,0,getWidth(), getHeight());
        }
    }
}
