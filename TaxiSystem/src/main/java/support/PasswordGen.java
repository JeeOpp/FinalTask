package support;

import org.apache.commons.lang.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by DNAPC on 21.01.2018.
 */
public final class PasswordGen {

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final int PASS_LENGTH = 10;

    private PasswordGen() {
        throw new UnsupportedOperationException("Empty constructor is not supported.");
    }

    public static String generate() {
        StringBuilder password = new StringBuilder(PASS_LENGTH);
        Random random = new Random(System.nanoTime());

        List<String> charCategories = new ArrayList<>();
        charCategories.add(LOWER);
        charCategories.add(UPPER);
        charCategories.add(DIGITS);

        for (int i = 0; i < PASS_LENGTH; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            password.append(charCategory.charAt(position));
        }
        return new String(password);
    }
}
