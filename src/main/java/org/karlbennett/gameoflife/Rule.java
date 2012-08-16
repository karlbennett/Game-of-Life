package org.karlbennett.gameoflife;

/**
 * User: karl
 * Date: 16/08/12
 *
 * This is a rule for the Game of Life, it can be applied to a cell.
 *
 * @type C - the type of {@see Cell} that this rule can be applied to.
 * @type S - the type of state that this rule produces.
 */
public interface Rule<R extends Rule, C extends Cell<S, R>, S> {

    /**
     * Apply this rule to the supplied <code>Cell</code> and return the produced state.
     *
     * @param cell - the <code>Cell</code> to apply the rule to.
     * @return the state the rule produces.
     */
    public S apply(C cell);
}
