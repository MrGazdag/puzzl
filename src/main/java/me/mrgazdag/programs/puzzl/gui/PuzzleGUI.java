package me.mrgazdag.programs.puzzl.gui;

import me.mrgazdag.programs.puzzl.FadeState;
import me.mrgazdag.programs.puzzl.PuzzleFrame;

import java.awt.*;
import java.awt.event.*;

public abstract class PuzzleGUI implements KeyListener, MouseListener, MouseMotionListener {
    protected final PuzzleFrame frame;

    public PuzzleGUI(PuzzleFrame frame) {
        this.frame = frame;
        this.fadeState = FadeState.NOT_FADING;
        this.fadeNextState = FadeState.NOT_FADING;
    }

    public abstract void render(Graphics2D g);

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

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

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    private int fadeSpeed;
    private int fadeProgress;
    private FadeState fadeState;
    private Runnable fadeCompletedTask;
    private FadeState fadeNextState;
    public void fadeIn(Runnable completedTask) {
        this.fadeState = FadeState.FADING_IN;
        this.fadeProgress = 0;
        this.fadeSpeed = 5;
        this.fadeCompletedTask = completedTask;
        this.fadeNextState = FadeState.NOT_FADING;
    }
    public void fadeOut(Runnable completedTask) {
        this.fadeState = FadeState.FADING_OUT;
        this.fadeProgress = 0;
        this.fadeSpeed = 5;
        this.fadeCompletedTask = completedTask;
        this.fadeNextState = FadeState.FADED_OUT;
    }
    public void tickFade() {
        if (fadeState == fadeNextState) return;
        if (fadeProgress >= 100) {
            //if (fadeState == FadeState.FADING_IN) fadeState = FadeState.NOT_FADING;
            this.fadeCompletedTask.run();
            fadeState = fadeNextState;
            return;
        }
        this.fadeProgress+=fadeSpeed;
        frame.repaint();
    }

    public int getWidth() {
        return frame.getContentPaneWidth();
    }
    public int getHeight() {
        return frame.getContentPaneHeight();
    }

    public FadeState getFadeState() {
        return fadeState;
    }

    public int getFadeProgress() {
        return fadeProgress;
    }
}
