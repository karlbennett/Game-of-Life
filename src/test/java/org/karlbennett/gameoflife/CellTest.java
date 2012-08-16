package org.karlbennett.gameoflife;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * User: karl
 * Date: 16/08/12
 */
public class CellTest {

    @Test
    public void testGetNextStateWithNullRules() throws Exception {

        Cell<Boolean, Rule<Boolean>> cell = new Cell<Boolean, Rule<Boolean>>(true, null, null);

        assertTrue("next state with no rules should be true", cell.getNextState());
    }

    @Test
    public void testGetNextStateWithOneRule() throws Exception {

        Rule<Boolean> rule = new Rule<Boolean>() {

            @Override
            public <R extends Rule<Boolean>> Boolean apply(Cell<Boolean, R> cell) {

                return !cell.getState();
            }
        };

        Cell<Boolean, Rule<Boolean>> cell = new Cell<Boolean, Rule<Boolean>>(
                true,
                Collections.singletonList(rule),
                null);

        assertFalse("next state with one rule should be false", cell.getNextState());
    }

    @Test
    public void testGetNextStateWithMultipleRules() throws Exception {

        Rule<Boolean> rule1 = new Rule<Boolean>() {

            @Override
            public <R extends Rule<Boolean>> Boolean apply(Cell<Boolean, R> cell) {

                return cell.getState();
            }
        };

        Rule<Boolean> rule2 = new Rule<Boolean>() {

            @Override
            public <R extends Rule<Boolean>> Boolean apply(Cell<Boolean, R> cell) {

                return !cell.getState();
            }
        };

        Rule<Boolean> rule3 = new Rule<Boolean>() {

            @Override
            public <R extends Rule<Boolean>> Boolean apply(Cell<Boolean, R> cell) {

                return cell.getState();
            }
        };

        Cell<Boolean, Rule<Boolean>> cell = new Cell<Boolean, Rule<Boolean>>(
                true,
                Arrays.asList(rule1, rule2, rule3),
                null);

        assertFalse("next state with multiple rules should be false", cell.getNextState());
    }
}
