package ssz.practice;

import java.util.*;

public class Main {
    // For Question 2
    private static int[][] memTable;

    public static class ListPair {
        public ArrayList<Integer> aList;
        public int numPermutations;

        public ListPair() {
            aList = new ArrayList<Integer>();
            numPermutations = 1;
        }

        public ListPair(ArrayList<Integer> al, int n) {
            aList = al;
            numPermutations = n;
        }
    }

    public static void main(String[] args) {
        // Question 1:
        ArrayList<Integer> al = new ArrayList<>();
//        al.add(-2);
//        al.add(-7);
//        al.add(2);
//        al.add(7);
        al.add(10);

//        al.add(1);
        al.add(3);
        al.add(-2);
        Elevator(11, al, 1, 11);


        // Question 2:
        int n = 4;     // red cookies
        int m = 4;      // green cookies
        // TODO used for CookieGame1 and 2
        memTable = new int[n + 1][m + 1];
//        for (int i = 0; i < n + 1; i++) {
//            for (int j = 0; j < m + 1; j++) {
//                memTable[i][j] = -1;
//            }
//        }

        String winner = "No winning strategy";
        int resultR = CookieGame(n, m);
        int resultM = -1;

//        if ((n >= 2 && m >= 1) || (n >= 1 && m >= 2)) {
//            resultR = CookieGame2(n, m, 1);
//            // reset memTable
//            for (int i = 0; i < n + 1; i++) {
//                for (int j = 0; j < m + 1; j++) {
//                    memTable[i][j] = -1;
//                }
//            }
//            resultM = CookieGame1(n, m, 1);
//        }

        if (resultR == 1) {
            winner = "Riley";
        } else if (resultR == 0) {
            winner = "Morgan";
        }

        System.out.println("Q2 Answer: " + winner);
    }

    private static void Elevator(int n, List<Integer> B, int i, int j) {
        // Create HashMap for storing current floor possibilities with a sequence to get there and numPermutations
        HashMap<Integer, ListPair> hm = new HashMap<>();
        ListPair lp = new ListPair();
        lp.aList.add(i);
        hm.put(i, lp);

        // set of visited floors
        HashSet<Integer> hsVisited = new HashSet<>();
        hsVisited.add(i);

        int floorsAdded = 1;
        while (!hm.containsKey(j) && floorsAdded > 0) {     // O(lg(n)) ?
            floorsAdded = 0;

            // Go through all keys in hashmap (possible elevator levels reached by button presses)
            Object[] arr = hm.keySet().toArray();
            for (int l = 0; l < arr.length; l++) {          // O(n) (number of levels)
                int curKey = Integer.parseInt(arr[l].toString());

                // Go though each button for current floor possibility
                for (int k = 0; k < B.size(); k++) {        // O(B) (number of buttons)
                    int nextFloor = curKey + B.get(k);

                    // check if number of permutations needs to be increased
                    if (hm.containsKey(nextFloor)) {
                        hm.replace(nextFloor, new ListPair(hm.get(nextFloor).aList,
                                hm.get(curKey).numPermutations + hm.get(nextFloor).numPermutations));
                        floorsAdded++;
                    }

                    // next floor must be between [1-n] and should not be a visited floor
                    if (nextFloor <= n && nextFloor > 0 && !hsVisited.contains(nextFloor)) {
                        // Get all list values of curKey then update it with nextFloor
                        ArrayList<Integer> temp = new ArrayList<>();
                        temp.addAll(hm.get(curKey).aList);        // O(1) (get list corresponding to key)

                        temp.add(nextFloor);       // O(1)
                        // put new key, value pair in hashmap
                        hm.put(nextFloor, new ListPair(temp, hm.get(curKey).numPermutations));            // O(1)

                        // put new key in hsVisited
                        hsVisited.add(nextFloor);
                        floorsAdded++;
                    }
                }
                hm.remove(curKey);
            }
        }

        if (hm.containsKey(j)) {
            // Get shortest sequence of button presses
            printButtons(hm.get(j).aList);

            // print sequence of floors
            System.out.println(hm.get(j).aList);

            // Print number of sequences of button presses
            System.out.println(hm.get(j).numPermutations);
        } else {
            System.out.println("NO");
        }
    }

    private static void printButtons(List<Integer> l) {
        ArrayList<Integer> result = new ArrayList<>();
        int prev = l.get(0);
        for (int i = 1; i < l.size(); i++) {
            int cur = l.get(i);
            result.add(cur - prev);
            prev = cur;
        }
        System.out.println(result);
    }

    private static int CookieGame2(int n, int m, int player) {
        if ((n <= 0 || m <= 0) || (n <= 1 && m <= 1)) {
            return (player == 1) ? 0 : 1;
        }

        if (memTable[n][m] != -1) {
            return memTable[n][m];
        }

        player = (player == 1) ? 0 : 1;
        int choice1 = CookieGame2(n - 2, m - 1, player);
        int choice2 = CookieGame2(n - 1, m - 2, player);
        memTable[n][m] = Math.max(choice1, choice2);

        return memTable[n][m];
    }

    private static int CookieGame1(int n, int m, int player) {
        if ((n <= 0 || m <= 0) || (n <= 1 && m <= 1)) {
            return (player == 1) ? 0 : 1;
        }

        if (memTable[n][m] != -1) {
            return memTable[n][m];
        }

        player = (player == 1) ? 0 : 1;
        int choice1 = CookieGame1(n - 2, m - 1, player);
        int choice2 = CookieGame1(n - 1, m - 2, player);
        memTable[n][m] = Math.min(choice1, choice2);

        return memTable[n][m];
    }

    private static int CookieGame(int n, int m) {
        if (n < 0 || m < 0) {
            return 1;
        }

        if ((n == 0 || m == 0) || (m == 1 && n == 1)) {
            return 0;
        }

        if (memTable[n][m] == 1) {
            return 1;
        }

        // TODO maybe don't need -3 part?
        memTable[n][m] = Math.max(Math.min(CookieGame(n - 4, m - 2), CookieGame(n - 3, m - 3)),
                Math.min(CookieGame(n - 2, m - 4), CookieGame(n - 3, m - 3)));
//        memTable[n][m] = Math.max(CookieGame(n - 2, m - 1), CookieGame(n - 1, m - 2));

        return memTable[n][m];
    }

}
