package support;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DNAPC on 30.01.2018.
 */
public class MD5Test {
    private static final String line = "root";
    private static final String expected = "63a9f0ea7bb98050796b649e85481845";
    @Test
    public void md5Hash() throws Exception {
        assertEquals(expected, MD5.md5Hash(line));
    }
}