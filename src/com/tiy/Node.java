package com.tiy;

import org.omg.CORBA.NO_IMPLEMENT;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by pauldennis on 12/13/2016.
 *
 * This Node/Tree structure is designed to be used with a "scoreable" payload (i.e. T, the generic type). Using the
 * getMinOfChildrensMax, getMaxOfChildrensMin, getMaxChildrenScore, getMinChildrenScore, we then traverse the tree
 * using a "minimax"/"maximin" algorithm. This is not yet fully implemented.
 * See TreeTester for unit tests.
 */
public class Node<T> {
    private Node<T> parent;
    private T me; //"payload" or "value".
    private List<Node> children;
    private int score;

    /**
     *
     * @param parent null if this is the root
     * @param me the payload/generic contained in the Node
     */
    public Node (Node<T> parent, T me) {
        this.parent = parent;
        children = new LinkedList<>();
        this.me = me;
    }

    /**
     * Sets the given list as the children of the node. <b>Any old children will be discarded</b>.
     * @param children List to set as the new children of the node
     */
    public void setChildren (List<T> children) {
        this.children = new LinkedList<Node>();
        for (T t : children) {
            Node<T> n = new Node<T>(this, t);
            this.children.add(n);
        }
    }

    /**
     * This method assists a minimax/maximin search/algorithm.
     * @return the minimum score of the max of the grandchildren's scores
     * @throws NoChildrenException if the node has no children
     */
    public int getMinOfChildrensMax () throws NoChildrenException {
        int bestScore = Integer.MAX_VALUE;
        if (children.size() == 0) {
            throw new NoChildrenException();
        }
        for (Node child : children) {
            if (child.getMaxChildrenScore() < bestScore) {
                bestScore = child.getMaxChildrenScore();
            }
        }
        return bestScore;
    }

    /**
     * This method assists a minimax/maximin search/algorithm.
     * @return the maximum score of the min of the grandchildren's scores
     * @throws NoChildrenException if the node has no children
     */
    public int getMaxOfChildrensMin () throws NoChildrenException {
        int bestScore = Integer.MIN_VALUE;
        if (children.size() == 0) {
            throw new NoChildrenException();
        }
        for (Node child : children) {
            if (child.getMinChildrenScore() > bestScore) {
                bestScore = child.getMinChildrenScore();
            }
        }
        return bestScore;
    }

    /**
     *
     * @return The payload, generic type contained in the Node.
     */
    public T getMe () {
        return me;
    }

    public List<Node> getChildren () {
        return children;
    }

    public Node<T> getParent () {
        return parent;
    }

    /**
     * Figuring out the score is the job of the implementing class.
     * @return the score.
     */
    public int getScore () {
        return score;
    }

    public void setScore (int score) {
        this.score = score;
    }

    /**
     * Returns the maximum score of the node's children.
     * @return The max of its children's scores.
     * @throws NoChildrenException if the node has no children
     */
    public int getMaxChildrenScore () throws NoChildrenException {
        int bestScore = Integer.MIN_VALUE;
        if (children.size() == 0) {
            throw new NoChildrenException();
        }
        for (Node node : children) {
            if (node.getScore() > bestScore) {
                bestScore = node.getScore();
            }
        }
        return bestScore;
    }

    /**
     * Returns the minimum score of the node's children.
     * @return The min of its children's scores.
     * @throws NoChildrenException if the node has no children
     */
    public int getMinChildrenScore () throws NoChildrenException {
        int bestScore = Integer.MAX_VALUE;
        if (children.size() == 0) {
            throw new NoChildrenException();
        }
        for (Node node : children) {
            if (node.getScore() < bestScore) {
                bestScore = node.getScore();
            }
        }
        return bestScore;
    }

    /**
     * Prunes the unwantedChild from the list of children for this node.
     * @param unwantedChild child to be discarded
     */
    public void discardChild (Node<T> unwantedChild) {
        for (Node child : children) {
            if (child.equals(unwantedChild)) {
                children.remove(child);
                break; //Without this line we get a ConcurrentModificationException
            }
        }
    }

    public int getNumberOfChildren () {
        return children.size();
    }
}