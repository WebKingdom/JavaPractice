package ssz.practice;

import java.util.Scanner;

public class BeatSpread10812 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int numTests = scan.nextInt();

        for (int i = 0; i < numTests; i++) {
            long sum = scan.nextLong();
            long diff = scan.nextLong();

            // x + y = sum
            // x = sum - y
            // y = sum - x

            // abs(x - y) = diff
            // abs(sum - 2y) = diff
            // sum - 2y = +-diff
            // sum +-diff = 2y
            // y = (sum +- diff)/2

            // abs(x - sum + x) = diff
            // 2x - sum = +-diff
            // x = (sum +- diff)/2

            double score1 = (sum - diff) / 2.0;
            if (score1 < 0) {
                System.out.println("impossible");
            } else {
                score1 = (sum + diff) / 2.0;
                if (score1 % 1 != 0) {
                    System.out.println("impossible");
                } else {
                    System.out.println(((long) score1) + " " + ((long) (sum - score1)));
                }
            }
        }
    }
}
