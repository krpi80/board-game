package krpi.boardgame.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class TetrisPanel extends JPanel {
    
    private static final int TILE_SIZE = 30;
    
    private static final int TILE_BORDER = 2;
    
    private final TetrisBoard game = new TetrisBoard(10, 10);

    public TetrisPanel() {
        initComponents();
    }

    private void initComponents() {
        setPreferredSize(getBoardDimension());
        
        addKeyListener(new KeyboardHandler(game));
        
        game.setObserver(new TetrisBoard.Observer() {
            @Override
            public void onChange() {
                repaint();
            }
        });
    }
    
    private Dimension getBoardDimension() {
        return new Dimension(game.getWidth()*TILE_SIZE, 
                game.getHeight()*TILE_SIZE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintSpawnArea(g);
        paintAllTiles(g);
    }

    private void paintSpawnArea(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(game.getSpawnArea().getX()*TILE_SIZE, 
                game.getSpawnArea().getY()*TILE_SIZE, 
                game.getSpawnArea().getWidth()*TILE_SIZE, 
                game.getSpawnArea().getHeight()*TILE_SIZE);
    }

    private void paintAllTiles(Graphics g) {
        for(Tile t : game.getTiles()) {
            paintTile(g, t.getX(), t.getY());
        }
    }

    private static void paintTile(Graphics g, int x, int y) {
        g.setColor(Color.YELLOW);
        g.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
        
        g.setColor(Color.GREEN);
        int innerSize = TILE_SIZE - TILE_BORDER*2;
        g.fillRect(x*TILE_SIZE + TILE_BORDER, y*TILE_SIZE + TILE_BORDER, 
                innerSize, innerSize);
    }

}
