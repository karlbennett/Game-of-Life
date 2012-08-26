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
                0);
        cell.setNeighbours(NEIGHBOURS);

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
                cell.getNeighbours().get(index_n1_n1));

        cell.setNeighbour(_0_n1, 0, -1);
        assertEquals("cell at coordinate (0,-1) should be  correct", _0_n1.getState(),
                cell.getNeighbours().get(index_0_n1));

        cell.setNeighbour(_1_n1, 1, -1);
        assertEquals("cell at coordinate (1,-1) should be  correct", _1_n1.getState(),
                cell.getNeighbours().get(index_1_n1));

        cell.setNeighbour(_n1_0, -1, 0);
        assertEquals("cell at coordinate (-1,0) should be  correct", _n1_0.getState(),
                cell.getNeighbours().get(index_n1_0));

        cell.setNeighbour(_1_0, 1, 0);
        assertEquals("cell at coordinate (1,0) should be  correct", _1_0.getState(),
                cell.getNeighbours().get(index_1_0));

        cell.setNeighbour(_n1_1, -1, 1);
        assertEquals("cell at coordinate (-1,1) should be  correct", _n1_1.getState(),
                cell.getNeighbours().get(index_n1_1));

        cell.setNeighbour(_0_1, 0, 1);
        assertEquals("cell at coordinate (0,1) should be  correct", _0_1.getState(),
                cell.getNeighbours().get(index_0_1));

        cell.setNeighbour(_1_1, 1, 1);
        assertEquals("cell at coordinate (1,1) should be  correct", _1_1.getState(),
                cell.getNeighbours().get(index_1_1));
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
