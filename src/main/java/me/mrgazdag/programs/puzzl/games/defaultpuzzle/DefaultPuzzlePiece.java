package me.mrgazdag.programs.puzzl.games.defaultpuzzle;

import me.mrgazdag.programs.puzzl.games.PuzzlePiece;

import java.awt.image.BufferedImage;

public class DefaultPuzzlePiece extends PuzzlePiece {
    private int currentPosition;
    private final int goodPosition;

    public DefaultPuzzlePiece(BufferedImage img, int goodPosition) {
        super(img);
        this.currentPosition = 0;
        this.goodPosition = goodPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getGoodPosition() {
        return goodPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isInCorrectPosition() {
        return currentPosition == goodPosition;
    }
}
