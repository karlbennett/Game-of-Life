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

//        this.root = buildBoard(
//                rules,
//                initialState,
//                new ArrayList<Cell<S, R>>(Arrays.asList(new Cell[(int)Math.pow(3, dimensions.length) - 1])),
//                dimensions);
        this.root = null;
    }

    public static <S extends Comparable<S>, R extends Rule<S>> Cell<S, R> buildBoard(
            List<R> rules,
            InitialState<S> initialState,
            List<Cell<S, R>> neighbours,
            int... dimensions
    ) {

        boolean stop = false;

        for (int x : dimensions) if (0 >= x) stop = true;

        if (stop) return null;

        Cell<S, R> newCell = new Cell<S, R>(initialState.state(), rules, null);

        int[] newDimensions;
        List<Cell<S, R>> newNeighbours;
        int recursiveIndex;
        int previousRecursiveIndex = 0;
        Cell<S, R> neighbour;
        for (int d = 0; d < dimensions.length; d++) {
            // Decrement the current dimension value so that we will stop recursing at some point.
            newDimensions = Arrays.copyOf(dimensions, dimensions.length);
            newDimensions[d] = dimensions[d] - 1;

            // Create the neighbours list for the new Cell.
            newNeighbours = new ArrayList<Cell<S, R>>(Arrays.<Cell<S, R>>asList(new Cell[neighbours.size()]));

            // Set the neighbour index for the recursive Cell creation for this dimension.
            // 1D => 1, 2D => 3, 3D => 9...
            recursiveIndex = (int) Math.pow(3, d);

            // If we are working with a board that has more than one dimension then the first neighbour of every
            // dimensional neighbour above dimension x will have had it's first neighbour created by the neighbour at
            // the current index from the previous dimension.
            if (0 < previousRecursiveIndex) {

                neighbour = neighbours.get(previousRecursiveIndex);

                if (null != neighbour) {

                    neighbour = neighbour.getNeighbours().get(recursiveIndex);

                    newNeighbours.set(previousRecursiveIndex, neighbour);
                }
            }

            // If the current neighbour has not already been populated then create it recursively.
            if (null == neighbours.get(recursiveIndex)) {

                neighbours.set(recursiveIndex, buildBoard(rules, initialState, newNeighbours, newDimensions));
            }

            neighbour = neighbours.get(recursiveIndex);

            if (null != neighbour) neighbour.getNeighbours().set(recursiveIndex - 1, newCell);

            previousRecursiveIndex = recursiveIndex;
        }

        newCell.setNeighbours(neighbours);

        return newCell;
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
