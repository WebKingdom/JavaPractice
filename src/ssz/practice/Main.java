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
        al.add(-5);
        al.add(2);
//        al.add(1);
//        al.add(-1);
        Elevator(72, al, 17, 16);


        // Question 2:
        int n = 3;
        int m = 4;
        memTable = new int[n + 1][m + 1];

        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < m + 1; j++) {
                memTable[i][j] = -1;
            }
        }

        String winner = "No winning strategy";
        int result = -1;

        if ((n >= 2 && m >= 1) || (n >= 1 && m >= 2)) {
            result = CookieGame2(n, m, 1);
        }

        if (result == 1) {
            winner = "Riley";
        } else if (result == 0) {
            winner = "Morgan";
        }

        System.out.println("Q2 Answer: " + winner);
    }

    private static void Elevator(int n, List<Integer> B, int i, int j) {
        HashMap<Integer, ListPair> hm = new HashMap<>();
        ListPair lp = new ListPair();
        lp.aList.add(0, i);
        hm.put(i, lp);

        // set of visited floors
        HashSet<Integer> hsVisited = new HashSet<>();
        hsVisited.add(i);

        int floorsAdded = 1;
        while (!hm.containsKey(j) && floorsAdded > 0) {     // O(n) ?
            floorsAdded = 0;

            // Go through all keys in hashmap (all possible elevator levels)
            Object[] arr = hm.keySet().toArray();
            for (int l = 0; l < arr.length; l++) {          // O(n) (number of levels)
                int curKey = Integer.parseInt(arr[l].toString());

                // Go though each button for current floor possibility
                for (int k = 0; k < B.size(); k++) {        // O(B) (number of buttons)
                    int nextFloor = curKey + B.get(k);

                    // check if number of permutations needs to be increased
                    if (hm.containsKey(nextFloor)) {
                        hm.replace(nextFloor, new ListPair(hm.get(nextFloor).aList, hm.get(curKey).numPermutations + 1));
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
//        memTable[n][m] = Math.max(choice1, choice2);

        return memTable[n][m] = Math.max(choice1, choice2);
    }

    private static int CookieGame(int n, int m) {
        if (n < 0 || m < 0) {
            return 1;
        }

        if ((n == 0 || m == 0) || (m == 1 && n == 1)) {
            return 0;
        }

        if (memTable[n][m] != -1) {
            return memTable[n][m];
        }

        memTable[n][m] = Math.max(CookieGame(n - 2, m - 1), CookieGame(n - 1, m - 2));

        return memTable[n][m];
    }

}
