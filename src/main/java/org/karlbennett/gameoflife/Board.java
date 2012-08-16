package org.karlbennett.gameoflife;

import java.util.List;

/**
 * User: karl
 * Date: 16/08/12
 *
 * This is a board for the Game of Life. It consists of a set of cells that are all connected to each other through N
 * neighbourhood links.
 *
 * @type C - the type of {@see Cell} that this board contains.
 * @type S - the type of state that the Cell contains.
 */
public class Board<S extends Comparable<S>, R extends Rule<S>, I extends InitialState<S>, C extends Cell<S,R>> {

    private final List<Rule<S>> rules;

    private final InitialState<S> initialState;

    private final int dimensions;

    private final int[] scale;

    private final Cell<S, R> root;


    /**
     * Construct a new <code>Board</code> that adheres to the supplied rules and fits the supplied number of dimension
     * and scale.
     *
     * @param rules - the rules that will be applied on each tick of the Game of Life.
     * @param initialState - the object the supplies the initial state for all the cells.
     * @param dimensions - the number of dimensions of the board e.g. 2D, 3D...
     * @param scale - the scale of the dimensions of the board e.g. width, height, depth...
     */
    public Board(List<Rule<S>> rules, InitialState<S> initialState, int dimensions, int... scale) {

        this.rules = rules;

        if (null == initialState) {

            throw new IllegalArgumentException("The initialState can not be null");
        }

        this.initialState = initialState;

        if (scale.length != dimensions) {

            throw new IllegalArgumentException("The number of the scale arguments must match the number of dimensions." +
            " Dimensions: (" + dimensions + ") SStateExceptioncale: (" + scale + ")");
        }

        this.dimensions = dimensions;

        this.scale = scale;

        this.root = null;
    }


    /**
     * Get the size of the requested dimension. For example if the width of a 2D board was required that could be
     * requested with the following.
     *
     * <code>int width = board.dimensionSize(0);</code>
     *
     * @param d - the dimension of the size is to be returned e.g. 0 => x, 1 => y, 2 => z...
     * @return the size of the requested dimension.
     */
    public int dimensionSize(int d) {

        if (dimensions < d) {

            throw new IndexOutOfBoundsException("The supplied dimension index is too large. " + dimensions + " < " + d);
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
     *
     * @throws IllegalCoordinateNumber if an incorrect number of coordinates has been supplied. This is so that an
     *      incorrect use of this API fails as early as possible. This is a runtime exceptions so should not be caught.
     */
    public C cell(int ...x) throws IllegalCoordinateNumber {

        if (scale.length != x.length) {

            throw new IllegalArgumentException("The number of coordinates is invalid. Expected: "
                    + scale.length + " Actual: " + x.length);
        }

        for (int i = 0; i < x.length; i++) {

            if (scale[i] <= x[i]) {

                throw new IndexOutOfBoundsException("The supplied coordinate with index " + i +
                        " is larger than it's related dimension of size " + scale[i]);
            }
        }

        return null;
    }
}
