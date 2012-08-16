package org.karlbennett.gameoflife;

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
public interface Board<C extends Cell, S> {

    /**
     * Tick the board over one generation, this will apply all the rules to board and return a brand. A brand new
     * instance of the board will be returned that contains the state produced by the application of the rules.
     *
     * @return a new board with the new rule modified state.
     */
    public Board<C, S> tick();

    /**
     * Retrieve a Cell from the board using the supplied coordinates. This method accepts any number of coordinates.
     *
     * @param x - and arbitrary number of coordinates e.g. x, y, z...
     * @return the <code>Cell</code> found at the supplied coordinates.
     *
     * @throws IllegalCoordinateNumber if an incorrect number of coordinates has been supplied. This is so that an
     *      incorrect use of this API fails as early as possible. This is a runtime exceptions so should not be caught.
     */
    public C cell(int ...x) throws IllegalCoordinateNumber;
}
