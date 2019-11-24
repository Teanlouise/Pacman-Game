package pacman.util;

import java.io.Serializable;

/**
 * Exception thrown when a game file cannot be unpacked.
 */
public class UnpackableException extends Exception implements Serializable {

    private Exception unpackableException;
    private String message;

    /**
     * Standard UnpackableException which takes no parameters, like Exception()
     */
    public UnpackableException() {
        this.unpackableException = new Exception();
    }

    /**
     * A UnpackableException that contains a message.
     * @param message to add to the exception
     */
    public UnpackableException(String message) {
        this.unpackableException = new Exception();
        this.message = message;
    }
}