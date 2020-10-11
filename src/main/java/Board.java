import java.util.*;

/**
 * This class represents Othello game board.
 *
 * <p>For each disk:
 * <UL>
 * <LI>'<code>X</code>' for a disk with DARK
 * <LI>'<code>O</code>' for a disk with LIGHT
 * <LI>'<code>-</code>' for an empty location
 * </ul>
 */
public class Board {

    /* The value to represent empty disk. */
    public static final char NO_PIECE = '-';

    /* The value to represent DARK player */
    public static final char DARK = 'X';

    /* The value to represent LIGHT player */
    public static final char LIGHT = 'O';

    /* Size of the board. */
    public static final int BOARD_SIZE = 8;

    /**
     * The map to keep all the available moves for both players.<p>
     * Key is the player and the value (List) contains
     * all available moves for the player.
     */
    private Map<Character, List<Disk>> allAvailableMoves;

    /* the game history*/
    private List<String> history;

    /* The array to keep the status for each disk on the board */
    private char disks[][];


    /**
     * This method creates a board with 8*8 disks and game starts like belows:
     * <pre>
     * 1 - - - - - - - -
     * 2 - - - - - - - -
     * 3 - - - - - - - -
     * 4 - - - O X - - -
     * 5 - - - X O - - -
     * 6 - - - - - - - -
     * 7 - - - - - - - -
     * 8 - - - - - - - -
     *   a b c d e f g h
     * </pre>
     */
    public Board() {
        this.history = new ArrayList<>();
        allAvailableMoves = new HashMap<>();
        allAvailableMoves.put(DARK, new ArrayList<>());
        allAvailableMoves.put(LIGHT, new ArrayList<>());

        disks = new char[BOARD_SIZE][BOARD_SIZE];
        for (char[] chars : disks) {
            Arrays.fill(chars, NO_PIECE);
        }
        disks[3][3] = LIGHT;
        disks[3][4] = DARK;
        disks[4][3] = DARK;
        disks[4][4] = LIGHT;
        this.updateAvailableMoves();
    }


    /**
     * Return current status of board.
     *
     * @return The current result of board as String.
     */
    public String getBoardInfo() {
        StringBuffer boardInfo = new StringBuffer();

        for (int i = 0; i < disks.length; i++) {
            boardInfo.append(i + 1).append(" ");
            for (char disk : disks[i]) {
                boardInfo.append(disk).append(" ");
            }
            boardInfo.append(System.lineSeparator());
        }
        boardInfo.append("  a b c d e f g h").append(System.lineSeparator());
        return boardInfo.toString();
    }

    /**
     * Return true if there is any possible valid move for given player on current board.
     *
     * @param player the current player
     * @return true if there is any possible valid move for given player on current board.
     */
    public boolean hasAvailableMove(char player) {
        return allAvailableMoves.get(player).size() > 0;
    }

    /**
     * @param player the current player
     * @param move   the player's move
     * @return true if the move is valid on current board
     */
    public boolean validateMove(char player, String move) {
        Disk targetDisk = Disk.getDisk(move);
        if (null != targetDisk && getPlayer(targetDisk) == NO_PIECE
                && allAvailableMoves.get(player).contains(targetDisk)) {
            return true;
        } else {
            System.out.printf("Invalid move: %s !  %n", move);
            return false;
        }

    }

    /**
     * Turn over the single disk (or chain of opponent's disks)
     * on the line (eight directions) between the new disk and an anchoring disk.
     * <p>
     * see Rules on  <a href="https://en.wikipedia.org/wiki/Reversi">https://en.wikipedia.org/wiki/Reversi</a>
     *
     * @param player the current player
     * @param disk   the disk to put by current player
     */
    public void flip(char player, Disk disk) {
        char nextPlayOnBoard;
        Disk nextDisk;
        setPlayer(player, disk);
        addHistory(player, disk);
        for (Disk direction : Disk.ALL_DIRECTIONS) {
            if (canFlip(player, disk, direction)) {
                nextDisk = disk.nextDisk(direction);
                while (true) {
                    nextPlayOnBoard = getPlayer(nextDisk);
                    if (nextPlayOnBoard == NO_PIECE || nextPlayOnBoard == player) {
                        break;
                    } else {
                        setPlayer(player, nextDisk);
                    }
                    nextDisk.move(direction);
                }
            }
        }
        this.updateAvailableMoves();
    }

