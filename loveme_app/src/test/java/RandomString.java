/**
 * Created by chise on 29-Oct-17.
 */
import java.util.Locale;
import java.util.Random;

public class RandomString {
    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String lower = upper.toLowerCase(Locale.ROOT);

    private static final String digits = "0123456789";

    private static final String alphanum = upper + lower + digits;

    private final Random random;

    public String getRandomString(int length) {
        StringBuilder result = new StringBuilder();
        for (int idx = 0; idx < length; idx++) {
            result.append(alphanum.charAt(random.nextInt(alphanum.length())));
        }
        return result.toString();
    }

    public String getRandomEmail() {
        return getRandomString(6) + "@" + getRandomString(3) + "." + getRandomString(2);
    }

    public RandomString() {
        this.random = new Random();
    }
}