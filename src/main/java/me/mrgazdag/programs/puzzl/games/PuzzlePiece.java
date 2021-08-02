package me.mrgazdag.programs.puzzl.games;

import java.awt.image.BufferedImage;

public abstract class PuzzlePiece {
    private final BufferedImage img;

    public PuzzlePiece(BufferedImage img) {
        this.img = img;
    }

    public BufferedImage getImg() {
        return img;
    }

    public abstract boolean isInCorrectPosition();
}
