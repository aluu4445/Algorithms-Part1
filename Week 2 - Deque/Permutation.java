/* *****************************************************************************
 *  @author Andy Luu
 *  @description Client to read an intenger k as a command line-argument, read a sequence of strings from standard input and print exactly k of them uniformly at random
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> tester = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            tester.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            System.out.println(tester.dequeue());
        }
    }
}

