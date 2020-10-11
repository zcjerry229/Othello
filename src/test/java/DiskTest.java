import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class DiskTest {

    @Test
    public void nextDisk() {
        for (Disk direction : Disk.ALL_DIRECTIONS) {
            Disk disk = new Disk(0, 0);
            disk = disk.nextDisk(direction);
            assertEquals(disk.getX(), direction.getX());
            assertEquals(disk.getY(), direction.getY());
        }
    }


    @Test
    public void move() {
        for (Disk direction : Disk.ALL_DIRECTIONS) {
            Disk disk = new Disk(0, 0);
            disk.move(direction);
            assertEquals(disk.getX(), direction.getX());
            assertEquals(disk.getY(), direction.getY());
        }
    }

    @Test
    public void getDisk() {
        assertEquals(Disk.getDisk("1a"), new Disk(0, 0));
        assertEquals(Disk.getDisk("a1"), new Disk(0, 0));
        assertEquals(Disk.getDisk("8h"), new Disk(7, 7));
        assertEquals(Disk.getDisk("h8"), new Disk(7, 7));
        assertEquals(Disk.getDisk("aa"), null);
    }
}
