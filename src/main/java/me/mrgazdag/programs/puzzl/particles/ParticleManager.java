package me.mrgazdag.programs.puzzl.particles;

import me.mrgazdag.programs.puzzl.PuzzleFrame;
import me.mrgazdag.programs.puzzl.gui.PuzzleGUI;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ParticleManager {
    private final PuzzleFrame frame;
    private final Set<Particle> particles;

    public ParticleManager(PuzzleFrame frame) {
        this.frame = frame;
        this.particles = new HashSet<>();
    }

    public void spawnParticle(Particle p) {
        synchronized (particles) {
            this.particles.add(p);
            this.frame.repaint();
        }
    }

    public void clear() {
        synchronized (particles) {
            this.particles.clear();
        }
    }

    public void tick(PuzzleGUI gui) {
        boolean refresh = false;
        synchronized (particles) {
            Iterator<Particle> it = particles.iterator();
            while (it.hasNext()) {
                Particle p = it.next();
                p.tick(gui);
                if (p.isDespawned()) {
                    it.remove();
                    refresh = true;
                }
                if (p.isRequireRefresh()) refresh = true;
            }
            if (refresh) frame.repaint();
        }
    }

    public void render(PuzzleGUI gui, Graphics2D g) {
        synchronized (particles) {
            for (Particle particle : particles) {
                particle.render(gui, g);
            }
        }
    }
}
