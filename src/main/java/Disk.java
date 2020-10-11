import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * This class represents a disk on game board.
 */
public class Disk {

    /**
     * The row number  on the board (from 0).
     */
    private int x;

    /**
     * The column number on the board (from 0).
     */
    private int y;

    public Disk(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Disk() {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public Disk nextDisk(Disk disk) {
        return new Disk(this.x + disk.getX(), this.y + disk.getY());
    }

    public void move(Disk disk) {
        this.x += disk.getX();
        this.y += disk.getY();
    }

    /**
     * Represent all movements for eight directions.
     */
    public static final Disk[] ALL_DIRECTIONS = new Disk[]{new Disk(1, 0),
            new Disk(1, 1),
            new Disk(0, 1),
            new Disk(-1, 1),
            new Disk(-1, 0),
            new Disk(-1, -1),
            new Disk(0, -1),
            new Disk(1, -1),
    };


    /**
     * Key is the coordinate (eg: 1a 3b)  and value is the disk on the board.<p>
     * For example, '4c' and 'c4' represents the disk in the fourth row  and third column on the game board.
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
    private static final HashMap<String, Disk> ALL_DISKS = new LinkedHashMap<>();

    /**
     * Initialize ALL_DISKS
     */
    static {
        for (int row = 1; row <= 8; row++) {
            for (char column = 'a'; column <= 'h'; ++column) {
                Disk p = new Disk(row - 1, column - 'a');
                ALL_DISKS.put(row + String.valueOf(column), p);
                ALL_DISKS.put(String.valueOf(column) + row, p);
            }
        }
    }


    public static String getUserMove(Disk disk) {
        for (Map.Entry<String, Disk> entry : ALL_DISKS.entrySet()) {
            if (disk.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;

    }

    /**
     * Return the disk on the game board
     * @param playerInput the coordinates from player
     * @return  the disk on game board
     */
    public static Disk getDisk(String playerInput) {
        return ALL_DISKS.get(playerInput.trim().toLowerCase());
    }

    /**
     * Return all disks on the game board.
     * @return  all disks on the game board
     */
    public static List<Disk> getAllDisksOnBoard() {
        return ALL_DISKS.entrySet().stream()
                .filter(entry -> Character.isDigit(entry.getKey().charAt(0)))
                .map(Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Disk{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disk disk = (Disk) o;
        return x == disk.x &&
                y == disk.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}