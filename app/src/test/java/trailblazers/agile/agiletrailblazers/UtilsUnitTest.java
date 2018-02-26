package trailblazers.agile.agiletrailblazers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Lenovo on 2/24/2018.
 */
public class UtilsUnitTest {
    @Test
    public void validateZipCode() throws Exception {

        String input = "94040";
        boolean output;
        boolean expected = true;
        output = Utils.validateZipCode(input);
        assertEquals(expected,output);
    }

}