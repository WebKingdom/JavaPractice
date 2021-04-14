package ssz.practice;

import java.util.ArrayList;
import java.util.HashSet;

// substring of elements in L or L* are not part of set YES
// EX: if inStr = "1" then it is not in L nor L*
// EX: if inStr = "11" then not in L*
public class HW4_Prb2 {

    public static void main(String[] args) {
        String inStr = "0110111001";
        int len = inStr.length();

        int arr[][] = new int[len][len];

        // Set for a language starting with 0 and then 1s
        HashSet<String> L = new HashSet<>();
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

        method2(inStr, L, arr);
    }

    private static void method2(String inStr, HashSet<String> L, int[][] arr) {
        int len = inStr.length();
        // stores last connected vertex, so we can find path from vertex 0 to n
        int k = 0;

        for (int i = 0; i < len; i++) {
            // saves the last index of the largest substring found in inStr
            int lastSubStr = i;

            for (int j = i; j < len; j++) {

                String temp = inStr.substring(i, j + 1);
                if (L.contains(temp)) {
                    arr[i][j] = 1;
                    arr[j][i] = 1;

                    arr[k][j] = 1;
                    arr[j][k] = 1;
                    k = i;
                    lastSubStr = j;
                }
            }

            if (lastSubStr == i) {
                // if substring was not contained in L,
                // set k to next i (last possible connected vertex)
                k = i + 1;
            } else {
                // increment i by index of lastSubStr because we
                // already know longest substring in this iteration
                i = lastSubStr;
            }
        }

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

        // TODO Accept if there is a path from vertex 0 to n
        if (arr[0][len - 1] == 1) {
            System.out.println("Accept");
        } else {
            System.out.println("Reject");
        }
    }

    private static void method1(String inStr, HashSet<String> L, int[][] arr) {
        int len = inStr.length();

        if (inStr.isEmpty()) {
            System.out.println("Accept");
        }

        for (int i = 0; i < len; i++) {
            if ( L.contains(Character.toString(inStr.charAt(0))) ) {
                arr[i][i] = 1;
            }
        }

        for (int l = 1; l < len; l++) {
            for (int i = 0; i < (len - l); i++) {
                int j = i + l;

                String temp = inStr.substring(i, j + 1);
                if (L.contains(temp)) {
                    arr[i][i] = 1;
                }

                for (int k = i; k <= (j - 1); k++) {
                    if (arr[i][k] == 1 && arr[k][j] == 1) {
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
