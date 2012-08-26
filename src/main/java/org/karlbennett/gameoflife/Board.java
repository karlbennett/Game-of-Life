package org.karlbennett.gameoflife;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: karl
 * Date: 16/08/12
 * <p/>
 * This is a board for the Game of Life. It consists of a set of cells that are all connected to each other through N
 * neighbourhood links.
 *
 * @param <C> - the type of {@see Cell} that this board contains.
 * @param <S> - the type of state that the Cell contains.
 */
public class Board<S extends Comparable<S>, R extends Rule<S>, I extends InitialState<S>, C extends Cell<S, R>> {

    private final List<R> rules;

    private final InitialState<S> initialState;

    private final int[] dimensions;

    private final Cell<S, R> root;


    /**
     * Construct a new <code>Board</code> that adheres to the supplied rules and fits the supplied number of dimension
     * and scale.
     *
     * @param rules        - the rules that will be applied on each tick of the Game of Life.
     * @param initialState - the object the supplies the initial state for all the cells.
     * @param dimensions   - the dimensions of the board e.g. width, height, depth...
     */
    public Board(List<R> rules, InitialState<S> initialState, int... dimensions) {

        this.rules = rules;

        if (null == initialState) {

            throw new IllegalArgumentException("The initialState can not be null");
        }

        this.initialState = initialState;

        this.dimensions = dimensions;

//        this.root = buildBoard(rules, initialState, null, dimensions);
        this.root = null;
    }

    public static <S extends Comparable<S>, R extends Rule<S>> Cell<S, R> buildBoard(
            Cell<S, R> cell,
            InitialState<S> initialiser,
            int... dimensions) {

        if (null == cell) {

            throw new IllegalArgumentException("Cell cannot be null for Board.buildBoard(Cell<S, R>,InitialState<S>,int...).");
        }

        if (cell.getDimensions() != dimensions.length) {

            throw new IllegalArgumentException("The number of dimension values submitted must match the number of " +
                    "dimensions supported by the supplied cell.");
        }

        boolean stop = false;

        // Check to see if any dimension values are less than or equal to zero because if one is then that means we have
        // reached the edge of the world and should stop building.
        for (int x : dimensions) if (0 >= x) stop = true;

        if (stop) return null;

        int[] newDimensions;
        int[] neighbourCoordinates = new int[dimensions.length];
        Cell<S, R> neighbour;
        int[] cellCoordinates = new int[dimensions.length];
        for (int d = 0; d < dimensions.length; d++) {

            // Set the coordinates for the neighbour we are going to try and create.
            Arrays.fill(neighbourCoordinates, 0);
            neighbourCoordinates[d] = 1;

            // If the current neighbour does not already exist then create it recursively.
            if (null == cell.getNeighbour(neighbourCoordinates)) {

                neighbour = new Cell<S, R>(initialiser.state(), cell.getRules(), cell.getDimensions(),
                        Cell.findAxisNeighbours(cell, neighbourCoordinates));

                // Set the coordinates for the parent cell in relation to the new neighbour cell.
                Arrays.fill(cellCoordinates, 0);
                cellCoordinates[d] = -1;

                // Set the current cell as the parent neighbour.
                neighbour.setNeighbour(cell, cellCoordinates);

                // Decrement the current dimension value so that we will stop recursing at some point.
                newDimensions = Arrays.copyOf(dimensions, dimensions.length);
                newDimensions[d] = dimensions[d] - 1;

                cell.setNeighbour(buildBoard(neighbour, initialiser, newDimensions), neighbourCoordinates);
            }
        }

        return cell;
    }

    /**
     * Find all the neighbours for the cell that is currently being populated. This is done by taken the given parent
     * and traversing it's neighbours depending on the neighbour index of the parent. That is, if the parent is to the
     * left of the current cell then the neighbours that can be found are at the coordinates (1,1) from the parent and
     * (1,-1). Where as if the parent is above the current cell the the neighbours that can be found are at the
     * coordinates (-1,-1) and (1, -1).
     *
     * @param neighbours
     * @return
     */
    public List<Cell<S, R>> findNeighbours(List<Cell<S, R>> neighbours) {

        return null;
    }

    /**
     * Get the size of the requested dimension. For example if the width of a 2D board was required that could be
     * requested with the following.
     * <p/>
     * <code>int width = board.dimensionSize(0);</code>
     *
     * @param d - the dimension of the size is to be returned e.g. 0 => x, 1 => y, 2 => z...
     * @return the size of the requested dimension.
     */
    public int dimensionSize(int d) {

        if (dimensions.length <= d) {

            throw new IndexOutOfBoundsException("The supplied dimension index is too large. " +
                    dimensions.length + " < " + d);
        }

        return 0;
    }

    /**
     * Tick the board over one generation, this will apply all the rules to board and return a brand. A brand new
     * instance of the board will be returned that contains the state produced by the application of the rules.
     *
     * @return a new board with the new rule modified state.
     */
    public Board<S, R, I, C> tick() {

        return null;
    }

    /**
     * Retrieve a Cell from the board using the supplied coordinates. This method accepts any number of coordinates.
     *
     * @param x - and arbitrary number of coordinates e.g. x, y, z...
     * @return the <code>Cell</code> found at the supplied coordinates.
     * @throws IllegalCoordinateNumber if an incorrect number of coordinates has been supplied. This is so that an
     *                                 incorrect use of this API fails as early as possible. This is a runtime exceptions so should not be caught.
     */
    public C cell(int... x) throws IllegalCoordinateNumber {

        if (dimensions.length != x.length) {

            throw new IllegalArgumentException("The number of coordinates is invalid. Expected: "
                    + dimensions.length + " Actual: " + x.length);
        }

        for (int i = 0; i < x.length; i++) {

            if (dimensions[i] <= x[i]) {

                throw new IndexOutOfBoundsException("The supplied coordinate with index " + i +
                        " is larger than it's related dimension of size " + dimensions[i]);
            }
        }

        return null;
    }
}
