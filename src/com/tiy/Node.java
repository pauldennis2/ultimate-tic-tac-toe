package com.tiy;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by erronius on 12/13/2016.
 */
public class Node<T> {
    private Node<T> parent;
    private T me; //"payload" or "value".
    private List<Node> children;
    private int score;
    private int maxChildrenScore;
    private int minChildrenScore;

    public Node (Node<T> parent, T me) {
        this.parent = parent;
        children = new LinkedList<>();
        this.me = me;
    }

    //The old children are now cast adrift
    public void setChildren (List<T> children) {
        this.children = new LinkedList<Node>();
        for (T t : children) {
            Node<T> n = new Node<T>(this, t);
            this.children.add(n);
        }
    }

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
        maxChildrenScore = bestScore;
        return maxChildrenScore;
    }

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
        minChildrenScore = bestScore;
        return minChildrenScore; //These are messing with variables they shouldn't be. need to revisit these functions
        //They should probably throw a NoChildException if this method is called when they have no children
    }

    public T getMe () {
        return me;
    }

    public List<Node> getChildren () {
        return children;
    }

    public Node<T> getParent () {
        return parent;
    }

    public int getScore () {
        return score;
    }

    public void setScore (int score) {
        this.score = score;
    }

    public int getMaxChildrenScore () throws NoChildrenException {
        int bestScore = Integer.MIN_VALUE;
        for (Node node : children) {
            if (node.getScore() > bestScore) {
                bestScore = node.getScore();
            }
        }
        maxChildrenScore = bestScore;
        return maxChildrenScore;
    }

    public int getMinChildrenScore () throws NoChildrenException {
        int bestScore = Integer.MAX_VALUE;
        if (children != null) {
            for (Node node : children) {
                if (node.getScore() < bestScore) {
                    bestScore = node.getScore();
                }
            }
        }
        minChildrenScore = bestScore;
        return minChildrenScore;
    }

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