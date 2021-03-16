package me.mateus.tictactoe.game;


import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame {

    private final Mark[] board = new Mark[9];
    private final Color[] WIN_COLORS = new Color[]{Color.RED, Color.GREEN, Color.DARK_GRAY};
    private RenderPanel renderPanel;
    private int player = 0;

    public GameFrame() {
        for (int i = 0; i < board.length; i++) {
            board[i] = new Mark();
        }
        setTitle("Tic Tac Toe");
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        renderPanel = new RenderPanel(this);
        add(renderPanel, BorderLayout.CENTER);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener((l) -> newGame());
        menu.add(newGame);
        menu.addSeparator();
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((l) -> dispose());
        menu.add(exit);
        menuBar.add(menu);

        setJMenuBar(menuBar);
    }

    public int getPlayer() {
        return player;
    }

    public void nextPlayer() {
        player = player == 0 ? 1 : 0;
    }

    public Mark[] getBoard() {
        return board;
    }
    public void setMark(int idx, int player) {
        board[idx].setPlayer(player);
    }

    public Mark getMark(int idx) {
        return board[idx];
    }

    public void win(boolean tie) {
        if (!tie) {
            Color color = WIN_COLORS[player];
            UIManager.put("OptionPane.messageForeground", color);
            JOptionPane.showMessageDialog(this, "Player " + (player + 1) + " wins!", "WIN", JOptionPane.INFORMATION_MESSAGE);
        } else {
            UIManager.put("OptionPane.messageForeground", WIN_COLORS[2]);
            JOptionPane.showMessageDialog(this, "Tie!", "Tie", JOptionPane.INFORMATION_MESSAGE);
        }
        newGame();
    }

    public void newGame() {
        for (int i = 0; i < board.length; i++) {
            board[i] = new Mark();
        }
        player = 0;
        if (renderPanel != null)
            renderPanel.repaint();
    }
}
