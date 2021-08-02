package me.mrgazdag.programs.puzzl;

import me.mrgazdag.programs.puzzl.games.PuzzleContext;
import me.mrgazdag.programs.puzzl.games.defaultpuzzle.DefaultPuzzleContext;
import me.mrgazdag.programs.puzzl.games.defaultpuzzle.DefaultPuzzlePiece;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum PuzzleType {
    DEFAULT {
        @Override
        public <T extends PuzzleContext> T createContextFromBuilder(PuzzleContextBuilder builder) {
            BufferedImage source = builder.getSource();
            int sW = source.getWidth();
            int sH = source.getHeight();

            int elementWidth = sW/builder.getHorizontalCount();
            int elementHeight = sH/builder.getVerticalCount();

            int xCount = builder.getHorizontalCount();
            int yCount = builder.getVerticalCount();

            List<DefaultPuzzlePiece> piecesList = new ArrayList<>(yCount*xCount);
            for (int y = 0; y < builder.getVerticalCount(); y++) {
                for (int x = 0; x < builder.getHorizontalCount(); x++) {
                    BufferedImage subImage = source.getSubimage(x*elementWidth, y*elementHeight, elementWidth, elementHeight);
                    DefaultPuzzlePiece piece = new DefaultPuzzlePiece(subImage, (y*xCount)+x);
                    piecesList.add(piece);
                }
            }
            Collections.shuffle(piecesList);

            DefaultPuzzlePiece[] pieces = new DefaultPuzzlePiece[builder.getVerticalCount()*builder.getHorizontalCount()];

            for (int i = 0; i < piecesList.size(); i++) {
                DefaultPuzzlePiece piece = piecesList.get(i);
                piece.setCurrentPosition(i);
                pieces[i] = piece;
            }

            //noinspection unchecked
            return (T) new DefaultPuzzleContext(source,pieces, xCount, yCount);
        }
    },
    JIGSAW {
        @Override
        public <T extends PuzzleContext> T createContextFromBuilder(PuzzleContextBuilder builder) {
            return null;
        }
    }
    ;

    public abstract <T extends PuzzleContext> T createContextFromBuilder(PuzzleContextBuilder builder);
}
