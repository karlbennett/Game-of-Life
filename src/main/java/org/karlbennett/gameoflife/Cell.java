package org.karlbennett.gameoflife;

import java.util.List;

/**
 * User: karl
 * Date: 16/08/12
 *
 * This is a cell is used to make up a board for the Game of Life. It's neighbours, current and rule modified state can
 * be retrieved.
 */
public interface Cell<S> {

    /**
     * Get the current state of the cell before the games rules have been applied.
     *
     * @return the current state.
     */
    public S currentState();

    /**
     * Get the next state of the cell after the rules have been applied.
     *
     * @return the next state.
     */
    public S nextState();

    /**
     * Get all the cells that are neighbours to this cell.
     *
     * @return all the cells neighbours.
     */
    public List<Cell<S>> neighbours();
}
