package me.mateus.tictactoe.bot;

import me.mateus.tictactoe.game.GameFrame;
import me.mateus.tictactoe.game.Mark;

public class BotPlayer {

    private final GameFrame gameFrame;
    private BotStrategy botStrategy;

    public BotPlayer(GameFrame gameFrame, BotStrategy botStrategy) {
        this.gameFrame = gameFrame;
        this.botStrategy = botStrategy;
    }

    public void setBotStrategy(BotStrategy botStrategy) {
        this.botStrategy = botStrategy;
    }

    public void makeMove() {
        Mark[] board = gameFrame.getBoard();
        int playIdx = botStrategy.play(board);

        gameFrame.setMark(playIdx, gameFrame.getPlayer());
    }
}
