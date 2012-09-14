package org.karlbennett.gameoflife;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
     * relation to the current cell having the coordinates (0), (0,0), (0,0,0), etc. Where as the index generation is
     * simplified by treating the current cell as if it has coordinates (1), (1,1), (1,1,1), etc.
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

        // Calculate the offset.
        for (int i = 0; i < offsetCoordinates.length; i++) offsetCoordinates[i] = coordinates[i] + offset[i];

        return offsetCoordinates;
    }

    /**
     * Calculate the index for the cell requested with the supplied neighbour coordinates and offset. The offset is any
     * coordinate difference that may need to be applied to the supplied neighbour coordinates so they are still correct
     * where the current cells coordinates are (1,1). For example, if the supplied neighbour coordinates assume the
     * current cells coordinate are (0,0) then the offset will need to be (1,1).
     *
     * @param offset      - the offset that will need to be applied to the neighbour coordinates so that they are
     *                    correct if the current cells coordinates are assumed to be (1,1).
     * @param coordinates - the coordinates for the requested cell. These must be in relation to the current cell
     *                    having the coordinates (0,0);
     * @return the index for the request neighbour.
     */
    public static int calculateIndex(int[] offset, int... coordinates) {

        int[] offsetCoordinates = calculateOffsetCoordinates(offset, coordinates);

        int index = 0;

        for (int i = 0; i < offsetCoordinates.length; i++) index += Math.pow(3, i) * offsetCoordinates[i];

        return index;
    }

    /**
     * Utility method for checking the supplied coordinates to make sure they are valid for the supplied cell.
     *
     * @param dimensions  - the number of dimensions supported by the current cell.
     * @param coordinates - the coordinates to check. Must be supplied as an array to allow overloading.
     * @throws IllegalArgumentException  if the supplied are (0,0) because that is coordinate of this cell not a
     *                                   neighbour. Or if two many dimensional coordinates are supplied e.g. if (0,1,2)
     *                                   is supplied for a 2D cell.
     * @throws IndexOutOfBoundsException if any coordinate value greater than 1 is supplied.
     */
    public static void checkNeighbourCoordinates(int dimensions, int[] coordinates) {

        if (coordinates.length != dimensions) {

            throw new IllegalArgumentException("The number of supplied coordinates is incorrect. Should be (" +
                    dimensions + "), but was (" + coordinates.length + ").");
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
     * Adjust the neighbour index to take into account that the current cell is not in the set of neighbours. It will do
     * this by subtracting 1 off the index if it is greater than the cell index.
     *
     * @param cellIndex - the index of the current cell.
     * @param index     - the index to adjust.
     * @return the adjusted index.
     */
    public static int adjustForCellIndex(int cellIndex, final int index) {

        return cellIndex > index ? index : index - 1;
    }

    /**
     * Calculate the index for the neighbour requested with the supplied coordinates. This method adjusts the index in
     * relation to the current cell not being contained within the neighbour list.
     *
     * @param dimensions  - the number of dimensions supported by the current cell.
     * @param cellIndex   - the index of the current cell.
     * @param offset      - the offset that will need to be applied to the neighbour coordinates so that they are
     *                    correct if the current cells coordinates are assumed to be (1,1).
     * @param coordinates - the coordinates for the requested neighbour. These must be in relation to the current cell
     *                    having the coordinates (0,0);
     * @return the index for the request neighbour.
     */
    public static int calculateNeighbourIndex(int dimensions, int cellIndex, int[] offset, int... coordinates) {

        checkNeighbourCoordinates(dimensions, coordinates);

        return adjustForCellIndex(cellIndex, calculateIndex(offset, coordinates));
    }


    private final S state;

    private S nextState;

    private final List<R> rules;

    private final int dimensions;

    private List<Cell<S, R>> neighbours;

    private final int[] coordinateOffset;

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

        this.cellIndex = calculateIndex(cellCoordinates);
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
     * Get a list of neighbours are contained in this cells neighbours list but surround the cell at the supplied
     * coordinates offset from the current cell.
     *
     * @param offset - the offset coordinate for the new cell that treat the current cell as (0,0).
     * @return the new offset neighbours list.
     */
    public List<Cell<S, R>> getNeighbours(int... offset) {

        int[] coordinates = new int[offset.length];

        // Start at the lowest neighbour.
        Arrays.fill(coordinates, -1);

        int[] inverseOffset = new int[offset.length];

        // Calculate the inverse offset.
        for (int i = 0; i < offset.length; i++) inverseOffset[i] = offset[i] * -1;

        return buildOffsetNeighbours(offset, inverseOffset, coordinates);
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

        return neighbours.get(calculateNeighbourIndex(coordinates));
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

        neighbours.set(calculateNeighbourIndex(coordinates), neighbour);
    }

    /**
     * Get the offset that should be applied to give this cell the coordinates (1,1).
     *
     * @return this cells coordinate offset.
     */
    public int[] getCoordinateOffset() {

        return coordinateOffset;
    }

    /**
     * Get the neighbour index of the current cell.
     *
     * @return the current cells neighbour index.
     */
    public int getCellIndex() {

        return cellIndex;
    }


    /**
     * Adjust the neighbour index to take into account that the current cell is not in the set of neighbours. It will do
     * this by subtracting 1 off the index if it is greater than the cell index.
     *
     * @param index the index to adjust.
     * @return the adjusted index.
     */
    public int adjustForCellIndex(final int index) {

        return adjustForCellIndex(cellIndex, index);
    }

    /**
     * Utility method for checking the supplied coordinates to make sure they are valid for the supplied cell.
     *
     * @param coordinates - the coordinates to check.
     * @throws IllegalArgumentException  if the supplied are (0,0) because that is coordinate of this cell not a
     *                                   neighbour. Or if two many dimensional coordinates are supplied e.g. if (0,1,2)
     *                                   is supplied for a 2D cell.
     * @throws IndexOutOfBoundsException if any coordinate value greater than 1 is supplied.
     */
    public void checkNeighbourCoordinates(int... coordinates) {

        checkNeighbourCoordinates(dimensions, coordinates);
    }

    /**
     * Apply the current cells offset to a set of coordinates.
     *
     * @param coordinates - coordinates to have the offset applied to.
     * @return the offset coordinates.
     * @throws IllegalStateException if the offset array length is not equal to the number of coordinates.
     */
    public int[] calculateOffsetCoordinates(int... coordinates) {

        return calculateOffsetCoordinates(coordinateOffset, coordinates);
    }

    /**
     * Calculate the index for the cell requested with the supplied neighbour coordinates.
     *
     * @param coordinates - the coordinates for the requested cell. These must be in relation to the current cell
     *                    having the coordinates (0,0);
     * @return the index for the request neighbour.
     */
    public int calculateIndex(int... coordinates) {

        return calculateIndex(coordinateOffset, coordinates);
    }

    /**
     * Calculate the index for the neighbour requested with the supplied coordinates. This method adjusts the index in
     * relation to the current cell not being contained within the neighbour list.
     *
     * @param coordinates - the coordinates for the requested neighbour. These must be in relation to the current cell
     *                    having the coordinates (0,0);
     * @return the index for the request neighbour.
     */
    public int calculateNeighbourIndex(int... coordinates) {

        return calculateNeighbourIndex(dimensions, cellIndex, coordinateOffset, coordinates);
    }

    /**
     * Build a list of neighbours from the neighbours in the current cell offset so that the list is indexed as if the
     * current cell resides in the neighbour location defined with the supplied offset coordinates. The inverse of the
     * offset coordinates and a starting neighbour coordinate must also be supplied.
     *
     * Note: This method works through all the available neighbours by only every incrementing the starting coordinates.
     *
     * @param offset - the offset that defines the coordinates to be used for the location of the new current cell.
     * @param inverseOffset - coordinates that are the inverse of the offset coordinates, these are used to check if the
     *                      supplied cell should be included as a neighbour.
     * @param coordinates - the coordinates for the current neighbour that should be retrieved.
     * @return the new offset neighbour list.
     */
    public List<Cell<S, R>> buildOffsetNeighbours(int[] offset, int[] inverseOffset, int[] coordinates) {

        // Try and retrieve the neighbour at the supplied coordinates in relation to the supplied offset.
        Cell<S, R> neighbour = null;
        List<Cell<S, R>> offsetNeighbours = null;
        try {

            // If the supplied coordinate equal the inverse of the supplied offset then the offset neighbour is actually
            // the supplied cell.
            if (Arrays.equals(inverseOffset, coordinates)) {

                neighbour = this;

            } else {

                neighbour = getNeighbour(calculateOffsetCoordinates(offset, coordinates));

            }

            offsetNeighbours = Arrays.<Cell<S, R>>asList(new Cell[getNeighbours().size()]);

            // Add the retrieved neighbour to the new offset list.
            offsetNeighbours.set(
                    calculateNeighbourIndex(offset.length, getCellIndex(), getCoordinateOffset(), coordinates),
                    neighbour);

            // If the neighbour cannot be retrieved then return an empty list.
        } catch (IllegalArgumentException e) {

            return Collections.emptyList();

        } catch (IndexOutOfBoundsException e) {

            return Collections.emptyList();
        }

        // Retrieve the rest of the offset neighbours by incrementally recursing through all the possible neighbour
        // coordinates.
        List<Cell<S, R>> tempOffsetNeighbours = null;
        Cell<S, R> tempNeighbour = null;
        int[] coordinatesCopy = null;
        for (int i = 0; i < coordinates.length; i++) {

            coordinatesCopy = Arrays.copyOf(coordinates, coordinates.length);

            coordinatesCopy[i]++;

            tempOffsetNeighbours = buildOffsetNeighbours(offset, inverseOffset, coordinatesCopy);

            for (int j = 0; j < tempOffsetNeighbours.size(); j++) {

                tempNeighbour = tempOffsetNeighbours.get(j);

                if (null != tempNeighbour) offsetNeighbours.set(j, tempNeighbour);
            }
        }

        // Then return the new offset neighbour list.
        return offsetNeighbours;
    }

    /**
     * Find any neighbours that fall on the dimensional axis' of the neighbour at the supplied coordinates in relation
     * to the supplied cell. Using the neighbours of the supplied cell.
     * <p/>
     * This method is primarily used to find any neighbours that might already exists and can be pre-populated for a new
     * neighbour that is about to be created.
     *
     * @param coordinates - the coordinates for the neighbour that is to have it's existing neighbours found.
     * @return the existing neighbours of the neighbour at the supplied coordinates including the supplied cell.
     * @throws IllegalArgumentException  if the supplied are (0,0) because that is coordinate of this cell not a
     *                                   neighbour. Or if two many dimensional coordinates are supplied e.g. if (0,1,2)
     *                                   is supplied for a 2D cell.
     * @throws IndexOutOfBoundsException if any coordinate value greater than 1 is supplied.
     */
    public List<Cell<S, R>> findAxisNeighbours(int... coordinates) {

        List<Cell<S, R>> neighbours = new ArrayList<Cell<S, R>>(
                Arrays.<Cell<S, R>>asList(new Cell[Cell.neighbourNumber(coordinates.length)]));

        Cell<S, R> neighbour;
        int[] neighbourCoordinates = new int[coordinates.length];
        for (int i = 0; i < coordinates.length - 1; i++) {

            Arrays.fill(neighbourCoordinates, 0);
            neighbourCoordinates[i] = 1;

            neighbour = getNeighbour(neighbourCoordinates);

            if (null != neighbour)
                neighbours.set(calculateNeighbourIndex(neighbourCoordinates), neighbour.getNeighbour(coordinates));
        }

        // Set the current cell as the neighbour opposite the one at the supplied coordinates.
        for (int i = 0; i < coordinates.length; i++) neighbourCoordinates[i] = coordinates[i] * -1;

        neighbours.set(calculateNeighbourIndex(neighbourCoordinates), this);

        return neighbours;
    }
}
