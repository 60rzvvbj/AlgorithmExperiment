package week7;

public class Week7_1 {
    public static void main(String[] args) {
        String a = "ACTCCTAG";
        String b = "CATTCAGC";
        LongestCommonSubstring longestCommonSubstring = new LongestCommonSubstring();
        System.out.println(longestCommonSubstring.find(a, b));
        longestCommonSubstring.show();
    }
}

// 最短公共子串
class LongestCommonSubstring {

    private char[] a;
    private char[] b;
    private int[][] dp;
    private String[][] sdp;

    // 寻找最长公共子串
    public String find(String strA, String strB) {
        a = (" " + strA).toCharArray();
        b = (" " + strB).toCharArray();
        dp = new int[a.length][b.length];
        sdp = new String[a.length][b.length];

        for (int i = 0; i < a.length; i++) {
            dp[i][0] = 0;
            sdp[i][0] = "";
        }
        for (int i = 0; i < b.length; i++) {
            dp[0][i] = 0;
            sdp[0][i] = "";
        }
        for (int i = 1; i < a.length; i++) {
            for (int j = 1; j < b.length; j++) {
                if (a[i] == b[j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    sdp[i][j] = sdp[i - 1][j - 1] + a[i];
                } else {
                    if (dp[i][j - 1] > dp[i - 1][j]) {
                        dp[i][j] = dp[i][j - 1];
                        sdp[i][j] = sdp[i][j - 1];
                    } else {
                        dp[i][j] = dp[i - 1][j];
                        sdp[i][j] = sdp[i - 1][j];
                    }
                }
            }
        }
        return sdp[a.length - 1][b.length - 1];
    }

    // 显示对比结果
    public void show() {
        // "\033[1;33m" 这个东西用来显示黄色字符
        String res = sdp[a.length - 1][b.length - 1];
        char[] chars = (res + " ").toCharArray();
        int index = 0;
        for (int i = 1; i < a.length; i++) {
            System.out.print(chars[index] == a[i] ? "\033[1;33m" + chars[index++] + "\033[0m" : a[i]);
        }
        System.out.println();
        index = 0;
        for (int i = 1; i < b.length; i++) {
            System.out.print(chars[index] == b[i] ? "\033[1;33m" + chars[index++] + "\033[0m" : b[i]);
        }
        System.out.println();
    }
}