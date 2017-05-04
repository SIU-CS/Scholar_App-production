package siucs.scholarsprogramapp;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GabeUnitTests extends TestCase {

    public MessageBoardPost testPost;
    public MessageBoardSubPost tempSubPost1 = new MessageBoardSubPost("User1", "comment1", 1495540800000L);
    public MessageBoardSubPost tempSubPost2 = new MessageBoardSubPost("User2", "comment2", 1495540800001L);
    public ArrayList<MessageBoardSubPost> subPosts = new ArrayList<>();

    @Before
    public void setUp(){

        subPosts.add(tempSubPost1);
        subPosts.add(tempSubPost2);
        subPosts.trimToSize();

        testPost = new MessageBoardPost("Gabe Wohlwend", "Subject",
                "Body Text", 1495540800000L,  subPosts);
    }


    @Test
    public void test1(){
        assertEquals("Gabe Wohlwend", testPost.posterName);
    }

    @Test
    public void test2(){
        assertEquals(2, testPost.subPosts.size());
    }

    @Test
    public void test3(){
        assertEquals("User1", testPost.subPosts.get(0).posterName);
    }
}
