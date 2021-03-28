package ssz.practice;

import java.util.*;

public class Main {

    class ListPair {
        public ArrayList<Integer> aList;
        public int numPermutations;

        public ListPair() {
            aList = new ArrayList<Integer>();
            numPermutations = 0;
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> al = new ArrayList<>();
        al.add(-5);
        al.add(2);
//        al.add(1);
//        al.add(-1);

        Elevator(72, al, 17, 16);
    }

    private static void Elevator(int n, List<Integer> B, int i, int j) {
        HashMap<Integer, List> hm = new HashMap<>();
        ArrayList<Integer> al = new ArrayList<>();
        al.add(0, i);
        hm.put(i, al);

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

                    // next floor must be between [1-n] and should not be a visited floor
                    if (nextFloor <= n && nextFloor > 0 && !hsVisited.contains(nextFloor)) {
                        // Get all list values of curKey then update it with nextFloor
                        List<Integer> temp = new ArrayList<>();
                        temp.addAll(hm.get(curKey));        // O(1) (get list corresponding to key)

                        temp.add(0, nextFloor);       // O(1)
                        // put new key, value pair in hashmap
                        hm.put(nextFloor, temp);            // O(1)

                        // put new key in hsVisited
                        hsVisited.add(nextFloor);
                        floorsAdded++;
                    }
                }
                hm.remove(curKey);
            }
        }

        if (hm.containsKey(j)) {
            // Print out sequence of buttons (could have method to iterate through floor and get buttons)
            System.out.println(hm.get(j));

            // TODO
            // Get number sequences
            printButtons(hm.get(j));
        } else {
            System.out.println("NO");
        }
    }

    private static void printButtons(List<Integer> l) {
        ArrayList<Integer> result = new ArrayList<>();
        int prev = l.get(0);
        for (int i = 1; i < l.size(); i++) {
            int cur = l.get(i);
            result.add(prev - cur);
            prev = cur;
        }
        System.out.println(result);
    }
}
