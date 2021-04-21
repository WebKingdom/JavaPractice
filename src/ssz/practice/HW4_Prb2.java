package ssz.practice;

import java.util.ArrayList;
import java.util.HashSet;

// substring of elements in L or L* are not part of set YES
// EX: if inStr = "1" then it is not in L nor L*
// EX: if inStr = "11" then not in L*
public class HW4_Prb2 {

    public static class APair {
        private int val;
        private boolean visited;

        public APair() {
            val = 0;
            visited = false;
        }

        public APair(int n, boolean vis) {
            val = n;
            visited = vis;
        }
    }

    public static void main(String[] args) {
        String inStr = "0100000110";
        int len = inStr.length();

        int arr[][] = new int[len][len];
        APair[][] dp = new APair[len][len];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {

                dp[i][j] = new APair(0, false);
            }
        }

        // Set for a language starting with 0 and then 1s
        HashSet<String> L = new HashSet<>();
        L.add("00000");
        L.add("11111");
        L.add("00");
        L.add("11");

        L.add("01");
        L.add("011");
        L.add("0111");
        L.add("01111");
        L.add("011111");
        L.add("0111111");
        L.add("01111111");
        // continue with more 1s at end

        HashSet<String> L2 = new HashSet<>();
        L2.add("0101");
        L2.add("01011");
        L2.add("010111");
        L2.add("0101111");
        L2.add("01011111");

        L2.add("01101");
        L2.add("011011");
        L2.add("0110111");
        L2.add("01101111");
        L2.add("011011111");

        L2.add("011101");
        L2.add("0111011");
        L2.add("01110111");
        L2.add("011101111");
        // continue with concatenations

        method1(inStr, L, arr);

//        method2(inStr, L, arr);
//        System.out.println();
//
//        arr = new int[len][len];
//        findx(inStr, L, arr);

//        method3(inStr, L, dp, 0, len - 1);

        // print adjacency matrix
//        for (int i = 0; i < len; i++) {
//            for (int j = 0; j < len; j++) {
//                if (j == len - 1) {
//                    System.out.println(dp[i][j].val);
//                }
//                else {
//                    if (j == 0) {
//                        System.out.printf("%2d: ", i);
//                    }
//                    System.out.print(dp[i][j].val + ", ");
//                }
//            }
//        }
    }

    private static int method3(String x, HashSet<String> L, APair[][] dp, int i, int j) {
        int dpj = Math.min(j + 1, x.length() - 1);

        if (j < i) {
            return i;
        }
        if (dp[i][dpj].visited) {    // If dp[i][j] is visited do not check again
            return j;
        }

        if (L.contains(x.substring(i, j + 1))) {
            dp[i][dpj].val = 1;
            dp[i][dpj].visited = true;
            dp[dpj][i].val = 1;
            dp[dpj][i].visited = true;
//            if (j - i == 0) {
//                return i;
//            }
            return j;
        }

        int temp = method3(x, L, dp, i, j - 1);
        if (temp != i) {
            dp[i][j].val = 1;
            dp[i][j].visited = true;
            dp[j][i].val = 1;
            dp[j][i].visited = true;
            i = temp;
        }
        method3(x, L, dp, i + 1, j);
//        if (i != j) {
//            dp[i][j].val = 1;
//            dp[i][j].visited = true;
//            dp[j][i].val = 1;
//            dp[j][i].visited = true;
//        }

        return dp[x.length() - 1][x.length() - 1].val;
    }

    private static void findx(String x, HashSet<String> L, int[][] arr) {
        if (L.contains(x)) {
            System.out.println("Accept");
            return;
        }

        int len = x.length();
        // stores last connected vertex, so we can find path from vertex 0 to n
        int k = 0;
        ArrayList<Integer> path = new ArrayList<>();

        for (int i = 0; i < len; i++) {     // c*n
            // determines if a substring was contained in set L
            boolean subStrContained = false;

            for (int j = len - 1; j >= i; j--) {
                String subStr = x.substring(i, j + 1);

                if (L.contains(subStr)) {
                    subStrContained = true;

                    if (k != j) {
                        if (path.isEmpty()) {
                            path.add(k);
                            path.add(j);
                        } else if (path.get(path.size() - 1) < j) {
                            path.add(j);
                        }
                    }
                    arr[i][j] = 1;
                    arr[j][i] = 1;

                    arr[k][j] = 1;
                    arr[j][k] = 1;
                    k = i;
                    i = j;
                }
            }

            if (!subStrContained) {
                // if substring was not contained in L,
                // set k to next i (last possible connected vertex)
                k = i + 1;
            }
        }

        // Accept if there is a path from vertex 0 to len
        if ( !path.isEmpty() && (path.get(0) == 0) && (path.get(path.size() - 1) == len - 1) ) {
            System.out.println("Accept");
        } else {
            System.out.println("Reject");
        }
        System.out.println("Path is:" + path);

        printArr(arr);
    }

    private static void method2(String x, HashSet<String> L, int[][] arr) {
        if (L.contains(x)) {
            System.out.println("Accept");
            return;
        }

        int len = x.length();
        // stores last connected vertex, so we can find path from vertex 0 to n
        int k = 0;

        for (int i = 0; i < len; i++) {     // c*n
            // saves the last index of the largest substring found in x
            int lastSubStr = i;
            // determines if a substring was contained in set L
            boolean subStrContained = false;

            for (int j = i; j < len; j++) { // c*n*(n - i)

                String temp = x.substring(i, j + 1);
                if (L.contains(temp)) {
                    subStrContained = true;

                    arr[i][j] = 1;
                    arr[j][i] = 1;

                    arr[k][j] = 1;
                    arr[j][k] = 1;

                    lastSubStr = j;
                    k = i;
                }
            }

            if (subStrContained) {
                // increment i by index of lastSubStr because we
                // already know longest substring in this iteration
                i = lastSubStr;
            } else {
                // if substring was not contained in L,
                // set k to next i (last possible connected vertex)
                k = i + 1;
            }
        }

        // Accept if there is a path from vertex 0 to len
        printArr(arr);
    }

    private static void printArr(int[][] arr) {
        int len = arr.length;
        // print adjacency matrix
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (j == len - 1) {
                    System.out.println(arr[i][j]);
                }
                else {
                    if (j == 0) {
                        System.out.printf("%2d: ", i);
                    }
                    System.out.print(arr[i][j] + ", ");
                }
            }
        }
    }

    private static void method1(String inStr, HashSet<String> L, int[][] arr) {
        int len = inStr.length();

        if (inStr.isEmpty()) {
            System.out.println("Accept");
            return;
        }

        for (int i = 0; i < len; i++) {
            if ( L.contains(Character.toString(inStr.charAt(i))) ) {
                arr[i][i] = 1;
            }
        }

        for (int l = 1; l < len; l++) {
            for (int i = 0; i < (len - l); i++) {
                int j = i + l;

                String temp = inStr.substring(i, j + 1);
                if (L.contains(temp)) {
                    arr[i][j] = 1;
                }

                for (int k = i; k < j; k++) {
                    if (arr[i][k] == 1 && arr[k + 1][j] == 1) {
                        arr[i][j] = 1;
                    }
                }
            }
        }

        if (arr[0][len - 1] == 1) {
            System.out.println("Accept");
        } else {
            System.out.println("Reject");
        }
    }
}
