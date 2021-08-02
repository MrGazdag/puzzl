package me.mrgazdag.programs.puzzl.games.defaultpuzzle;

import me.mrgazdag.programs.puzzl.games.PuzzleContext;

import java.awt.image.BufferedImage;

public class DefaultPuzzleContext implements PuzzleContext {
    private final BufferedImage source;
    private final DefaultPuzzlePiece[] pieces;
    private final int xCount;
    private final int yCount;

    private boolean completed;
    public DefaultPuzzleContext(BufferedImage source, DefaultPuzzlePiece[] pieces, int xCount, int yCount) {
        this.source = source;
        this.pieces = pieces;
        this.xCount = xCount;
        this.yCount = yCount;
    }

    public int getxCount() {
        return xCount;
    }

    public int getyCount() {
        return yCount;
    }

    public DefaultPuzzlePiece[] getPieces() {
        return pieces;
    }

    public BufferedImage getSource() {
        return source;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void swap(int source, int destination) {
        if (isCompleted()) return;

        DefaultPuzzlePiece temp = pieces[destination];

        pieces[source].setCurrentPosition(destination);
        pieces[destination] = pieces[source];

        temp.setCurrentPosition(source);
        pieces[source] = temp;
        if (check()) {
            System.out.println("Map completed");
            completed = true;
        }
    }

    public boolean check() {
        //int i = 0;
        for (DefaultPuzzlePiece piece : pieces) {
            if (!piece.isInCorrectPosition()) {
                //System.out.println("piece " + piece.getCurrentPosition() + " at " + i + " is not at its good position " + piece.getGoodPosition());
                return false;
            }
            //i++;
        }
        return true;
    }
}
