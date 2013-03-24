package krpi.boardgame.tetris;

import javax.swing.JFrame;

public class TheTetrisGame {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TetrisPanel panel = new TetrisPanel();
                panel.setFocusable(true);
                JFrame frame = new JFrame("Tetris");
                frame.setResizable(false);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(panel);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
