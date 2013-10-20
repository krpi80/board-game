package krpi.boardgame.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class TetrisPanel extends JPanel {

    private static final int TILE_SIZE = 25;
    
    private static final int TILE_BORDER = 1;
    
    private final TetrisBoard game;

    private final Font font;

    public static final String GAME_OVER = "Game Over";

    public TetrisPanel(TetrisBoard game) {
        this.game = Objects.requireNonNull(game);
        int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
        int fontSize = (int) Math.round(16.0 * screenRes / 72.0);
        font = new Font("Arial", Font.BOLD, fontSize);
        setPreferredSize(getBoardDimension());
    }

    private Dimension getBoardDimension() {
        return new Dimension(game.getWidth()*TILE_SIZE, game.getHeight()*TILE_SIZE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintSpawnArea(g);
        paintAllTiles(g);
        paintPoints(g);
        if (game.isFinished()) {
            paintGameOver(g);
        }
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
        int innerSize = TILE_SIZE - TILE_BORDER - TILE_BORDER;
        g.fillRect(x*TILE_SIZE + TILE_BORDER, y*TILE_SIZE + TILE_BORDER,
                innerSize, innerSize);
    }

    private void paintPoints(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(String.valueOf(game.getPoints()), 5, 16);
    }

    private void paintGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(GAME_OVER,
                (int)(getBoardDimension().getWidth() - fm.stringWidth(GAME_OVER))/2,
                (int)(getBoardDimension().getHeight() - fm.getHeight())/2);
    }

}
