package org.karlbennett.gameoflife;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: karl
 * Date: 16/08/12
 * <p/>
 * This is a cell is used to make up a board for the Game of Life. It's direct neighbours, current and rule modified
 * state can be retrieved.
 */
public class Cell<S extends Comparable<S>, R extends Rule<S>> {

    /**
     * Calculate the number of neighbours the cell should have in relation to the number of dimensions the cell supports.
     *
     * @param dimensions - the number of supported dimensions e.g. x, y, z...
     * @return the number of neighbours this could potentially have.
     */
    public static int neighbourNumber(int dimensions) {

        return (int) Math.pow(3, dimensions) - 1;
    }


    private final S state;

    private S nextState;

    private final List<R> rules;

    private int dimensions;

    private List<Cell<S, R>> neighbours;

    /**
     * Construct a new <code>Cell</code> with the supplied state, rules, and neighbours.
     *
     * @param state      - the state for this cell.
     * @param rules      - the rules that should be applied to this cell.
     * @param dimensions - the number of dimensions for this cell e.g. 1D, 2D, 3D.
     */
    public Cell(S state, List<R> rules, int dimensions) {

        this.state = state;
        this.rules = rules;
        this.dimensions = dimensions;
        this.neighbours = new ArrayList<Cell<S, R>>(Arrays.<Cell<S, R>>asList(new Cell[neighbourNumber(dimensions)]));
    }

    public Cell(S state, List<R> rules, int dimensions, List<Cell<S, R>> neighbours) {

        if (neighbourNumber(dimensions) != neighbours.size()) {

            throw new IllegalStateException("The number supplied neighbours is incorrect in relation to the supported dimension.");
        }

        this.state = state;
        this.rules = rules;
        this.dimensions = dimensions;
        this.neighbours = neighbours;
    }

    /**
     * Get the current state of the cell before the games rules have been applied.
     *
     * @return the current state.
     */
    public S getState() {

        return state;
    }

    /**
     * Get the next state of the cell after the rules have been applied.
     *
     * @return the next state.
     */
    public S getNextState() {

        // If the nextState has not been calculated.
        if (null == nextState) {

            nextState = state;

            // Then iterate through the rules and return the first modified state.
            if (null != rules) for (R rule : rules) {

                nextState = rule.apply(this);

                if (nextState != state) return nextState;
            }
        }

        // Otherwise return the current state.
        return nextState;
    }

    /**
     * Get all the cells that are neighbours to this cell.
     *
     * @return all the cells neighbours.
     */
    public List<Cell<S, R>> getNeighbours() {

        return neighbours;
    }

    /**
     * Set the list of cells that are the neighbours of this cell.
     *
     * @param neighbours - the list of the cells neighbours.
     */
    void setNeighbours(List<Cell<S, R>> neighbours) {

        this.neighbours = neighbours;
    }

    /**
     * Retrieve the neighbour at the supplied coordinates where this cell is (0,0).
     *
     * @param coordinates - the coordinates for the cells neighbour.
     * @return the requested neighbour.
     * @throws IllegalArgumentException  if the supplied are (0,0) because that is coordinate of this cell not a
     *                                   neighbour. Or if two many dimensional coordinates are supplied e.g. if (0,1,2) is supplied for a 2D cell.
     * @throws IndexOutOfBoundsException if any coordinate value greater than 2 is supplied.
     */
    public Cell<S, R> getNeighbour(int... coordinates) {

        checkCoordinates(coordinates);

        return null;
    }

    /**
     * Set the neighbour at the supplied coordinates where this cell is (0,0).
     *
     * @param neighbour   - the cell to set as a new neighbour.
     * @param coordinates - the coordinates for the new neighbour.
     * @throws IllegalArgumentException  if the supplied are (0,0) because that is coordinate of this cell not a
     *                                   neighbour. Or if two many dimensional coordinates are supplied e.g. if (0,1,2) is supplied for a 2D cell.
     * @throws IndexOutOfBoundsException if any coordinate value greater than 2 is supplied.
     */
    public void setNeighbour(Cell<S, R> neighbour, int... coordinates) {

        checkCoordinates(coordinates);

    }


    /**
     * Utility method for checking the supplied coordinates to make sure they are valid.
     *
     * @param coordinates - the coordinates to check.
     * @throws IllegalArgumentException  if the supplied are (0,0) because that is coordinate of this cell not a
     *                                   neighbour. Or if two many dimensional coordinates are supplied e.g. if (0,1,2) is supplied for a 2D cell.
     * @throws IndexOutOfBoundsException if any coordinate value greater than 2 is supplied.
     */
    private void checkCoordinates(int... coordinates) {

        if (coordinates.length != dimensions) {

            throw new IllegalArgumentException("The number of supplied coordinates is incorrect. Should be (" +
                    dimensions + "), but was (" + coordinates.length + ").");
        }

        int sum = 0;

        for (int c : coordinates) {

            sum += c;

            if (2 < c) throw new IndexOutOfBoundsException("The coordinate value (" + c + ") is too large.");
        }

        if (0 == sum)
            throw new IllegalArgumentException("The cell at coordinate (0,0) is the current cell not a neighbour.");
    }
}
