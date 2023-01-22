package com.artiomtaliaronak.ipr1ppo;

import static org.mockito.Mockito.verify;

import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;

@RunWith(JUnit4.class)
public class MainUnitTest {

    @Mock
    View view;

    @Mock
    public MainActivity mainActivity;


    @Before
    public void setUp() throws Exception {
        mainActivity = Mockito.mock(MainActivity.class);
    }

    @Test
    public void startGame_isCorrect() throws Exception {
        mainActivity.startGame(view);
        verify(mainActivity).startGame(view);
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
        view = null;
    }
}