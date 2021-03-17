package me.mateus.tictactoe.game;


import me.mateus.tictactoe.bot.BotPlayer;
import me.mateus.tictactoe.bot.BotStrategy;
import me.mateus.tictactoe.bot.types.MinimaxBotStrategy;
import me.mateus.tictactoe.bot.types.RandomBotStrategy;

import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame {

    private final Mark[] board = new Mark[9];
    private final Color[] WIN_COLORS = new Color[]{Color.RED, Color.GREEN, Color.DARK_GRAY};
    private final BotStrategy[] BOT_STRATEGIES = new BotStrategy[]{new RandomBotStrategy(), new MinimaxBotStrategy()};
    private final BotPlayer botPlayer;
    private JCheckBoxMenuItem enableBot;
    private RenderPanel renderPanel;
    private int player = 0;

    public GameFrame() {
        for (int i = 0; i < board.length; i++) {
            board[i] = new Mark();
        }
        this.botPlayer = new BotPlayer(this, new RandomBotStrategy());
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
        JMenu gameMenu = new JMenu("Game");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener((l) -> newGame());
        gameMenu.add(newGame);
        gameMenu.addSeparator();
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((l) -> dispose());
        gameMenu.add(exit);
        menuBar.add(gameMenu);

        JMenu botMenu = new JMenu("Bot");

        enableBot = new JCheckBoxMenuItem("Enable Bot");
        botMenu.add(enableBot);

        JMenuItem changeDifficulty = new JMenuItem("Change Difficulty");
        changeDifficulty.addActionListener((l) -> {
            String[] options = new String[]{"Easy", "Impossible"};
            int response = JOptionPane.showOptionDialog(this, "Choose the bot difficulty", "Bot Difficulty",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (response != -1){
                botPlayer.setBotStrategy(BOT_STRATEGIES[response]);
            }
        });
        botMenu.add(changeDifficulty);

        menuBar.add(botMenu);

        setJMenuBar(menuBar);
    }

    public int getPlayer() {
        return player;
    }

    public void nextPlayer() {
        player = player == 0 ? 1 : 0;
        if (player == 1 && enableBot != null && enableBot.getState()) {
            botPlayer.makeMove();
            if (!renderPanel.checkWin(board)) {
                nextPlayer();
            }
        }
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
