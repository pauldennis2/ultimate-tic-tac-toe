package com.tiy;

/**
 * Created by pauldennis on 12/14/2016.
 *
 * Thrown by Node class's methods that return mins and maxes in order to make sure the caller understands there are no
 * children. There is no "error message" necessary since the exception itself is the message: the Node doesn't have
 * children.
 */
public class NoChildrenException extends Exception {
}
