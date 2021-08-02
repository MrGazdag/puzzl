package me.mrgazdag.programs.puzzl;

import java.io.IOException;

public class PuzzleMain {
    public static void main(String[] args) {
        try {
            new PuzzleFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
