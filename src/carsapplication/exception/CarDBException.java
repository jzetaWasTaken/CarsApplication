/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.exception;

/**
 *
 * @author jon
 */
public class CarDBException extends Exception {

    /**
     * Creates a new instance of <code>CarDBException</code> without detail
     * message.
     */
    public CarDBException() {
    }

    /**
     * Constructs an instance of <code>CarDBException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public CarDBException(String msg) {
        super(msg);
    }
}
