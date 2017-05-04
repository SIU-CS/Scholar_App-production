package siucs.scholarsprogramapp;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;


import static android.R.attr.onClick;
import static org.junit.Assert.assertEquals;

/**
 * Created by Kirby on 5/3/2017.
 */

public class KirbyUnitTests {
    User newUser;
    MessageBoardSubPost message1, message2;

    @Before
    public void setUp(){
        newUser = new User (2017, "millereaton@siu.edu", "no", "Miller", "Eaton ", 20, 1);
        message1 = new MessageBoardSubPost("Sam Hunt", "Heres a message", 00002341002);
        message2 = new MessageBoardSubPost("Bob Phil", "This is the body", 000022154);
    }

    @Test
    public void checkUserName()throws Exception{
        assertEquals("millereaton@siu.edu", newUser.email);
    }

    @Test
    public void checkTimeStamp() throws Exception {
        assertEquals(00002341002, message1.timeStamp);
        assertEquals(000022154, message2.timeStamp);

    }

    @Test
    public void checkPostName() throws Exception {
        assertEquals("Sam Hunt", message1.posterName);
        assertEquals("Bob Phil", message2.posterName);

    }


}