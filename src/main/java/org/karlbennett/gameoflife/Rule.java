package org.karlbennett.gameoflife;

/**
 * User: karl
 * Date: 16/08/12
 */
public interface Rule<C extends Cell, S> {

    public S apply(C cell);
}
