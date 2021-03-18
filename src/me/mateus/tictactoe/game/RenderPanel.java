package me.mateus.tictactoe.game;

import me.mateus.tictactoe.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RenderPanel extends JPanel {

    private final int[][] WIN_CONDITIONS = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
    };

    private final GameFrame gameFrame;
    private final List<Pair<Integer, Integer>> clickableMarks = new ArrayList<>();
    private final BufferedImage emptyMark = new BufferedImage(98, 98, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage xMark;
    private BufferedImage oMark;
    private BufferedImage grid;

    public RenderPanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream gridStream = classLoader.getResourceAsStream("grid.png");
        InputStream xStream = classLoader.getResourceAsStream("x.png");
        InputStream oStream = classLoader.getResourceAsStream("o.png");

        Objects.requireNonNull(gridStream, "Grid stream can't be null");
        Objects.requireNonNull(xStream, "X mark stream can't be null");
        Objects.requireNonNull(oStream, "O mark stream can't be null");

        try {
            grid = ImageIO.read(gridStream);
            xMark = ImageIO.read(xStream);
            oMark = ImageIO.read(oStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        clickableMarks.add(new Pair<>(35 + 11, 48 + 11));
        clickableMarks.add(new Pair<>(148, 48 + 11));
        clickableMarks.add(new Pair<>(250, 48 + 11));
        clickableMarks.add(new Pair<>(35 + 11, 161));
        clickableMarks.add(new Pair<>(148, 161));
        clickableMarks.add(new Pair<>(250, 161));
        clickableMarks.add(new Pair<>(35 + 11, 264));
        clickableMarks.add(new Pair<>(148, 264));
        clickableMarks.add(new Pair<>(250, 264));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Point point = e.getPoint();
                for (int i = 0; i < clickableMarks.size(); i++) {
                    Pair<Integer, Integer> clickableMark = clickableMarks.get(i);
                    Rectangle rectangle = new Rectangle(clickableMark.getValue1(), clickableMark.getValue2() - 35, 98, 98);
                    if (rectangle.contains(point)) {
                        if (gameFrame.getMark(i).getPlayer() != -1)
                            break;
                        gameFrame.setMark(i, gameFrame.getPlayer());
                        if (checkWin(gameFrame.getBoard())) {
                            break;
                        }
                        gameFrame.nextPlayer();
                        repaint();
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (grid != null) {
            int x = (getWidth() - grid.getWidth()) / 2;
            int y = (getHeight() - grid.getHeight()) / 2;
            g2d.drawImage(grid, x, y, null);
        }
        Mark[] board = gameFrame.getBoard();
        for (int i = 0; i < board.length; i++) {
            Pair<Integer, Integer> point = clickableMarks.get(i);
            Mark mark = board[i];
            BufferedImage markImage = null;
            switch (mark.getPlayer()) {
                case 0:
                    if (xMark != null)
                        markImage = xMark;
                    break;
                case 1:
                    if (oMark != null)
                        markImage = oMark;
                    break;
                default:
                case -1:
                    markImage = emptyMark;
                    break;
            }

            g2d.drawImage(markImage, point.getValue1(), point.getValue2() - 35, 98, 98,null);
        }
    }

    public boolean checkWin(Mark[] board) {
        for (int[] wins : WIN_CONDITIONS) {
            int player = board[wins[0]].getPlayer();
            if (player == -1)
                continue;
            if (player == board[wins[1]].getPlayer() && board[wins[1]].getPlayer() == board[wins[2]].getPlayer()) {
                repaint();
                gameFrame.win(false);
                return true;
            }
        }
        if (Arrays.stream(board).noneMatch(m -> m.getPlayer() == -1)) {
            repaint();
            gameFrame.win(true);
            return true;
        }
        return false;
    }
}
