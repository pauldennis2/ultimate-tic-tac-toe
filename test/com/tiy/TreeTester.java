package com.tiy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by erronius on 12/13/2016.
 */
public class TreeTester {

    List<Integer> firstIntList;
    List<Integer> secondIntList;
    List<Integer> thirdIntList;

    @Before
    public void setUp() throws Exception {

        firstIntList = new LinkedList<>();
        secondIntList = new LinkedList<>();
        thirdIntList = new LinkedList<>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testBasicTreeFunctionality () throws Exception {
        Node<Integer> root = new Node<Integer>(null, 8);
        assertEquals(0, root.getNumberOfChildren());
        Integer n1 = 6;
        Integer n2 = 4;
        firstIntList.add(n1);
        firstIntList.add(n2);
        root.setChildren(firstIntList);
        assertEquals(2, root.getNumberOfChildren());

        secondIntList.add(7);
        secondIntList.add(8);
        secondIntList.add(9);
        root.setChildren(secondIntList);
        assertEquals(3, root.getNumberOfChildren());
        Node<Integer> unwantedChild = root.getChildren().get(0);
        root.discardChild(unwantedChild); //Awww...
        int total = 0;
        for (Node<Integer> node : root.getChildren()) {
            total += node.getMe();
        }
        assertEquals(8 + 9, total);
    }

    @Test
    public void testBestChildrenScore () throws Exception {
        Node<Integer> root = new Node<Integer>(null, 8);
        firstIntList.add(3);
        firstIntList.add(5);
        firstIntList.add(7);
        root.setChildren(firstIntList);
        for (Node<Integer> node : root.getChildren()) {
            node.setScore(node.getMe());
            //Arbitrarily set their scores to equal their values
        }
        root.getMaxChildrenScore();
        assertEquals(7, root.getMaxChildrenScore());
        firstIntList.add(20);
        root.setChildren(firstIntList);
        assertEquals (4, root.getNumberOfChildren());
        for (Node<Integer> node : root.getChildren()) {
            node.setScore(node.getMe());
        }
        root.getMaxChildrenScore();
        assertEquals(20, root.getMaxChildrenScore());
    }

    @Test
    public void testBasicBigBoardTree () throws Exception {
        Node<BigBoard> root = new Node<BigBoard>(null, new BigBoard());
        ArrayList<BigBoard> possibles = root.getMe().getPossibleMoves(1, 'O');//Should be 9 possibles
        root.setChildren(possibles);
        assertEquals(9, root.getNumberOfChildren());
        ArrayList<BigBoard> morePossibilities = root.getMe().getPossibleMoves(0, 'O');//Should be 81 here
        root.setChildren(morePossibilities);
        assertEquals(81, root.getNumberOfChildren());
    }

    @Test
    public void testBasicBBTreeDecreasingNumberOfMoves () throws Exception {
        Node<BigBoard> root = new Node<BigBoard>(null, new BigBoard());
        ArrayList<BigBoard> possibles = root.getMe().getPossibleMoves(1, 'O');
        root.setChildren(possibles);
        assertEquals(9, root.getNumberOfChildren());

        Node<BigBoard> firstChild = root.getChildren().get(0);

        ArrayList<BigBoard> secondOrderPossibles = firstChild.getMe().getPossibleMoves(1, 'X');
        firstChild.setChildren(secondOrderPossibles);
        assertEquals(8, firstChild.getNumberOfChildren());//We're in the same board with one move taken
        assertEquals(9, root.getNumberOfChildren());//Root should still have 9 children
        /*for (Node<BigBoard> bb : firstChild.getChildren()) {
            System.out.println(bb.getMe());
        }*/
    }

    @Test
    public void testMiniMax () throws Exception {
        Node<Integer> root = new Node<>(null, 5);

        List<Integer> childlist = new LinkedList<>();
        childlist.add(1);
        childlist.add(2); //These numbers don't matter and could be the same for all we care
        childlist.add(3);
        root.setChildren(childlist);

        //These are for grandchildren
        firstIntList.add(3);
        firstIntList.add(5);
        firstIntList.add(7);
        secondIntList.add(2);
        secondIntList.add(8);
        secondIntList.add(11);
        thirdIntList.add(2);
        thirdIntList.add(3);
        thirdIntList.add(4);

        root.getChildren().get(0).setChildren(firstIntList);
        root.getChildren().get(1).setChildren(secondIntList);
        root.getChildren().get(2).setChildren(thirdIntList);

        printTree(root);

        setScoreEqualValue(root);
        //int scoreOf2ndChilds2ndChild = root.getChildren().get(1).getChildren().get(1);
        Node<Integer> secondChild = root.getChildren().get(1);
        Node<Integer> secondChilds2ndChild = secondChild.getChildren().get(1);
        assertEquals(8, secondChilds2ndChild.getScore());

        for (Node<Integer> node : root.getChildren()) {
            int min = node.getMinChildrenScore();  //This is unneccesary since it will be called anyway inside the MaxofMin/Minofmax function
        }                                           //To prove it, we'll go without it for the next test
        int maximin = root.getMaxOfChildrensMin();
        assertEquals(3, maximin);
        int minimax = root.getMinOfChildrensMax();
        assertEquals(4, minimax);

        //Where's my prunin' shears at?!

        root.discardChild(root.getChildren().get(2));
        secondChild.discardChild(secondChild.getChildren().get(0));
        printTree(root);

        maximin = root.getMaxOfChildrensMin();
        assertEquals(8, maximin);
        minimax = root.getMinOfChildrensMax();
        assertEquals(7, minimax);
    }

    public void printTree (Node<Integer> root) {
        System.out.println("Printing tree:");
        System.out.println("Root = " + root.getMe());
        System.out.print("Children = ");
        for (Node<Integer> child : root.getChildren()) {
            System.out.print(child.getMe() + " ");
        }
        System.out.println();
        System.out.print("Grandchildren = ");
        for (Node<Integer> child : root.getChildren()) {
            for (Node<Integer> grandchild : child.getChildren()) {
                System.out.print(grandchild.getMe() + " ");
            }
            System.out.print("\t");
        }
        System.out.println();
    }

    public void setScoreEqualValue (Node<Integer> root) {
        root.setScore(root.getMe());
        for (Node<Integer> child : root.getChildren()) {
            setScoreEqualValue(child);
        }
    }
}
