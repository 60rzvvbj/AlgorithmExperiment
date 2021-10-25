package util;

public class RandomUtil {

    public static int getIntRandom(int l, int r) {
        return (int)(Math.random() * (r - l + 1) + l);
    }

    public static double getDoubleRandom(double l, double r) {
        return Math.random() * (r - l) + l;
    }

    // 生成n的置换(0 ~ n - 1)
    public static int[] createPermutation(int n) {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = i;
        }
        for (int i = 0; i < n; i++) {
            int o = getIntRandom(0, n - 1);
            int t = res[i];
            res[i] = res[o];
            res[o] = t;
        }
        return res;
    }
}
