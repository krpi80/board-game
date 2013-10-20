package krpi.boardgame.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TheTetrisGame {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final TetrisBoard game = new TetrisBoard(15, 20);

                final TetrisPanel panel = new TetrisPanel(game);
                panel.setFocusable(true);

                final KeyboardHandler keyboardHandler = new KeyboardHandler(game, panel);

                panel.addKeyListener(keyboardHandler);

                final Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        game.tick();
                        panel.repaint();
                        if (game.isFinished()) {
                            panel.removeKeyListener(keyboardHandler);
                        }
                    }
                });
                timer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (game.isFinished()) {
                            timer.stop();
                        }
                    }
                });
                timer.setInitialDelay(500);
                timer.start();

                JFrame frame = new JFrame("Tetris");
                frame.setResizable(false);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.add(panel);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
