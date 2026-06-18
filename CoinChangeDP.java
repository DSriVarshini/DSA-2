import java.util.*;

public class CoinChangeDP {
    public static void main(String[] args) {

        int[] coins = {1, 2, 5, 10, 20};
        int amount = 43;

        int[] dp = new int[amount + 1];
        int[] choice = new int[amount + 1];

        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i && dp[i - coin] != Integer.MAX_VALUE) {
                    if (dp[i] > dp[i - coin] + 1) {
                        dp[i] = dp[i - coin] + 1;
                        choice[i] = coin;
                    }
                }
            }
        }

        System.out.println("Amount = " + amount);
        System.out.println("Available Coins = " + Arrays.toString(coins));
        System.out.println("\nMinimum Number of Coins Required = " + dp[amount]);

        System.out.println("\nCoins Used:");
        int temp = amount;
        while (temp > 0) {
            System.out.print(choice[temp] + " ");
            temp -= choice[temp];
        }
    }
}