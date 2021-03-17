package me.mateus.tictactoe.bot;

import me.mateus.tictactoe.game.Mark;

public interface BotStrategy {

    int play(Mark[] board);
}
