/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.exception;

/**
 * Represents an exception to be thrown from DAO layer when no cars meet the
 * finding criteria set in the query
 * 
 * @author Jon Zaballa
 */
public class NoCarException extends Exception {

    /**
     * Creates a new instance of <code>NoCarException</code> without detail
     * message.
     */
    public NoCarException() {
    }

    /**
     * Constructs an instance of <code>NoCarException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NoCarException(String msg) {
        super(msg);
    }
}
