package support;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DNAPC on 30.01.2018.
 */
public class TLSSenderTest {
    private static final String TO = "demkoandrey2012@gmail.com";
    private static final String SUBJECT = "test";
    private static final String TEXT = "test";

    @Test
    public void run() throws Exception {
        TLSSender tlsSender = new TLSSender(TO,TEXT,SUBJECT);
        assertTrue(tlsSender.send());
    }

}