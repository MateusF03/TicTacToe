package me.mateus.tictactoe.bot.types;

import me.mateus.tictactoe.bot.BotStrategy;
import me.mateus.tictactoe.game.Mark;

import java.util.Arrays;

public class MinimaxBotStrategy implements BotStrategy {

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

    @Override
    public int play(Mark[] board) {
        Mark[] copiedBoard = Arrays.copyOf(board, board.length);
        long depth = Arrays.stream(copiedBoard).filter(m -> m.getPlayer() == -1).count();
        int bestScore = Integer.MIN_VALUE;
        int bestMove = 0;
        for (int i = 0; i < copiedBoard.length; i++) {
            Mark mark = copiedBoard[i];
            if (mark.getPlayer() == -1) {
                mark.setPlayer(1);
                int score = minimax(copiedBoard, depth, false);
                mark.setPlayer(-1);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    private int minimax(Mark[] board, long depth, boolean maximizingPlayer) {
        int win = checkWin(board);
        if (win != -2 || depth == 0) {
            return win;
        }
        int bestScore;
        if (maximizingPlayer) {
            bestScore = Integer.MIN_VALUE;
            for (Mark mark : board) {
                if (mark.getPlayer() == -1) {
                    mark.setPlayer(1);
                    int score = minimax(board, depth - 1, false);
                    mark.setPlayer(-1);
                    bestScore = Math.max(score, bestScore);
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (Mark mark : board) {
                if (mark.getPlayer() == -1) {
                    mark.setPlayer(0);
                    int score = minimax(board, depth - 1, true);
                    mark.setPlayer(-1);
                    bestScore = Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
    }

    private int checkWin(Mark[] board) {
        for (int[] winCondition : WIN_CONDITIONS) {
            int player = board[winCondition[0]].getPlayer();
            if (player != -1) {
                if (player == board[winCondition[1]].getPlayer() && player == board[winCondition[2]].getPlayer()) {
                    if (player == 0)
                        return -1;
                    else
                        return 1;
                }
            }
        }
        if (Arrays.stream(board).noneMatch(m -> m.getPlayer() == -1)) {
            return 0;
        }
        return -2;
    }
}
