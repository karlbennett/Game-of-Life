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

    /**
     * Build the coordinate offset for this cell in relation to it's neighbours. Neighbour lookups are carried out in
     * relation to the current cell having the coordinates (0,0). Where as the index generation is simplified by
     * treating the current cell as if it has coordinates (1,1);
     *
     * @param dimensions - the number of dimensions this cell supports.
     * @return the coordinate offset.
     */
    public static int[] buildCoordinateOffset(int dimensions) {

        int[] offset = new int[dimensions];

        Arrays.fill(offset, 1);

        return offset;
    }

    /**
     * Utility method for checking the supplied coordinates to make sure they are valid for the supplied cell.
     *
     * @param cell        - the cell to check the coordinates against.
     * @param coordinates - the coordinates to check.
     * @throws IllegalArgumentException  if the supplied are (0,0) because that is coordinate of this cell not a
     *                                   neighbour. Or if two many dimensional coordinates are supplied e.g. if (0,1,2)
     *                                   is supplied for a 2D cell.
     * @throws IndexOutOfBoundsException if any coordinate value greater than 1 is supplied.
     */
    public static void checkNeighbourCoordinates(Cell cell, int... coordinates) {

        if (coordinates.length != cell.dimensions) {

            throw new IllegalArgumentException("The number of supplied coordinates is incorrect. Should be (" +
                    cell.dimensions + "), but was (" + coordinates.length + ").");
        }

        int sum = 0;

        for (int c : coordinates) {

            sum += Math.abs(c);

            if (1 < c) throw new IndexOutOfBoundsException("The coordinate value (" + c + ") is too large.");
        }

        if (0 == sum)
            throw new IllegalArgumentException("The cell at coordinate (0,0) is the current cell not a neighbour.");
    }

    /**
     * Apply the supplied offset to a set of coordinates.
     *
     * @param offset      - the offset to apply.
     * @param coordinates - coordinates to have the offset applied to.
     * @return the offset coordinates.
     * @throws IllegalStateException if the offset array length is not equal to the number of coordinates.
     */
    public static int[] calculateOffsetCoordinates(int[] offset, int... coordinates) {

        if (offset.length != coordinates.length) {

            throw new IllegalStateException("The offset array must have the same length as the number of coordinates supplied.");
        }

        int[] offsetCoordinates = Arrays.copyOf(coordinates, coordinates.length);

        for (int i = 0; i < offsetCoordinates.length; i++) offsetCoordinates[i] = coordinates[i] + offset[i];

        return offsetCoordinates;
    }

    /**
     * Calculate the index for the neighbour requested with the supplied coordinates.
     *
     * @param coordinates - the coordinates for the requested neighbour. These must be in relation to the current cell
     *                    having the coordinates (1,1);
     * @return the index for the request neighbour.
     */
    public static int calculateIndex(int... coordinates) {

        int index = 0;

        for (int i = 0; i < coordinates.length; i++) index += Math.pow(3, i) * coordinates[i];

        return index;
    }

    /**
     * Find any neighbours that fall on the dimensional axis' of the neighbour at the supplied coordinates in relation
     * to the supplied cell. Using the neighbours of the supplied cell.
     * <p/>
     * This method is primarily used to find any neighbours that might already exists and can be pre-populated for a new
     * neighbour that is about to be created.
     *
     * @param cell        - the cell to start from when carrying ou the neighbour search.
     * @param coordinates - the coordinates for the neighbour that is to have it's existing neighbours found.
     * @return the existing neighbours of the neighbour at the supplied coordinates including the supplied cell.
     * @throws IllegalArgumentException  if the supplied are (0,0) because that is coordinate of this cell not a
     *                                   neighbour. Or if two many dimensional coordinates are supplied e.g. if (0,1,2)
     *                                   is supplied for a 2D cell.
     * @throws IndexOutOfBoundsException if any coordinate value greater than 1 is supplied.
     */
    public static <S extends Comparable<S>, R extends Rule<S>> List<Cell<S, R>> findAxisNeighbours(
            Cell<S, R> cell,
            int... coordinates) {

        checkNeighbourCoordinates(cell, coordinates);

        List<Cell<S, R>> neighbours = new ArrayList<Cell<S, R>>(
                Arrays.<Cell<S, R>>asList(new Cell[Cell.neighbourNumber(coordinates.length)]));

        Cell<S, R> neighbour;
        int[] neighbourCoordinates = new int[coordinates.length];
        for (int i = 0; i < coordinates.length - 1; i++) {

            Arrays.fill(neighbourCoordinates, 0);
            neighbourCoordinates[i] = 1;

            neighbour = cell.getNeighbour(neighbourCoordinates);

            if (null != neighbour) neighbours.set(
                    cell.adjustForCellIndex(
                            calculateIndex(calculateOffsetCoordinates(cell.coordinateOffset, neighbourCoordinates))),
                    neighbour.getNeighbour(coordinates));
        }

        for (int i = 0; i < coordinates.length; i++) neighbourCoordinates[i] = coordinates[i] * -1;

        neighbours.set(calculateIndex(calculateOffsetCoordinates(cell.coordinateOffset, neighbourCoordinates)), cell);

        return neighbours;
    }


    private final S state;

    private S nextState;

    private final List<R> rules;

    private int dimensions;

    private List<Cell<S, R>> neighbours;

    private int[] coordinateOffset;

    private final int cellIndex;

    /**
     * Construct a new <code>Cell</code> with the supplied state, rules, and neighbours.
     *
     * @param state      - the state for this cell.
     * @param rules      - the rules that should be applied to this cell.
     * @param dimensions - the number of dimensions for this cell e.g. 1D, 2D, 3D.
     */
    public Cell(S state, List<R> rules, int dimensions) {

        this(state, rules, dimensions,
                new ArrayList<Cell<S, R>>(Arrays.<Cell<S, R>>asList(new Cell[neighbourNumber(dimensions)])));
    }

    public Cell(S state, List<R> rules, int dimensions, List<Cell<S, R>> neighbours) {

        if (neighbourNumber(dimensions) != neighbours.size()) {

            throw new IllegalStateException("The number supplied neighbours is incorrect in relation to the supported dimension.");
        }

        this.state = state;
        this.rules = rules;
        this.dimensions = dimensions;
        this.neighbours = neighbours;
        this.coordinateOffset = buildCoordinateOffset(dimensions);

        int[] cellCoordinates = new int[dimensions];
        Arrays.fill(cellCoordinates, 0);

        this.cellIndex = calculateIndex(calculateOffsetCoordinates(this.coordinateOffset, cellCoordinates));
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
     * Get the rules that will be applied to this cell to generate the next state.
     *
     * @return the cells rules.
     */
    public List<R> getRules() {

        return rules;
    }

    /**
     * Get the number of dimensions this cell supports.
     *
     * @return the number of dimenstions this cell supports.
     */
    public int getDimensions() {

        return dimensions;
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
     *                                   neighbour. Or if two many dimensional coordinates are supplied e.g. if (0,1,2)
     *                                   is supplied for a 2D cell.
     * @throws IndexOutOfBoundsException if any coordinate value greater than 2 is supplied.
     */
    public Cell<S, R> getNeighbour(int... coordinates) {

        checkNeighbourCoordinates(this, coordinates);

        return neighbours.get(adjustForCellIndex(calculateIndex(calculateOffsetCoordinates(coordinateOffset, coordinates))));
    }

    /**
     * Set the neighbour at the supplied coordinates where this cell is (0,0).
     *
     * @param neighbour   - the cell to set as a new neighbour.
     * @param coordinates - the coordinates for the new neighbour.
     * @throws IllegalArgumentException  if the supplied are (0,0) because that is coordinate of this cell not a
     *                                   neighbour. Or if two many dimensional coordinates are supplied e.g. if (0,1,2)
     *                                   is supplied for a 2D cell.
     * @throws IndexOutOfBoundsException if any coordinate value greater than 2 is supplied.
     */
    public void setNeighbour(Cell<S, R> neighbour, int... coordinates) {

        checkNeighbourCoordinates(this, coordinates);

        neighbours.set(adjustForCellIndex(calculateIndex(calculateOffsetCoordinates(coordinateOffset, coordinates))), neighbour);
    }

    /**
     * Adjust the neighbour index to take into account that the current cell is not in the set of neighbours. It will do
     * this by subtracting 1 of the index if it is greater than the cell index.
     *
     * @param index the index to adjust.
     * @return the adjusted index.
     */
    public int adjustForCellIndex(final int index) {

        return cellIndex > index ? index : index - 1;
    }
}
