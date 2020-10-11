import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Othello Tester.
 *
 * @author Zhang， Chen
 */
@RunWith(Parameterized.class)
public class OthelloTest {
    private String moves;
    private String expectedResult;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        String moves_1 = "";
        String expectedResult_1 = "1 - - - - - - - - \n" +
                "2 - - - - - - - - \n" +
                "3 - - - - - - - - \n" +
                "4 - - - O X - - - \n" +
                "5 - - - X O - - - \n" +
                "6 - - - - - - - - \n" +
                "7 - - - - - - - - \n" +
                "8 - - - - - - - - \n" +
                "  a b c d e f g h\n";

        String moves_2 = "f5, 6f, f7, 4f, f3, 3e, d3, c5";
        String expectedResult_2 = "1 - - - - - - - - \n" +
                "2 - - - - - - - - \n" +
                "3 - - - X X X - - \n" +
                "4 - - - X X X - - \n" +
                "5 - - O O O X - - \n" +
                "6 - - - - - X - - \n" +
                "7 - - - - - X - - \n" +
                "8 - - - - - - - - \n" +
                "  a b c d e f g h\n";

        String moves_3 = "4c, 3e, 2f, 5c, 6f, 3b, 6d, " +
                "7e, 4f, 6e, 7d, 6g, 2a, 6c, 6b, 7c, 7g, 2e, 6h, 3d, 8d, 3c, 7f, " +
                "7a, 2d, 5f, 3f, 3g, 6a, 8c, 2g, 8h, 5g, 1f, 7h, 1g, 8b, 4g, 3h, 1c, " +
                "5b, 5h, 1h, 4b, 2c, 8e, 5a, 8a, 1e, 2b, 1a, 1d, 8f, 4h, 1b, 2h, 3a, 4a, 8g, 7b";
        String expectedResult_3 = "1 X X X X X X X X \n" +
                "2 X X X O O O O O \n" +
                "3 X X O O O O O O \n" +
                "4 X X X O O O O O \n" +
                "5 X X O O X X O O \n" +
                "6 X X O O O O O O \n" +
                "7 O O O O O O O O \n" +
                "8 O O O O O O O O \n" +
                "  a b c d e f g h\n";

        return Arrays.asList(new Object[][]{
                {moves_1, expectedResult_1},
                {moves_2, expectedResult_2},
                {moves_3, expectedResult_3}


        });
    }

    public OthelloTest(String moves, String expected) {
        this.moves = moves;
        this.expectedResult = expected;
    }

    /**
     * Method: playGame()
     */
    @Test
    public void test() {
        assertEquals(expectedResult, Othello.playGame(moves));
    }
} 
