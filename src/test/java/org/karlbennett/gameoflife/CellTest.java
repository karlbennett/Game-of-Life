package org.karlbennett.gameoflife;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * User: karl
 * Date: 16/08/12
 */
public class CellTest {

    private static final int[] COORDINATES_0 = {0};
    private static final int[] COORDINATES_0_0 = {0, 0};
    private static final int[] COORDINATES_0_0_0 = {0, 0, 0};
    private static final int[] COORDINATES_0_0_0_0 = {0, 0, 0, 0};
    private static final int[] COORDINATES_1 = {1};
    private static final int[] COORDINATES_1_1 = {1, 1};
    private static final int[] COORDINATES_1_1_1 = {1, 1, 1};
    private static final int[] COORDINATES_1_1_1_1 = {1, 1, 1, 1};
    private static final int[] COORDINATES_1_2_3 = {1, 2, 3};

    private static final Cell CELL_0D = new Cell(null, null, 0);
    private static final Cell CELL_1D = new Cell(null, null, 1);
    private static final Cell CELL_2D = new Cell(null, null, 2);
    private static final Cell CELL_3D = new Cell(null, null, 3);
    private static final Cell CELL_4D = new Cell(null, null, 4);

    private static final Cell<Coordinates, Rule<Coordinates>> _N1_N1 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(-1, -1), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _0_N1 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(0, -1), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _1_N1 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(1, -1), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _N1_0 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(-1, 0), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _0_0 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(0, 0), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _1_0 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(1, 0), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _N1_1 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(-1, 1), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _0_1 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(0, 1), null, 0);
    private static final Cell<Coordinates, Rule<Coordinates>> _1_1 = new Cell<Coordinates, Rule<Coordinates>>(
            new Coordinates(1, 1), null, 0);

    private static final int INDEX_N1_N1 = 0;
    private static final int INDEX_0_N1 = 1;
    private static final int INDEX_1_N1 = 2;
    private static final int INDEX_N1_0 = 3;
    private static final int INDEX_1_0 = 4;
    private static final int INDEX_N1_1 = 5;
    private static final int INDEX_0_1 = 6;
    private static final int INDEX_1_1 = 7;

    private static final List<Cell<Coordinates, Rule<Coordinates>>> NEIGHBOURS = Collections.unmodifiableList(
            Arrays.asList(_N1_N1, _0_N1, _1_N1, _N1_0, _1_0, _N1_1, _0_1, _1_1)
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

    @Test(expected = IndexOutOfBoundsException.class)
    public void testStaticCheckNeighbourCoordinatesWithInvalidCoordinateValues() throws Exception {

        Cell.checkNeighbourCoordinates(1, new int[]{2});
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testInstanceCheckNeighbourCoordinatesWithInvalidCoordinateValues() throws Exception {

        new Cell(null, null, 1).checkNeighbourCoordinates(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStaticCheckNeighbourCoordinatesWithInvalidCoordinateNumber() throws Exception {

        Cell.checkNeighbourCoordinates(1, new int[]{0, 0});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInstanceCheckNeighbourCoordinatesWithInvalidCoordinateNumber() throws Exception {

        new Cell(null, null, 1).checkNeighbourCoordinates(0, 0);
    }

    @Test
    public void testCalculateOffsetCoordinates() throws Exception {

        assertArrayEquals("offset {0} against coordinates {0} should produce {0}", COORDINATES_0, Cell.calculateOffsetCoordinates(COORDINATES_0, 0));
        assertArrayEquals("offset {0, 0} against coordinates {0, 0} should produce {0, 0}", COORDINATES_0_0, Cell.calculateOffsetCoordinates(COORDINATES_0_0, 0, 0));
        assertArrayEquals("offset {0, 0, 0} against coordinates {0, 0, 0} should produce {0, 0, 0}", COORDINATES_0_0_0, Cell.calculateOffsetCoordinates(COORDINATES_0_0_0, 0, 0, 0));
        assertArrayEquals("offset {0, 0, 0} against coordinates {1, 1, 1} should produce {1, 1, 1}", COORDINATES_1_1_1, Cell.calculateOffsetCoordinates(COORDINATES_0_0_0, 1, 1, 1));
        assertArrayEquals("offset {1} against coordinates {0} should produce {1}", COORDINATES_1, Cell.calculateOffsetCoordinates(COORDINATES_1, 0));
        assertArrayEquals("offset {1, 1} against coordinates {0, 0} should produce {1, 1}", COORDINATES_1_1, Cell.calculateOffsetCoordinates(COORDINATES_1_1, 0, 0));
        assertArrayEquals("offset {1, 1, 1} against coordinates {0, 0, 0} should produce {1, 1, 1}", COORDINATES_1_1_1, Cell.calculateOffsetCoordinates(COORDINATES_1_1_1, 0, 0, 0));
        assertArrayEquals("offset {1, 2, 3} against coordinates {0, 0, 0} should produce {1, 2, 3}", COORDINATES_1_2_3, Cell.calculateOffsetCoordinates(COORDINATES_1_2_3, 0, 0, 0));
        assertArrayEquals("offset {1, 2, 3} against coordinates {1, 1, 1} should produce {2, 3, 4}", new int[]{2, 3, 4}, Cell.calculateOffsetCoordinates(COORDINATES_1_2_3, 1, 1, 1));
        assertArrayEquals("offset {1, 2, 3} against coordinates {3, 2, 1} should produce {4, 4, 4}", new int[]{4, 4, 4}, Cell.calculateOffsetCoordinates(COORDINATES_1_2_3, 3, 2, 1));
        assertArrayEquals("offset {1, 2, 3} against coordinates {-1, -1, -1} should produce {0, 1, 2}", new int[]{0, 1, 2}, Cell.calculateOffsetCoordinates(COORDINATES_1_2_3, -1, -1, -1));
    }

    @Test(expected = IllegalStateException.class)
    public void testCalculateOffsetCoordinatesWithInvalidCoordinateLength() throws Exception {

        Cell.calculateOffsetCoordinates(COORDINATES_1_1_1, -1, -1);
    }

    @Test
    public void testStaticCalculateIndex() throws Exception {

        assertEquals("coordinates (0) should produce index 1", 1, Cell.calculateIndex(COORDINATES_1, 0));
        assertEquals("coordinates (0,0) should produce index 4", 4, Cell.calculateIndex(COORDINATES_1_1, 0, 0));
        assertEquals("coordinates (0,0,0)  should produce index 13", 13, Cell.calculateIndex(COORDINATES_1_1_1, 0, 0, 0));
        assertEquals("coordinates (0,0,0,0)  should produce index 13", 40, Cell.calculateIndex(COORDINATES_1_1_1_1, 0, 0, 0, 0));
        assertEquals("coordinates (1)  should produce index 2", 2, Cell.calculateIndex(COORDINATES_1, 1));
        assertEquals("coordinates (1,1)  should produce index 8", 8, Cell.calculateIndex(COORDINATES_1_1, 1, 1));
        assertEquals("coordinates (1,1,1)  should produce index 26", 26, Cell.calculateIndex(COORDINATES_1_1_1, 1, 1, 1));
        assertEquals("coordinates (1,1,1,1)  should produce index 80", 80, Cell.calculateIndex(COORDINATES_1_1_1_1, 1, 1, 1, 1));
        assertEquals("coordinates (-1)  should produce index 0", 0, Cell.calculateIndex(COORDINATES_1, -1));
        assertEquals("coordinates (-1,-1)  should produce index 0", 0, Cell.calculateIndex(COORDINATES_1_1, -1, -1));
        assertEquals("coordinates (-1,-1,-1)  should produce index 0", 0, Cell.calculateIndex(COORDINATES_1_1_1, -1, -1, -1));
        assertEquals("coordinates (-1,-1,-1,-1)  should produce index 0", 0, Cell.calculateIndex(COORDINATES_1_1_1_1, -1, -1, -1, -1));
        assertEquals("coordinates (1,2)  should produce index 11", 11, Cell.calculateIndex(COORDINATES_1_1, 1, 2));
        assertEquals("coordinates (1,2,3)  should produce index 47", 47, Cell.calculateIndex(COORDINATES_1_1_1, 1, 2, 3));
        assertEquals("coordinates (1,2,3,4)  should produce index 182", 182, Cell.calculateIndex(COORDINATES_1_1_1_1, 1, 2, 3, 4));
    }

    @Test
    public void testInstanceCalculateIndex() throws Exception {

        assertEquals("coordinates (0) should produce index 1", 1, CELL_1D.calculateIndex(0));
        assertEquals("coordinates (0,0) should produce index 4", 4, CELL_2D.calculateIndex(0, 0));
        assertEquals("coordinates (0,0,0)  should produce index 13", 13, CELL_3D.calculateIndex(0, 0, 0));
        assertEquals("coordinates (0,0,0,0)  should produce index 13", 40, CELL_4D.calculateIndex(0, 0, 0, 0));
        assertEquals("coordinates (1)  should produce index 2", 2, CELL_1D.calculateIndex(1));
        assertEquals("coordinates (1,1)  should produce index 8", 8, CELL_2D.calculateIndex(1, 1));
        assertEquals("coordinates (1,1,1)  should produce index 26", 26, CELL_3D.calculateIndex(1, 1, 1));
        assertEquals("coordinates (1,1,1,1)  should produce index 80", 80, CELL_4D.calculateIndex(1, 1, 1, 1));
        assertEquals("coordinates (-1)  should produce index 0", 0, CELL_1D.calculateIndex(-1));
        assertEquals("coordinates (-1,-1)  should produce index 0", 0, CELL_2D.calculateIndex(-1, -1));
        assertEquals("coordinates (-1,-1,-1)  should produce index 0", 0, CELL_3D.calculateIndex(-1, -1, -1));
        assertEquals("coordinates (-1,-1,-1,-1)  should produce index 0", 0, CELL_4D.calculateIndex(-1, -1, -1, -1));
        assertEquals("coordinates (1,2)  should produce index 11", 11, CELL_2D.calculateIndex(1, 2));
        assertEquals("coordinates (1,2,3)  should produce index 47", 47, CELL_3D.calculateIndex(1, 2, 3));
        assertEquals("coordinates (1,2,3,4)  should produce index 182", 182, CELL_4D.calculateIndex(1, 2, 3, 4));
    }

    @Test
    public void testStaticCalculateNeighbourIndex() throws Exception {

        assertEquals("coordinates (1)  should produce index 2", 1, Cell.calculateNeighbourIndex(1, CELL_1D.getCellIndex(), COORDINATES_1, 1));
        assertEquals("coordinates (1,1)  should produce index 8", 7, Cell.calculateNeighbourIndex(2, CELL_2D.getCellIndex(), COORDINATES_1_1, 1, 1));
        assertEquals("coordinates (1,1,1)  should produce index 26", 25, Cell.calculateNeighbourIndex(3, CELL_3D.getCellIndex(), COORDINATES_1_1_1, 1, 1, 1));
        assertEquals("coordinates (1,1,1,1)  should produce index 79", 79, Cell.calculateNeighbourIndex(4, CELL_4D.getCellIndex(), COORDINATES_1_1_1_1, 1, 1, 1, 1));
        assertEquals("coordinates (-1)  should produce index 0", 0, Cell.calculateNeighbourIndex(1, CELL_1D.getCellIndex(), COORDINATES_1, -1));
        assertEquals("coordinates (-1,-1)  should produce index 0", 0, Cell.calculateNeighbourIndex(2, CELL_2D.getCellIndex(), COORDINATES_1_1, -1, -1));
        assertEquals("coordinates (-1,-1,-1)  should produce index 0", 0, Cell.calculateNeighbourIndex(3, CELL_3D.getCellIndex(), COORDINATES_1_1_1, -1, -1, -1));
        assertEquals("coordinates (-1,-1,-1,-1)  should produce index 0", 0, Cell.calculateNeighbourIndex(4, CELL_4D.getCellIndex(), COORDINATES_1_1_1_1, -1, -1, -1, -1));
    }

    @Test
    public void testInstanceCalculateNeighbourIndex() throws Exception {

        assertEquals("coordinates (1)  should produce index 2", 1, CELL_1D.calculateNeighbourIndex(1));
        assertEquals("coordinates (1,1)  should produce index 8", 7, CELL_2D.calculateNeighbourIndex(1, 1));
        assertEquals("coordinates (1,1,1)  should produce index 26", 25, CELL_3D.calculateNeighbourIndex(1, 1, 1));
        assertEquals("coordinates (1,1,1,1)  should produce index 79", 79, CELL_4D.calculateNeighbourIndex(1, 1, 1, 1));
        assertEquals("coordinates (-1)  should produce index 0", 0, CELL_1D.calculateNeighbourIndex(-1));
        assertEquals("coordinates (-1,-1)  should produce index 0", 0, CELL_2D.calculateNeighbourIndex(-1, -1));
        assertEquals("coordinates (-1,-1,-1)  should produce index 0", 0, CELL_3D.calculateNeighbourIndex(-1, -1, -1));
        assertEquals("coordinates (-1,-1,-1,-1)  should produce index 0", 0, CELL_4D.calculateNeighbourIndex(-1, -1, -1, -1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStaticCalculateNeighbourIndexWith1DCellIndex() throws Exception {

        Cell.calculateNeighbourIndex(1, CELL_1D.getCellIndex(), COORDINATES_1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInstanceCalculateNeighbourIndexWith1DCellIndex() throws Exception {

        CELL_1D.calculateNeighbourIndex(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStaticCalculateNeighbourIndexWith2DCellIndex() throws Exception {

        Cell.calculateNeighbourIndex(2, CELL_2D.getCellIndex(), COORDINATES_1_1, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testINstanceCalculateNeighbourIndexWith2DCellIndex() throws Exception {

        CELL_2D.calculateNeighbourIndex(0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStaticCalculateNeighbourIndexWith3DCellIndex() throws Exception {

        Cell.calculateNeighbourIndex(3, CELL_3D.getCellIndex(), COORDINATES_1_1_1, 0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInstanceCalculateNeighbourIndexWith3DCellIndex() throws Exception {

        CELL_3D.calculateNeighbourIndex(0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStaticCalculateNeighbourIndexWith4DCellIndex() throws Exception {

        Cell.calculateNeighbourIndex(4, CELL_4D.getCellIndex(), COORDINATES_1_1_1_1, 0, 0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInstanceCalculateNeighbourIndexWith4DCellIndex() throws Exception {

        CELL_4D.calculateNeighbourIndex(0, 0, 0, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testStaticCalculateNeighbourIndexWith1DOutOfBoundIndex() throws Exception {

        Cell.calculateNeighbourIndex(1, CELL_1D.getCellIndex(), COORDINATES_1, 2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testInstanceCalculateNeighbourIndexWith1DOutOfBoundIndex() throws Exception {

        CELL_1D.calculateNeighbourIndex(2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testStaticCalculateNeighbourIndexWith2DOutOfBoundIndex() throws Exception {

        Cell.calculateNeighbourIndex(2, CELL_2D.getCellIndex(), COORDINATES_1_1, 0, 2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testInstanceCalculateNeighbourIndexWith2DOutOfBoundIndex() throws Exception {

        CELL_2D.calculateNeighbourIndex(0, 2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testStaticCalculateNeighbourIndexWith3DOutOfBoundIndex() throws Exception {

        Cell.calculateNeighbourIndex(3, CELL_3D.getCellIndex(), COORDINATES_1_1_1, 0, 0, 2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testInstanceCalculateNeighbourIndexWith3DOutOfBoundIndex() throws Exception {

        CELL_3D.calculateNeighbourIndex(0, 0, 2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testStaticCalculateNeighbourIndexWith4DOutOfBoundIndex() throws Exception {

        Cell.calculateNeighbourIndex(4, CELL_4D.getCellIndex(), COORDINATES_1_1_1_1, 0, 0, 0, 2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testInstanceCalculateNeighbourIndexWith4DOutOfBoundIndex() throws Exception {

        CELL_4D.calculateNeighbourIndex(0, 0, 0, 2);
    }

    @Test
    public void testStaticAdjustForIndex() throws Exception {

        assertEquals("index of 0 should be adjusted to -1 for 0 dimensional cell", -1, Cell.adjustForCellIndex(CELL_0D.getCellIndex(), 0));
        assertEquals("index of 2 should be adjusted to 1 for 1 dimensional cell", 1, Cell.adjustForCellIndex(CELL_1D.getCellIndex(), 2));
        assertEquals("index of 5 should be adjusted to 4 for 2 dimensional cell", 4, Cell.adjustForCellIndex(CELL_2D.getCellIndex(), 5));
        assertEquals("index of 14 should be adjusted to 13 for 3 dimensional cell", 13, Cell.adjustForCellIndex(CELL_3D.getCellIndex(), 14));
        assertEquals("index of 41 should be adjusted to 40 for 4 dimensional cell", 40, Cell.adjustForCellIndex(CELL_4D.getCellIndex(), 41));
    }

    @Test
    public void testInstanceAdjustForIndex() throws Exception {

        assertEquals("index of 0 should be adjusted to -1 for 0 dimensional cell", -1, CELL_0D.adjustForCellIndex(0));
        assertEquals("index of 2 should be adjusted to 1 for 1 dimensional cell", 1, CELL_1D.adjustForCellIndex(2));
        assertEquals("index of 5 should be adjusted to 4 for 2 dimensional cell", 4, CELL_2D.adjustForCellIndex(5));
        assertEquals("index of 14 should be adjusted to 13 for 3 dimensional cell", 13, CELL_3D.adjustForCellIndex(14));
        assertEquals("index of 41 should be adjusted to 40 for 4 dimensional cell", 40, CELL_4D.adjustForCellIndex(41));
    }

    @Test
    public void testIncrementCoordinates() throws Exception {

        assertArrayEquals("coordinates (0,0,0) with max 2 should be incremented to (1,0,0).", new int[] {1,0,0},
                Cell.incrementCoordinates(2, new int[] {0,0,0}));
        assertArrayEquals("coordinates (1,0,0) with max 2 should be incremented to (2,0,0).", new int[] {2,0,0},
                Cell.incrementCoordinates(2, new int[] {1,0,0}));
        assertArrayEquals("coordinates (2,0,0) with max 2 should be incremented to (0,1,0).", new int[] {0,1,0},
                Cell.incrementCoordinates(2, new int[] {2,0,0}));
        assertArrayEquals("coordinates (0,1,0) with max 2 should be incremented to (1,1,0).", new int[] {1,1,0},
                Cell.incrementCoordinates(2, new int[] {0,1,0}));
        assertArrayEquals("coordinates (1,1,0) with max 2 should be incremented to (2,1,0).", new int[] {2,1,0},
                Cell.incrementCoordinates(2, new int[] {1,1,0}));
        assertArrayEquals("coordinates (2,1,0) with max 2 should be incremented to (0,2,0).", new int[] {0,2,0},
                Cell.incrementCoordinates(2, new int[] {2,1,0}));
        assertArrayEquals("coordinates (0,2,0) with max 2 should be incremented to (1,2,0).", new int[] {1,2,0},
                Cell.incrementCoordinates(2, new int[] {0,2,0}));
        assertArrayEquals("coordinates (1,2,0) with max 2 should be incremented to (2,2,0).", new int[] {2,2,0},
                Cell.incrementCoordinates(2, new int[] {1,2,0}));
        assertArrayEquals("coordinates (2,2,0) with max 2 should be incremented to (0,0,1).", new int[] {0,0,1},
                Cell.incrementCoordinates(2, new int[] {2,2,0}));
        assertArrayEquals("coordinates (0,0,1) with max 2 should be incremented to (1,0,1).", new int[] {1,0,1},
                Cell.incrementCoordinates(2, new int[] {0,0,1}));
        assertArrayEquals("coordinates (1,0,1) with max 2 should be incremented to (2,0,1).", new int[] {2,0,1},
                Cell.incrementCoordinates(2, new int[] {1,0,1}));
        assertArrayEquals("coordinates (2,0,1) with max 2 should be incremented to (0,1,1).", new int[] {0,1,1},
                Cell.incrementCoordinates(2, new int[] {2,0,1}));
        assertArrayEquals("coordinates (0,1,1) with max 2 should be incremented to (1,1,1).", new int[] {1,1,1},
                Cell.incrementCoordinates(2, new int[] {0,1,1}));
        assertArrayEquals("coordinates (1,1,1) with max 2 should be incremented to (2,1,1).", new int[] {2,1,1},
                Cell.incrementCoordinates(2, new int[] {1,1,1}));
        assertArrayEquals("coordinates (2,1,1) with max 2 should be incremented to (0,2,1).", new int[] {0,2,1},
                Cell.incrementCoordinates(2, new int[] {2,1,1}));
        assertArrayEquals("coordinates (0,2,1) with max 2 should be incremented to (1,2,1).", new int[] {1,2,1},
                Cell.incrementCoordinates(2, new int[] {0,2,1}));
        assertArrayEquals("coordinates (1,2,1) with max 2 should be incremented to (2,2,1).", new int[] {2,2,1},
                Cell.incrementCoordinates(2, new int[] {1,2,1}));
        assertArrayEquals("coordinates (2,2,1) with max 2 should be incremented to (0,0,2).", new int[] {0,0,2},
                Cell.incrementCoordinates(2, new int[] {2,2,1}));
        assertArrayEquals("coordinates (0,0,2) with max 2 should be incremented to (1,0,2).", new int[] {1,0,2},
                Cell.incrementCoordinates(2, new int[] {0,0,2}));
        assertArrayEquals("coordinates (1,0,2) with max 2 should be incremented to (2,0,2).", new int[] {2,0,2},
                Cell.incrementCoordinates(2, new int[] {1,0,2}));
        assertArrayEquals("coordinates (2,0,2) with max 2 should be incremented to (0,1,2).", new int[] {0,1,2},
                Cell.incrementCoordinates(2, new int[] {2,0,2}));
        assertArrayEquals("coordinates (0,1,2) with max 2 should be incremented to (1,1,2).", new int[] {1,1,2},
                Cell.incrementCoordinates(2, new int[] {0,1,2}));
        assertArrayEquals("coordinates (1,1,2) with max 2 should be incremented to (2,1,2).", new int[] {2,1,2},
                Cell.incrementCoordinates(2, new int[] {1,1,2}));
        assertArrayEquals("coordinates (2,1,2) with max 2 should be incremented to (0,2,2).", new int[] {0,2,2},
                Cell.incrementCoordinates(2, new int[] {2,1,2}));
        assertArrayEquals("coordinates (0,2,2) with max 2 should be incremented to (1,2,2).", new int[] {1,2,2},
                Cell.incrementCoordinates(2, new int[] {0,2,2}));
        assertArrayEquals("coordinates (1,2,2) with max 2 should be incremented to (2,2,2).", new int[] {2,2,2},
                Cell.incrementCoordinates(2, new int[] {1,2,2}));
        assertArrayEquals("coordinates (2,2,2) with max 2 should be incremented to (0,0,0).", new int[] {0,0,0},
                Cell.incrementCoordinates(2, new int[] {2,2,2}));
    }

    @Test
    public void testBuildOffsetNeighbours() throws Exception {

        Cell[] neighbours = {
                new Cell<Integer, Rule<Integer>>(0, null, 2),
                new Cell<Integer, Rule<Integer>>(1, null, 2),
                new Cell<Integer, Rule<Integer>>(2, null, 2),
                new Cell<Integer, Rule<Integer>>(3, null, 2),
                new Cell<Integer, Rule<Integer>>(4, null, 2),
                new Cell<Integer, Rule<Integer>>(5, null, 2),
                new Cell<Integer, Rule<Integer>>(6, null, 2),
                new Cell<Integer, Rule<Integer>>(7, null, 2),
        };

        Cell<Integer, Rule<Integer>> cell = new Cell<Integer, Rule<Integer>>(-1, null, 2,
                Arrays.<Cell<Integer, Rule<Integer>>>asList(neighbours));

        Cell[] testOffsetNeighbours = {
                neighbours[1],
                neighbours[2],
                null,
                cell,
                null,
                neighbours[6],
                neighbours[7],
                null
        };

        List<Cell<Integer, Rule<Integer>>> offsetNeighbours = cell.getNeighbours(new int[]{1, 0});

        assertNotNull("a list of offset neighbours should be returned.", offsetNeighbours);
        assertArrayEquals("offset neighbours list should be correct", testOffsetNeighbours,
                offsetNeighbours.toArray(new Cell[offsetNeighbours.size()]));
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

        assertEquals("cell set to coordinate (-1,-1) should be  correct", _N1_N1.getState(), cell.getNeighbour(-1, -1).getState());
        assertEquals("cell set to coordinate (0,-1) should be correct", _0_N1.getState(), cell.getNeighbour(0, -1).getState());
        assertEquals("cell set to coordinate (1,-1) should be correct", _1_N1.getState(), cell.getNeighbour(1, -1).getState());
        assertEquals("cell set to coordinate (-1,0) should be correct", _N1_0.getState(), cell.getNeighbour(-1, 0).getState());
        assertEquals("cell set to coordinate (1,0) should be correct", _1_0.getState(), cell.getNeighbour(1, 0).getState());
        assertEquals("cell set to coordinate (-1,1) should be correct", _N1_1.getState(), cell.getNeighbour(-1, 1).getState());
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

        cell.setNeighbour(_N1_N1, -1, -1);
        assertEquals("cell at coordinate (-1,-1) should be  correct", _N1_N1.getState(),
                cell.getNeighbours().get(INDEX_N1_N1).getState());

        cell.setNeighbour(_0_N1, 0, -1);
        assertEquals("cell at coordinate (0,-1) should be  correct", _0_N1.getState(),
                cell.getNeighbours().get(INDEX_0_N1).getState());

        cell.setNeighbour(_1_N1, 1, -1);
        assertEquals("cell at coordinate (1,-1) should be  correct", _1_N1.getState(),
                cell.getNeighbours().get(INDEX_1_N1).getState());

        cell.setNeighbour(_N1_0, -1, 0);
        assertEquals("cell at coordinate (-1,0) should be  correct", _N1_0.getState(),
                cell.getNeighbours().get(INDEX_N1_0).getState());

        cell.setNeighbour(_1_0, 1, 0);
        assertEquals("cell at coordinate (1,0) should be  correct", _1_0.getState(),
                cell.getNeighbours().get(INDEX_1_0).getState());

        cell.setNeighbour(_N1_1, -1, 1);
        assertEquals("cell at coordinate (-1,1) should be  correct", _N1_1.getState(),
                cell.getNeighbours().get(INDEX_N1_1).getState());

        cell.setNeighbour(_0_1, 0, 1);
        assertEquals("cell at coordinate (0,1) should be  correct", _0_1.getState(),
                cell.getNeighbours().get(INDEX_0_1).getState());

        cell.setNeighbour(_1_1, 1, 1);
        assertEquals("cell at coordinate (1,1) should be  correct", _1_1.getState(),
                cell.getNeighbours().get(INDEX_1_1).getState());
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


        private Coordinates(int... coordinates) {

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
