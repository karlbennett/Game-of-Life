package org.karlbennett.gameoflife;

import java.util.List;

/**
 * User: karl
 * Date: 16/08/12
 */
public interface Cell<S> {

    public S currentState();

    public S nextState();

    public List<Cell<S>> neighbours();
}
