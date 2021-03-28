package ssz.practice;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class MainTesting {

    private static void removeDuplicatesLL() {
        LinkedList<Integer> ll2 = new LinkedList<>();

        for (int i = 0; i < 100; i++) {
            ll2.add(i*10);
            ll2.add(i);
        }
        System.out.println(ll2.toString());
        System.out.println(ll2.size());

        // Remove duplicates from linked list
        // Hashmap to store Integer key (data of the Node in linked list) and Integer value for the number of times
        // the key occurred.
        HashMap<Integer, Integer> hm = new HashMap<>();
        // create iterator since can not go inside LinkedList class to make method for removing duoplicates
        ListIterator<Integer> ll2Itr = ll2.listIterator(0);
        System.out.println("Removing duplicates...");
        while (ll2Itr.hasNext()) {
            int curElement = ll2Itr.next();

            if (hm.containsKey(curElement)) {
                ll2Itr.remove();
            } else {
                hm.put(curElement, 1);
            }
        }

        System.out.println(ll2.toString());
        System.out.println(ll2.size());
    }

    private static void revWordsInString() {
        // Reverse words in a string
        String s1 = new String("Hello my name is Soma and I would like to reverse this.");
        Scanner scan = new Scanner(s1);

        StringBuilder revBuild = new StringBuilder();
        while (scan.hasNext()) {
            revBuild.insert(0, scan.next() + " ");
        }
        System.out.println(revBuild.toString());
    }

    /**
     * Hackerrank: Hash Tables: Ransom Note problem
     * @param magazine
     * @param note
     */
    // Complete the checkMagazine function below.
    static void checkMagazine(String[] magazine, String[] note) {
        if (note.length > magazine.length) {
            System.out.println("No");
            return;
        }

        HashMap<String, Integer> hmMagazine = generateHM(magazine);
//        HashMap<String, Integer> hmNote = generateHM(note);

        for (int i = 0; i < note.length; i++) {
            String curKey = note[i];

            if (hmMagazine.containsKey(curKey) && (hmMagazine.get(curKey) > 0)) {
                hmMagazine.replace(curKey, hmMagazine.get(curKey) - 1);
            } else {
                System.out.println("No");
                return;
            }
        }
        System.out.println("Yes");
    }

    /**
     * Returns a hashmap representation of the string array with a key for each string in the array a value for the
     * number of times that string (key) has occurred.
     * @param arr of strings
     * @return HashMap
     */
    private static HashMap<String, Integer> generateHM(String[] arr) {
        HashMap<String, Integer> hmRet = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            String curKey = arr[i];

            // if contains key increment value
            if (hmRet.containsKey(curKey)) {
                hmRet.replace(curKey, hmRet.get(curKey) + 1);
            } else {
                hmRet.put(curKey, 1);
            }
        }
        return hmRet;
    }

    /**
     * HackerRank: Sherlock and Anagrams
     * @param s
     * @return
     */
    // Complete the sherlockAndAnagrams function below.
    private static int sherlockAndAnagrams(String s) {


        return 0;
    }

    public static void main(String[] args) {
//        System.out.println("Hello Soma!");
//        removeDuplicatesLL();
//        revWordsInString();

    }
}
