package support;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DNAPC on 30.01.2018.
 */
public class PasswordGenTest {
    @Test
    public void generate() throws Exception {
        assertFalse(PasswordGen.generate().isEmpty());
    }

}