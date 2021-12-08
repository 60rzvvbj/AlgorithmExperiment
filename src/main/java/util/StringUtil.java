package util;

public class StringUtil {

    public static String getTabString(String[] str, int interval) {
        String res = "";
        for (int i = 0; i < str.length; i++) {
            res += str[i];
            for (int j = str[i].length(); j < interval; j++) {
                res += " ";
            }
        }
        return res;
    }

    public static String getCircleString(String str, int count) {
        String res = "";
        for (int i = 0; i < count; i++) {
            res += str;
        }
        return res;
    }
}
