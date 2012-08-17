package org.karlbennett.gameoflife;

import java.util.Collections;
import java.util.List;

/**
 * User: karl
 * Date: 16/08/12
 *
 * This is a cell is used to make up a board for the Game of Life. It's direct neighbours, current and rule modified
 * state can be retrieved.
 */
public class Cell<S extends Comparable<S>, R extends Rule<S>> {

    private final S state;

    private final List<R> rules;

    private final List<Cell<S, R>> neighbours;

    /**
     * Construct a new <code>Cell</code> with the supplied state, rules, and neighbours.
     *
     * @param state - the state for this cell.
     * @param rules - the rules that should be applied to this cell.
     * @param neighbours - the cells direct neighbouring.
     */
    public Cell(S state, List<R> rules, List<Cell<S, R>> neighbours) {

        this.state = state;
        this.rules = null == rules ? null : Collections.unmodifiableList(rules);
        this.neighbours = null == neighbours ? null : Collections.unmodifiableList(neighbours);
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

        S nextState = state;

        // Iterate through the rules and return the first changed state.
        if (null != rules) for (R rule : rules) {

            nextState = rule.apply(this);

            if (nextState != state) return nextState;
        }

        // Otherwise return the current state.
        return state;
    }

    /**
     * Get all the cells that are neighbours to this cell.
     *
     * @return all the cells neighbours.
     */
    public List<Cell<S, R>> getNeighbours() {

        return neighbours;
    }
}
