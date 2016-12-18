package com.tiy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by erronius on 12/17/2016.
 */
public class BigBoardTester {

    BigBoard bigBoard;
    @Before
    public void setUp() throws Exception {
        bigBoard = new BigBoard();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRecentMoveMethods () {
        bigBoard.placeToken(0, 1, 1, 1, 'X'); //Middle square of topmiddle board
        assertEquals(5, bigBoard.getCurrentBoardNum());
        bigBoard.placeToken(0, 1, 0, 0, 'X');
        assertEquals(1, bigBoard.getCurrentBoardNum());
        bigBoard.placeToken(0, 1, 2, 2, 'X'); //This board should now be considered 'won' by X
        bigBoard.placeToken(1, 1, 0, 1, 'O'); //O's move should send X to a won board, so currentNum = 0 (any);
        assertEquals(0, bigBoard.getCurrentBoardNum());
    }

}