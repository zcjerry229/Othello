import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * <pre>
 * The simple implementation of Othello game.
 *
 * Game Rules :
 * - Two players 'O' and 'X'.
 * - Players take alternate turns.
 * - 'X' goes first and *must* place an 'X' on the board,
 *   in such a position that there exists at least one straight (horizontal, vertical, or diagonal)
 *   occupied line of 'O's between the new 'X' and another 'X' on the board.
 * - After placing the piece, all 'O's lying on all straight lines between the
 *   new 'X' and any existing 'X' are captured (i.e. they turn into 'X's ).
 * - Now 'O' plays. 'O' operates under the same rules, with the roles reversed:
 *  'O' places an 'O' on the board in such a position where *at least one* 'X' is captured.
 * - If there is no valid move for current player, play passes back to the other player.
 * - The player with the most pieces on the board at the end of the game wins.
 *
 * How to play :
 * - Moves are specified as coordinates. Column+row or row+column (e.g. 3d or d3)
 *
 *
 * The game ends when either
 *  1. neither player can make a valid move
 *  2. the board is full
 * </pre>
 *
 * @author Zhang, Chen
 * @see <a href="https://en.wikipedia.org/wiki/Reversi">https://en.wikipedia.org/wiki/Reversi</a>
 */
public class Othello {

    /**
     * Play Othello game in alternate way
     *
     * @return the board stats
     */
    public static void playGame() {
        playGame(null);
    }


    /**
     * Return game board status after all moves processed.<p>
     * Please NOTE this method reads moves one by one (treat first move from 'X')<p>
     * But if there is no available move for opponent after one specific move, it will automatically
     * treat next move from current player.
     *
     * @param moves all players inputs
     * @return the game  board status
     */
    public static String playGame(String moves) {
        Board board = new Board();
        char currentPlayer = Board.DARK; //The 'X' takes first move

        String[] providedMoves = null;
        int provided_move_index = 0;
        if (moves != null) {
            providedMoves = moves.split(",");
        }

        Scanner keyboard = new Scanner(System.in);
        String targetMove = null;
        while (true) {
            if (!board.hasAvailableMove(currentPlayer)) {
                currentPlayer = Board.getOpponent(currentPlayer);
                System.out.printf("No valid move after '%s', give turn to %s %n", targetMove.trim(), currentPlayer);
                continue;
            }

            if (providedMoves != null) {
                targetMove = nextValidMoveFromInput(board, currentPlayer, providedMoves, provided_move_index++);
                if (targetMove == null) {
                    break;
                }
            } else {
                targetMove = nextValidMoveFromUser(board, currentPlayer, keyboard);
            }

            board.flip(currentPlayer, Disk.getDisk(targetMove));
            currentPlayer = Board.getOpponent(currentPlayer);
            if (board.isGameOver()) {
                board.endGame();
                break;
            }
        }
        return board.getBoardInfo();
    }


    private static String nextValidMoveFromInput(Board board, char player,
                                                 String[] provided_moves, int moveIndex) {
        if (moveIndex >= provided_moves.length) {
            return null;
        }
        String targetMove = provided_moves[moveIndex];
        if (board.validateMove(player, targetMove)) {
            return targetMove;
        } else {
            return null;
        }
    }

    /**
     * Read player input from keyboard.
     *
     * @param board    the game board
     * @param player   the current player
     * @param keyboard
     * @return the valid move
     */
    private static String nextValidMoveFromUser(Board board, char player, Scanner keyboard) {
        String userMove = null;
        do {
            System.out.println(board.getBoardInfo());
            System.out.printf("Player %s move: ", player);
            userMove = keyboard.next();
        } while (!board.validateMove(player, userMove));
        return userMove;
    }

    public static void main(String[] args) {
        if (args != null && args.length >= 1) {
            System.out.println(Othello.playGame(args[0]));
        } else {
            Othello.playGame();
        }
    }

}


