package siucs.scholarsprogramapp;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by jeff on 5/3/17.
 */

public class JeffUnitTests extends TestCase{

    User testUser;

    @Before
    public void setUp(){
        //int classYear, String email, String faculty, String firstName, String lastName, int serviceHours, int numberOfPhotos
         testUser= new User(2017, "jpjung2@siu.edu", "no", "Jeff", "Jung", 20, 14);

    }

    @Test
    public void test1(){
        assertEquals("Jeff", testUser.firstName);
    }

    @Test
    public void test2(){
        assertEquals("jpjung2@siu.edu", testUser.email);
    }

    @Test
    public void test3(){
        assertEquals(20, testUser.serviceHours);
    }
}
