package me.mrgazdag.programs.puzzl.particles;

import me.mrgazdag.programs.puzzl.gui.PuzzleGUI;

import java.awt.*;

public abstract class Particle {
    protected double x;
    protected double y;

    private boolean despawned;
    private boolean requiresRefresh;

    public Particle(double x, double y) {
        this.x = x;
        this.y = y;
        this.despawned = false;
    }

    public void tick(PuzzleGUI gui) {
        tickImpl(gui);
    }
    protected abstract void tickImpl(PuzzleGUI gui);
    public void render(PuzzleGUI gui, Graphics2D g) {
        requiresRefresh = false;
        renderImpl(gui, g);
    }
    protected abstract void renderImpl(PuzzleGUI gui, Graphics2D g);

    public boolean isDespawned() {
        return despawned;
    }

    protected void despawn() {
        this.despawned = true;
    }

    public boolean isRequireRefresh() {
        return requiresRefresh;
    }

    protected void requireRefresh() {
        this.requiresRefresh = true;
    }
}
