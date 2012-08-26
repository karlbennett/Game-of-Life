package org.karlbennett.gameoflife;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * User: karl
 * Date: 16/08/12
 */
public class CellTest {

    private static final int[] coordinates_0 = {0};
    private static final int[] coordinates_0_0 = {0, 0};
    private static final int[] coordinates_0_0_0 = {0, 0, 0};
    private static final int[] coordinates_1 = {1};
    private static final int[] coordinates_1_1 = {1, 1};
    private static final int[] coordinates_1_1_1 = {1, 1, 1};
    private static final int[] coordinates_1_2_3 = {1, 2, 3};

    private static final Cell<Coordinates, Rule<Coordinates>> _n1_n1 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(-1, -1), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _0_n1 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(0, -1), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _1_n1 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(1, -1), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _n1_0 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(-1, 0), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _0_0 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(0, 0), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _1_0 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(1, 0), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _n1_1 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(-1, 1), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _0_1 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(0, 1), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _1_1 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(1, 1), null, 0);

    private static final int index_n1_n1 = 0;
    private static final int index_0_n1 = 1;
    private static final int index_1_n1 = 2;
    private static final int index_n1_0 = 3;
    private static final int index_1_0 = 4;
    private static final int index_n1_1 = 5;
    private static final int index_0_1 = 6;
    private static final int index_1_1 = 7;

    private static final List<Cell<Coordinates, Rule<Coordinates>>> NEIGHBOURS = Collections.unmodifiableList(
            Arrays.asList(_n1_n1, _0_n1, _1_n1, _n1_0, _1_0, _n1_1, _0_1, _1_1)
    );


    @Test
    public void testNeighbourNumber() throws Exception {

        assertEquals("neighbour number for 0 dimensions should be correct", 0, Cell.neighbourNumber(0));
        assertEquals("neighbour number for 1 dimensions should be correct", 2, Cell.neighbourNumber(1));
        assertEquals("neighbour number for 2 dimensions should be correct", 8, Cell.neighbourNumber(2));
        assertEquals("neighbour number for 3 dimensions should be correct", 26, Cell.neighbourNumber(3));
    }

    @Test
    public void testBuildCoordinateOffset() throws Exception {

        assertArrayEquals("offset for zero dimension cell should be {}", new int[0], Cell.buildCoordinateOffset(0));
        assertArrayEquals("offset for one dimension cell should be {1}", new int[]{1}, Cell.buildCoordinateOffset(1));
        assertArrayEquals("offset for two dimension cell should be {1, 1}", new int[]{1, 1}, Cell.buildCoordinateOffset(2));
        assertArrayEquals("offset for three dimension cell should be {1, 1, 1}", new int[]{1, 1, 1}, Cell.buildCoordinateOffset(3));
    }

    @Test
    public void testCalculateOffsetCoordinates() throws Exception {

        assertArrayEquals("offset {0} against coordinates {0} should produce {0}", coordinates_0, Cell.calculateOffsetCoordinates(coordinates_0, 0));
        assertArrayEquals("offset {0, 0} against coordinates {0, 0} should produce {0, 0}", coordinates_0_0, Cell.calculateOffsetCoordinates(coordinates_0_0, 0, 0));
        assertArrayEquals("offset {0, 0, 0} against coordinates {0, 0, 0} should produce {0, 0, 0}", coordinates_0_0_0, Cell.calculateOffsetCoordinates(coordinates_0_0_0, 0, 0, 0));
        assertArrayEquals("offset {0, 0, 0} against coordinates {1, 1, 1} should produce {1, 1, 1}", coordinates_1_1_1, Cell.calculateOffsetCoordinates(coordinates_0_0_0, 1, 1, 1));
        assertArrayEquals("offset {1} against coordinates {0} should produce {1}", coordinates_1, Cell.calculateOffsetCoordinates(coordinates_1, 0));
        assertArrayEquals("offset {1, 1} against coordinates {0, 0} should produce {1, 1}", coordinates_1_1, Cell.calculateOffsetCoordinates(coordinates_1_1, 0, 0));
        assertArrayEquals("offset {1, 1, 1} against coordinates {0, 0, 0} should produce {1, 1, 1}", coordinates_1_1_1, Cell.calculateOffsetCoordinates(coordinates_1_1_1, 0, 0, 0));
        assertArrayEquals("offset {1, 2, 3} against coordinates {0, 0, 0} should produce {1, 2, 3}", coordinates_1_2_3, Cell.calculateOffsetCoordinates(coordinates_1_2_3, 0, 0, 0));
        assertArrayEquals("offset {1, 2, 3} against coordinates {1, 1, 1} should produce {2, 3, 4}", new int[]{2, 3, 4}, Cell.calculateOffsetCoordinates(coordinates_1_2_3, 1, 1, 1));
        assertArrayEquals("offset {1, 2, 3} against coordinates {3, 2, 1} should produce {4, 4, 4}", new int[]{4, 4, 4}, Cell.calculateOffsetCoordinates(coordinates_1_2_3, 3, 2, 1));
        assertArrayEquals("offset {1, 2, 3} against coordinates {-1, -1, -1} should produce {0, 1, 2}", new int[]{0, 1, 2}, Cell.calculateOffsetCoordinates(coordinates_1_2_3, -1, -1, -1));
    }

    @Test(expected = IllegalStateException.class)
    public void testCalculateOffsetCoordinatesWithInvalidCoordinateLength() throws Exception {

        Cell.calculateOffsetCoordinates(coordinates_1_2_3, -1, -1);
    }

    @Test
    public void testCalculateIndex() throws Exception {

        assertEquals("coordinates (0) should produce index 0", 0, Cell.calculateIndex(0));
        assertEquals("coordinates (0,0) should produce index 0", 0, Cell.calculateIndex(0, 0));
        assertEquals("coordinates (0,0,0)  should produce index 0", 0, Cell.calculateIndex(0, 0, 0));
        assertEquals("coordinates (1)  should produce index 1", 1, Cell.calculateIndex(1));
        assertEquals("coordinates (1,1)  should produce index 4", 4, Cell.calculateIndex(1, 1));
        assertEquals("coordinates (1,1,1)  should produce index 13", 13, Cell.calculateIndex(1, 1, 1));
        assertEquals("coordinates (1,1,1,1)  should produce index 40", 40, Cell.calculateIndex(1, 1, 1, 1));
        assertEquals("coordinates (1,2)  should produce index 7", 7, Cell.calculateIndex(1, 2));
        assertEquals("coordinates (1,2,3)  should produce index 34", 34, Cell.calculateIndex(1, 2, 3));
        assertEquals("coordinates (1,2,3,4)  should produce index 142", 142, Cell.calculateIndex(1, 2, 3, 4));
    }

    @Test
    public void testAdjustForIndex() throws Exception {

        Cell cell0D = new Cell(null, null, 0);
        Cell cell1D = new Cell(null, null, 1);
        Cell cell2D = new Cell(null, null, 2);
        Cell cell3D = new Cell(null, null, 3);

        assertEquals("index of 0 should be adjusted to -1 for 0 dimensional cell", -1, cell0D.adjustForCellIndex(0));
        assertEquals("index of 2 should be adjusted to 1 for 1 dimensional cell", 1, cell1D.adjustForCellIndex(2));
        assertEquals("index of 5 should be adjusted to 4 for 2 dimensional cell", 4, cell2D.adjustForCellIndex(5));
        assertEquals("index of 14 should be adjusted to 13 for 3 dimensional cell", 13, cell3D.adjustForCellIndex(14));
    }

    @Test
    public void testGetNextStateWithNullRules() throws Exception {

        Cell<Boolean, Rule<Boolean>> cell = new Cell<Boolean, Rule<Boolean>>(true, null, 0);

        assertTrue("next state with no rules should be true", cell.getNextState());
    }

    @Test
    public void testGetNextStateWithOneRule() throws Exception {

        Rule<Boolean> rule = new Rule<Boolean>() {

            @Override
            public <R extends Rule<Boolean>> Boolean apply(Cell<Boolean, R> cell) {

                return !cell.getState();
            }
        };

        Cell<Boolean, Rule<Boolean>> cell = new Cell<Boolean, Rule<Boolean>>(
                true,
                Collections.singletonList(rule),
                0);

        assertFalse("next state with one rule should be false", cell.getNextState());
    }

    @Test
    public void testGetNextStateWithMultipleRules() throws Exception {

        Rule<Boolean> rule1 = new Rule<Boolean>() {

            @Override
            public <R extends Rule<Boolean>> Boolean apply(Cell<Boolean, R> cell) {

                return cell.getState();
            }
        };

        Rule<Boolean> rule2 = new Rule<Boolean>() {

            @Override
            public <R extends Rule<Boolean>> Boolean apply(Cell<Boolean, R> cell) {

                return !cell.getState();
            }
        };

        Rule<Boolean> rule3 = new Rule<Boolean>() {

            @Override
            public <R extends Rule<Boolean>> Boolean apply(Cell<Boolean, R> cell) {

                return cell.getState();
            }
        };

        Cell<Boolean, Rule<Boolean>> cell = new Cell<Boolean, Rule<Boolean>>(
                true,
                Arrays.asList(rule1, rule2, rule3),
                0);

        assertFalse("next state with multiple rules should be false", cell.getNextState());
    }

    @Test
    public void testGetNeighbour() throws Exception {

        Cell<Coordinates, Rule<Coordinates>> cell = new Cell<Coordinates, Rule<Coordinates>>(_0_0.getState(), null,
                2, NEIGHBOURS);

        assertEquals("cell set to coordinate (-1,-1) should be  correct", _n1_n1.getState(), cell.getNeighbour(-1, -1).getState());
        assertEquals("cell set to coordinate (0,-1) should be correct", _0_n1.getState(), cell.getNeighbour(0, -1).getState());
        assertEquals("cell set to coordinate (1,-1) should be correct", _1_n1.getState(), cell.getNeighbour(1, -1).getState());
        assertEquals("cell set to coordinate (-1,0) should be correct", _n1_0.getState(), cell.getNeighbour(-1, 0).getState());
        assertEquals("cell set to coordinate (1,0) should be correct", _1_0.getState(), cell.getNeighbour(1, 0).getState());
        assertEquals("cell set to coordinate (-1,1) should be correct", _n1_1.getState(), cell.getNeighbour(-1, 1).getState());
        assertEquals("cell set to coordinate (0,1) should be correct", _0_1.getState(), cell.getNeighbour(0, 1).getState());
        assertEquals("cell set to coordinate (1,1) should be correct", _1_1.getState(), cell.getNeighbour(1, 1).getState());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNeighbourWithInvalidCoordinates() throws Exception {

        Cell<Coordinates, Rule<Coordinates>> cell = new Cell<Coordinates, Rule<Coordinates>>(
                _0_0.getState(), null, 2);

        cell.getNeighbour(0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNeighbourWithTooManyDimensions() throws Exception {

        Cell<Coordinates, Rule<Coordinates>> cell = new Cell<Coordinates, Rule<Coordinates>>(
                _0_0.getState(), null, 2);

        cell.getNeighbour(0, 1, 2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetNeighbourWithTooHighCoordinates() throws Exception {

        Cell<Coordinates, Rule<Coordinates>> cell = new Cell<Coordinates, Rule<Coordinates>>(
                _0_0.getState(), null, 2);

        cell.getNeighbour(0, 3);
    }

    @Test
    public void testSetNeighbour() throws Exception {

        Cell<Coordinates, Rule<Coordinates>> cell = new Cell<Coordinates, Rule<Coordinates>>(
                _0_0.getState(), null, 2);

        cell.setNeighbour(_n1_n1, -1, -1);
        assertEquals("cell at coordinate (-1,-1) should be  correct", _n1_n1.getState(),
                cell.getNeighbours().get(index_n1_n1).getState());

        cell.setNeighbour(_0_n1, 0, -1);
        assertEquals("cell at coordinate (0,-1) should be  correct", _0_n1.getState(),
                cell.getNeighbours().get(index_0_n1).getState());

        cell.setNeighbour(_1_n1, 1, -1);
        assertEquals("cell at coordinate (1,-1) should be  correct", _1_n1.getState(),
                cell.getNeighbours().get(index_1_n1).getState());

        cell.setNeighbour(_n1_0, -1, 0);
        assertEquals("cell at coordinate (-1,0) should be  correct", _n1_0.getState(),
                cell.getNeighbours().get(index_n1_0).getState());

        cell.setNeighbour(_1_0, 1, 0);
        assertEquals("cell at coordinate (1,0) should be  correct", _1_0.getState(),
                cell.getNeighbours().get(index_1_0).getState());

        cell.setNeighbour(_n1_1, -1, 1);
        assertEquals("cell at coordinate (-1,1) should be  correct", _n1_1.getState(),
                cell.getNeighbours().get(index_n1_1).getState());

        cell.setNeighbour(_0_1, 0, 1);
        assertEquals("cell at coordinate (0,1) should be  correct", _0_1.getState(),
                cell.getNeighbours().get(index_0_1).getState());

        cell.setNeighbour(_1_1, 1, 1);
        assertEquals("cell at coordinate (1,1) should be  correct", _1_1.getState(),
                cell.getNeighbours().get(index_1_1).getState());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNeighbourWithInvalidCoordinates() throws Exception {

        Cell<Coordinates, Rule<Coordinates>> cell = new Cell<Coordinates, Rule<Coordinates>>(
                _0_0.getState(), null, 2);

        cell.setNeighbour(null, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNeighbourWithTooManyDimensions() throws Exception {

        Cell<Coordinates, Rule<Coordinates>> cell = new Cell<Coordinates, Rule<Coordinates>>(
                _0_0.getState(), null, 2);

        cell.setNeighbour(null, 0, 1, 2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetNeighbourWithTooHighCoordinates() throws Exception {

        Cell<Coordinates, Rule<Coordinates>> cell = new Cell<Coordinates, Rule<Coordinates>>(
                _0_0.getState(), null, 2);

        cell.setNeighbour(null, 0, 3);
    }

    private static class Coordinates implements Comparable<Coordinates> {

        public final int[] coordinates;


        private Coordinates(int ...coordinates) {

            this.coordinates = coordinates;
        }

        @Override
        public int compareTo(Coordinates other) {

            // If the supplied coordinates have more dimensions then they are greater than the current ones.
            if (this.coordinates.length < other.coordinates.length) return -1;

            // If the supplied coordinates have less dimensions then they are less than the current ones.
            if (this.coordinates.length > other.coordinates.length) return -1;

            int total = 0;
            int othertotal = 0;
            for (int i = 0; i < this.coordinates.length; i++) {

                total += this.coordinates[i] * (Math.pow(10, i));
                othertotal += other.coordinates[i] * (Math.pow(10, i));
            }

            return total - othertotal;
        }

        @Override
        public boolean equals(Object other) {

            if (this == other) return true;

            if (other == null || getClass() != other.getClass()) return false;

            Coordinates that = (Coordinates) other;

            return Arrays.equals(coordinates, that.coordinates);
        }

        @Override
        public int hashCode() {

            return Arrays.hashCode(coordinates);
        }
    }
}
