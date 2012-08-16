package org.karlbennett.gameoflife;

/**
 * User: karl
 * Date: 16/08/12
 *
 * This class provides the Game of Life board with a way of setting the initial state for all it's cells.
 */
public interface InitialState<S extends Comparable<S>> {

    /**
     * A possible initial state for a {@see Cell}.
     *
     * @return an initial state.
     */
    public S state();
}
