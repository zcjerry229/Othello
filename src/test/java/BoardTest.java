import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

import static org.junit.Assert.*;

/**
 * Board Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Oct 10, 2020</pre>
 */
public class BoardTest {

    private void testHasAvailableMove(char player, Board board, String[] expected) {
        List<Disk> allValidMoves = board.updateAvailableMoves().get(player);
        List<Disk> expectedDisks = new ArrayList<>();
        for (String coordinate : expected) {
            expectedDisks.add(Disk.getDisk(coordinate));
        }
        assertEquals(allValidMoves, expectedDisks);
    }

    @Test
    public void testHasAvailableMove() throws Exception {
        Board board = new Board();
        testHasAvailableMove(Board.DARK, board, new String[]{"3d", "4c", "5f", "6e"});
        board.flip(Board.DARK, Disk.getDisk("3d"));
        testHasAvailableMove(Board.LIGHT, board, new String[]{"3c", "3e", "5c"});
        board.flip(Board.LIGHT, Disk.getDisk("5c"));
        testHasAvailableMove(Board.DARK, board, new String[]{"6b", "6c", "6d", "6e", "6f"});
    }

    /**
     * Method: validateMove(char turn, Disk targetDisk, Map<Character, List<Disk>> allAvailableMoves)
     */
    @Test
    public void testValidateMove() throws Exception {
        Board board = new Board();
        assertFalse(board.validateMove(Board.DARK, "aa"));
        assertTrue(board.validateMove(Board.DARK, "3d"));
        assertFalse(board.validateMove(Board.LIGHT, "aa"));

    }

    /**
     * Method: flip(char player, Disk disk)
     */
    @Test
    public void testFlip() throws Exception {
        Board board = new Board();
        board.flip(Board.DARK, Disk.getDisk("3d"));
        String expected = "1 - - - - - - - - \n" +
                "2 - - - - - - - - \n" +
                "3 - - - X - - - - \n" +
                "4 - - - X X - - - \n" +
                "5 - - - X O - - - \n" +
                "6 - - - - - - - - \n" +
                "7 - - - - - - - - \n" +
                "8 - - - - - - - - \n" +
                "  a b c d e f g h\n";
        assertEquals(board.getBoardInfo(), expected);
    }

    /**
     * Method: isGameOver(Map<Character, List<Disk>> allAvailableMoves)
     */
    @Test
    public void testIsGameOver() throws Exception {
        Board board = new Board();
        assertFalse(board.isGameOver());
    }

    /**
     * Method: getOpponent(char currentPlayer)
     */
    @Test
    public void testGetOpponent() throws Exception {
        assertEquals(Board.getOpponent(Board.DARK), Board.LIGHT);
        assertEquals(Board.getOpponent(Board.LIGHT), Board.DARK);
    }


    /**
     * Method: getPlayer(Disk disk)
     */
    @Test
    public void testGetPlayer() throws Exception {
        Board board = new Board();
        assertEquals(board.getPlayer(new Disk(3, 3)), Board.LIGHT);
        assertEquals(board.getPlayer(new Disk(3, 4)), Board.DARK);
        assertEquals(board.getPlayer(new Disk(5, 5)), Board.NO_PIECE);
        assertEquals(board.getPlayer(new Disk(9, 9)), Board.NO_PIECE);
    }



    /**
     * Method: isDiskInBoard(Disk disk)
     */
    @Test
    public void testIsDiskInBoard() throws Exception {
        assertTrue(Board.isDiskInBoard(new Disk(0, 0)));
        assertTrue(Board.isDiskInBoard(new Disk(7, 7)));
        assertFalse(Board.isDiskInBoard(new Disk(8, 8)));

    }

} 
