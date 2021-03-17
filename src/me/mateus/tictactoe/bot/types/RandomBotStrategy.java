package me.mateus.tictactoe.bot.types;

import me.mateus.tictactoe.bot.BotStrategy;
import me.mateus.tictactoe.game.Mark;

import java.util.Random;

public class RandomBotStrategy implements BotStrategy {
    private final Random RANDOM = new Random();

    @Override
    public int play(Mark[] board) {
        int play;
        int len = board.length;
        do {
            play = RANDOM.nextInt(len);
        } while (board[play].getPlayer() != -1);
        return play;
    }
}