    /**
     * Return if the game can be ended.
     * The game ends when either<pre>
     * 1. Neither player can make a valid move
     * 2. The board is full
     *
     * @return true if the game if over.
     */
    public boolean isGameOver() {
        int countOccupiedDisk = 0;
        boolean isGameOver = false;
        for (Disk disk : Disk.getAllDisksOnBoard()) {
            char player = getPlayer(disk);
            if (player == DARK || player == LIGHT) {
                countOccupiedDisk++;
            }
        }
        if (countOccupiedDisk == BOARD_SIZE * BOARD_SIZE ||
                allAvailableMoves.get(DARK).size() == 0 && allAvailableMoves.get(LIGHT).size() == 0) {
            System.out.println("No further moves available");
            isGameOver = true;
        }
        return isGameOver;
    }

    /**
     * Update and return all available moves for both players on current board
     *
     * @return all available moves for both players on current board
     */
    public Map<Character, List<Disk>> updateAvailableMoves() {
        allAvailableMoves.get(DARK).clear();
        allAvailableMoves.get(LIGHT).clear();

        for (Disk disk : Disk.getAllDisksOnBoard()) {
            //No need to check occupied disk
            if (getPlayer(disk) != NO_PIECE) {
                continue;
            }
            for (char player : Arrays.asList(DARK, LIGHT)) {
                if (canFlip(player, disk)) {
                    allAvailableMoves.get(player).add(disk);
                }
            }
        }
        return allAvailableMoves;
    }

    /***
     *  Return true if the disk for current player can flip in one of eight directions
     * @param player the current player
     * @param disk the disk to check
     * @return true if the disk for current player can flip in one of eight directions
     */
    public boolean canFlip(char player, Disk disk) {
        for (Disk direction : Disk.ALL_DIRECTIONS) {
            if (canFlip(player, disk, direction)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Return true if the current player can flip any opponent's disk under <code>direction</code>
     *
     * @param player    the current player
     * @param disk      the disk to check
     * @param direction the direction to check
     * @return true if the current player can flip any opponent's disk
     */
    private boolean canFlip(char player, Disk disk, Disk direction) {
        char next_player;
        boolean nextDiskIsOpponent = false;
        Disk nextDisk = disk.nextDisk(direction);
        while (isDiskInBoard(nextDisk)) {
            next_player = getPlayer(nextDisk);
            if (next_player == NO_PIECE) {
                return false;
            } else if (next_player == Board.getOpponent(player)) {
                nextDiskIsOpponent = true;
            } else if (next_player == player) {
                return nextDiskIsOpponent;
            }
            nextDisk.move(direction);
        }
        return false;
    }

    /**
     * Return the opponent
     *
     * @param player the current player
     * @return the opponent
     */
    public static char getOpponent(char player) {
        return (player == Board.DARK) ? Board.LIGHT : Board.DARK;
    }

    /**
     * End the game and print the result.
     */
    public void endGame() {
        String boardInfo = this.getBoardInfo();
        long count_dark = boardInfo.chars().filter(ch -> ch == DARK).count();
        long count_light = boardInfo.chars().filter(ch -> ch == LIGHT).count();
        if (count_dark != count_light) {
            char winner = count_dark > count_light ? DARK : LIGHT;
            System.out.printf("Game over. Player '%s' wins.", winner);
        } else {
            System.out.print("The game ends in a tie.");
        }
        System.out.printf(" (%s vs %s) %n", Math.max(count_dark, count_light),
                Math.min(count_dark, count_light));
    }


    /**
     * Reture true if the coordinate of disk is in current board.
     *
     * @param disk the disk to check
     * @return true if the coordinate of disk is within current board.
     */
    public static boolean isDiskInBoard(Disk disk) {
        if (null != disk) {
            int x = disk.getX();
            int y = disk.getY();
            return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
        }
        return false;
    }

    /**
     * Put player on disk
     *
     * @param player the player to set up
     * @param disk   the disk to set up
     */
    public void setPlayer(char player, Disk disk) {
        if (isDiskInBoard(disk)) {
            disks[disk.getX()][disk.getY()] = player;
        }
    }

    /**
     * Record the history of game
     *
     * @param player the current player
     * @param disk   the current disk to move
     * @return history of the game
     */
    public List<String> addHistory(char player, Disk disk) {

        String userMove = Disk.getUserMove(disk);
        if (null != userMove) {
            this.history.add(player + "-" + userMove);
        }
        return this.history;
    }

    /**
     * Return the player on the disk
     *
     * @param disk the disk
     * @return the player on the disk.
     */
    public char getPlayer(Disk disk) {
        if (isDiskInBoard(disk)) {
            return disks[disk.getX()][disk.getY()];
        } else {
            return NO_PIECE;
        }
    }

}