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
        int code = evt.getKeyCode();
        int modifiers = evt.getModifiersEx();
        if ((code == KeyEvent.VK_LEFT) && (modifiers & KeyEvent.SHIFT_DOWN_MASK) == 0) {
            moveLeft();
            board.moveLeft();
        }
        if ((code == KeyEvent.VK_RIGHT) && (modifiers & KeyEvent.SHIFT_DOWN_MASK) == 0) {
            moveRight();
            board.moveRight();
        }
        if ((code == KeyEvent.VK_UP) && (modifiers & KeyEvent.SHIFT_DOWN_MASK) == 0) {
            moveUp();
            board.moveUp();
        }
        if ((code == KeyEvent.VK_DOWN) && (modifiers & KeyEvent.SHIFT_DOWN_MASK) == 0) {
            moveDown();
            board.moveDown();
        }
        if ((code == KeyEvent.VK_LEFT) && (modifiers & KeyEvent.SHIFT_DOWN_MASK) == KeyEvent.SHIFT_DOWN_MASK) {
            rotateLeft();
            board.rotateCcw();
        }
        if ((code == KeyEvent.VK_RIGHT) && (modifiers & KeyEvent.SHIFT_DOWN_MASK) == KeyEvent.SHIFT_DOWN_MASK) {
            rotateRight();
            board.rotateCw();
        }
        if (code == KeyEvent.VK_SPACE) {
            spawnNext();
            board.spawnNext();
        }
    }

    private void moveLeft() {
        System.out.println("Move Left");
    }

    private void moveRight() {
        System.out.println("Move Right");
    }

    private void moveUp() {
        System.out.println("Move Up");
    }

    private void moveDown() {
        System.out.println("Move Down");
    }

    private void rotateLeft() {
        System.out.println("Rotate Left");
    }

    private void rotateRight() {
        System.out.println("Rotate Right");
    }

    private void spawnNext() {
        System.out.println("Spawn Next");
    }
    
}
