package krpi.boardgame.tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class KeyboardHandler extends KeyAdapter {

    private static final Logger LOGGER = Logger.getGlobal();

    private final TetrisBoard board;

    private final TetrisPanel panel;
    
    public KeyboardHandler(TetrisBoard board, TetrisPanel panel) {
        this.board = requireNonNull(board);
        this.panel = requireNonNull(panel);
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent evt) {
        final int code = evt.getKeyCode();
        final int modifiers = evt.getModifiersEx();
        if ((code == KeyEvent.VK_LEFT) && (modifiers & KeyEvent.SHIFT_DOWN_MASK) == 0) {
            moveLeft();
        }
        if ((code == KeyEvent.VK_RIGHT) && (modifiers & KeyEvent.SHIFT_DOWN_MASK) == 0) {
            moveRight();
        }
        if ((code == KeyEvent.VK_DOWN) && (modifiers & KeyEvent.SHIFT_DOWN_MASK) == 0) {
            moveDown();
        }
        if ((code == KeyEvent.VK_LEFT) && (modifiers & KeyEvent.SHIFT_DOWN_MASK) == KeyEvent.SHIFT_DOWN_MASK) {
            rotateLeft();
        }
        if ((code == KeyEvent.VK_RIGHT) && (modifiers & KeyEvent.SHIFT_DOWN_MASK) == KeyEvent.SHIFT_DOWN_MASK) {
            rotateRight();
        }
    }

    private void moveLeft() {
        LOGGER.info("Move Left");
        board.moveLeft();
        panel.repaint();
    }

    private void moveRight() {
        LOGGER.info("Move Right");
        board.moveRight();
        panel.repaint();
    }

    private void moveDown() {
        LOGGER.info("Move Down");
        board.moveDown();
        panel.repaint();
    }

    private void rotateLeft() {
        LOGGER.info("Rotate Left");
        board.rotateCcw();
        panel.repaint();
    }

    private void rotateRight() {
        LOGGER.info("Rotate Right");
        board.rotateCw();
        panel.repaint();
    }

}
