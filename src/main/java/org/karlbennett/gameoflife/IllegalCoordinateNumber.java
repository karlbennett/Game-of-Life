package org.karlbennett.gameoflife;

/**
 * User: karl
 * Date: 16/08/12
 *
 * This exception is thrown when an invalid number of coordinates has been supplied for a {@see Cell} lookup.
 */
public class IllegalCoordinateNumber extends IllegalArgumentException {

    private final int coordinateNumber;

    /**
     * Construct a new IllegalCoordinateNumber exception with the supplied message and correct coordinate number.
     *
     * @param message - the message for this exceptions.
     * @param coordinateNumber - the correct number of coordinates that should be used for a <code>Cell</code> lookup.
     */
    public IllegalCoordinateNumber(String message, int coordinateNumber) {
        super(message);

        this.coordinateNumber = coordinateNumber;
    }

    /**
     * Get the correct number of coordinates for the game of life board.
     *
     * @return the correct number of coordinates.
     */
    public int getCoordinateNumber() {

        return coordinateNumber;
    }
}
