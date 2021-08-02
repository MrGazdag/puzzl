package me.mrgazdag.programs.puzzl.particles;

import me.mrgazdag.programs.puzzl.gui.PuzzleGUI;

import java.awt.*;
import java.util.Random;

public class ConfettiParticle extends Particle {
    private static final Random RANDOM = new Random();
    private final Color color;
    private double velX;
    private double velY;
    private final double scale;
    private final double drag;
    private final double gravity;
    private final double maxVelY;

    public ConfettiParticle(int x, int y, double velX, double velY, double scale) {
        super(x, y);
        this.velX = velX;
        this.velY = velY;
        this.color = Color.getHSBColor(RANDOM.nextFloat(), 1f, 1f);
        this.scale = scale;
        this.drag =    0.2;
        this.gravity = 0.1;
        this.maxVelY = 30;
    }

    public ConfettiParticle(int x, int y, double velX, double velY, double scale, float color) {
        super(x, y);
        this.velX = velX;
        this.velY = velY;
        this.color = Color.getHSBColor(color+(0.05f* RANDOM.nextFloat()), (RANDOM.nextFloat()*0.5f)+0.5f, (RANDOM.nextFloat()*0.5f)+0.5f);
        this.scale = scale;
        this.drag =    0.2;
        this.gravity = 0.1;
        this.maxVelY = 30;
    }

    @Override
    protected void tickImpl(PuzzleGUI gui) {
        if (velX != 0) {
            double signum = Math.signum(velX);
            velX-=signum*drag;
            if (Math.signum(velX) != signum) velX = 0;
        }
        velY = Math.min(velY+gravity+drag, maxVelY);

        x+=velX;
        y+=velY;

        if (x < 0 || x > gui.getWidth() || y < 0 || y > gui.getHeight()) despawn();
        else requireRefresh();
    }

    @Override
    public void renderImpl(PuzzleGUI gui, Graphics2D g) {
        g.setColor(color);
        g.fillRect(
                (int) x,
                (int) y,
                (int) (10*scale),
                (int) (10*scale)
        );
    }
}
