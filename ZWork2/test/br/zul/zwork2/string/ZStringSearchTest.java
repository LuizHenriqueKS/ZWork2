package br.zul.zwork2.string;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author skynet
 */
public class ZStringSearchTest {
    
    public ZStringSearchTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of listResults method, of class ZStringSearch.
     */
    @Test
    public void testListResults() {
        System.out.println("listResults");
        ZStringSearch instance = new ZStringSearch("1.3..6.8", true, ".","..");
        List<ZStringSearchResult> result = instance.listResults();
        for (ZStringSearchResult r:result){
            if (r.getIndex()!=1&&r.getIndex()!=6){
                fail("Esse index não devia existir: "+r.getIndex());
            }
        }
    }


    /**
     * Test of countResults method, of class ZStringSearch.
     */
    @Test
    public void testCountResults() {
        System.out.println("countResults");
        ZStringSearch instance = new ZStringSearch("1.3..6.8", true, ".","..");
        int expResult = 2;
        int result = instance.countResults();
        assertEquals(expResult, result);
    }

    /**
     * Test of getResult method, of class ZStringSearch.
     */
    @Test
    public void testGetResult() {
        System.out.println("getResult");
        ZStringSearch instance = new ZStringSearch("1.3..6.8", true, ".","..");
        ZStringSearchResult expResult = new ZStringSearchResult(instance, ".", 1);
        ZStringSearchResult result = instance.getResult();
        assertEquals(expResult, result);
    }
    
}
