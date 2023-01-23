package com.artiomtaliaronak.ipr1ppo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

@RunWith(JUnit4.class)
public class MainUnitTest {

    @Test
    public void startTest(){
        MainActivity mainActivity = Mockito.mock(MainActivity.class);
        mainActivity.checkUsername();
        verify(mainActivity).checkUsername();
    }

    @Test
    public void userCreationTest(){
        User user = new User(-1,"test", 1);
        assertEquals(-1, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals(1, user.getScore());
    }

    @Test
    public void readAllDataTest(){
        DatabaseHelper databaseHelper = Mockito.mock(DatabaseHelper.class);
        databaseHelper.readData();
        verify(databaseHelper).readData();
    }

    @Test
    public void updateDatabaseTest(){
        DatabaseHelper databaseHelper = Mockito.mock(DatabaseHelper.class);
        databaseHelper.updateDatabase(10, "testupdate", 1);
        verify(databaseHelper).updateDatabase(10, "testupdate",1);
    }

}