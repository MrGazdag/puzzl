package me.mrgazdag.programs.puzzl;

import me.mrgazdag.programs.puzzl.games.PuzzleContext;

import java.awt.image.BufferedImage;

public class PuzzleContextBuilder {
    private final BufferedImage source;
    private PuzzleType type;
    private int horizontalCount;
    private int verticalCount;

    public PuzzleContextBuilder(BufferedImage source) {
        this.source = source;
        this.type = PuzzleType.DEFAULT;
        this.verticalCount = 1;
        this.horizontalCount = 1;
    }

    public void setType(PuzzleType type) {
        this.type = type;
    }

    public void setHorizontalCount(int horizontalCount) {
        this.horizontalCount = horizontalCount;
    }

    public void setVerticalCount(int verticalCount) {
        this.verticalCount = verticalCount;
    }

    public void setCounts(int horizontalCount, int verticalCount) {
        this.horizontalCount = horizontalCount;
        this.verticalCount = verticalCount;
    }

    public <T extends PuzzleContext> T build() {
        return type.createContextFromBuilder(this);
    }

    public int getVerticalCount() {
        return verticalCount;
    }

    public int getHorizontalCount() {
        return horizontalCount;
    }

    public PuzzleType getType() {
        return type;
    }

    public BufferedImage getSource() {
        return source;
    }
}
