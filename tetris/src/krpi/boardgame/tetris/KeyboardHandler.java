package krpi.boardgame.tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardHandler extends KeyAdapter {

    private final TetrisBoard board;
    
    public KeyboardHandler(TetrisBoard board) {
        this.board = board;
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
        if ((code == KeyEvent.VK_UP) && (modifiers & KeyEvent.SHIFT_DOWN_MASK) == 0) {
            moveUp();
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
        if (code == KeyEvent.VK_SPACE) {
            spawnNext();
        }
    }

    private void moveLeft() {
        System.out.println("Move Left");
        board.moveLeft();
    }

    private void moveRight() {
        System.out.println("Move Right");
        board.moveRight();
    }

    private void moveUp() {
        System.out.println("Move Up");
        board.moveUp();
    }

    private void moveDown() {
        System.out.println("Move Down");
        board.moveDown();
    }

    private void rotateLeft() {
        System.out.println("Rotate Left");
        board.rotateCcw();
    }

    private void rotateRight() {
        System.out.println("Rotate Right");
        board.rotateCw();
    }

    private void spawnNext() {
        System.out.println("Spawn Next");
        board.spawnNext();
    }
    
}
