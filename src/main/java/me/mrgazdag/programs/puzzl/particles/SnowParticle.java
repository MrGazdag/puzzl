package me.mrgazdag.programs.puzzl.particles;

import me.mrgazdag.programs.puzzl.gui.PuzzleGUI;

import java.awt.*;
import java.util.Random;

public class SnowParticle extends Particle {
    private static final Random RANDOM = new Random();
    private final Color color;
    private final double windX;
    private final double gravity;
    private final double scale;

    public SnowParticle(double x, double y, double windX, double scale) {
        super(x, y);
        this.color = Color.getHSBColor(0f, 0f, (RANDOM.nextFloat()*0.3f) + 0.7f);
        this.windX = windX+(RANDOM.nextDouble()*0.2);
        this.gravity = 2;
        this.scale = scale;
    }

    @Override
    protected void tickImpl(PuzzleGUI gui) {
        x+=RANDOM.nextDouble()*windX;
        y+=gravity;

        if (y > gui.getHeight()) despawn();
        else requireRefresh();
    }

    @Override
    public void renderImpl(PuzzleGUI gui, Graphics2D g) {
        g.setColor(color);
        g.fillRect(
                (int) x,
                (int) y,
                (int) (4*scale),
                (int) (4*scale)
        );
    }
}
