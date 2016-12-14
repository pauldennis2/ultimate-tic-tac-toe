package com.tiy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by erronius on 12/13/2016.
 */
public class SmartBotTester {

    BigBoard bigBoard;
    SmartBot smarty;
    @Before
    public void setUp() throws Exception {
        bigBoard = new BigBoard();
        smarty = new SmartBot ('O', 0, 'X', bigBoard);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testBoardControlEvaluation() throws Exception {
        int result;
        bigBoard.placeToken(0,0,0,0, 'O');
        bigBoard.placeToken(0,0,1,1, 'O');
        bigBoard.placeToken(0,0,2,2, 'O');
        result = smarty.evaluateBoardControl(bigBoard);
        assertEquals(SmartBot.CONTROL_CORNER_BOARD, result); //we own the top left corner board. should be worth 6

        bigBoard.placeToken(1,1,0,0, 'O');
        bigBoard.placeToken(1,1,1,1, 'O');
        bigBoard.placeToken(1,1,2,2, 'O');
        //bigBoard.statusUpdate();
        System.out.println(bigBoard);
        result = smarty.evaluateBoardControl(bigBoard);
        assertEquals(SmartBot.CONTROL_CORNER_BOARD + SmartBot.CONTROL_MIDDLE_BOARD, result);


    }
    @Test
    public void otherTests () throws Exception {
        /*result = smarty.evaluateStatusBoard(bigBoard);
        assertEquals(SmartBot.WINNING_STATUS_BOARD, result);//Right now this should fail since we havent called update
        *//*
        bigBoard.statusUpdate();
        int result = smarty.evaluateStatusBoard(bigBoard);
        assertEquals(SmartBot.WINNING_STATUS_BOARD, result);//This should work*/
    }
}
