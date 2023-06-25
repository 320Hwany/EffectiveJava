package effective.chapter2.item6;

import java.util.regex.Pattern;

public class Regex {

    private static final Pattern MY_REGEX = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{4,12}$");

    public static boolean isBadWay(String s) {
        return s.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{4,12}$");
    }

    public static boolean isGoodWay(String s) {
        return MY_REGEX.matcher(s).matches();
    }
}
