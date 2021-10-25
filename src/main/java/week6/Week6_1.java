package week6;

public class Week6_1 {
    public static void main(String[] args) {
        int[] arr = {5, 200, 2, 100, 30, 200};
        MatrixContinuousMultiplication matrixContinuousMultiplication = new MatrixContinuousMultiplication();
        int res = matrixContinuousMultiplication.run(arr);
        System.out.println(res);
    }
}

class MatrixContinuousMultiplication {

    int[][] dp;
    int len;

    // 运算
    public int run(int[] arr) {
        if (arr.length < 2) {
            throw new RuntimeException("输入无意义");
        }
        len = arr.length - 1;
        dp = new int[len][len];

        for (int i = 0; i < len; i++) {
            dp[i][i] = 0;
        }

        for (int l = 1; l < len; l++) {
            for (int z = l; z < len; z++) {
                int h = z - l;
                dp[h][z] = Integer.MAX_VALUE;
                for (int i = h + 1; i <= z; i++) {
                    dp[h][z] = Math.min(dp[h][i - 1] + dp[i][z] + arr[h] * arr[i] * arr[z + 1], dp[h][z]);
                }
            }
        }

        return dp[0][len - 1];
    }
}
