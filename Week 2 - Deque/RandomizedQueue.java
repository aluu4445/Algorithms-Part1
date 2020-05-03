/* *****************************************************************************
 *  @author Andy Luu
 *  @description Implements a randomised queue where the item removed is chosen uniformly at random
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int n = 0;


    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }


    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == s.length) {
            resize(2 * s.length);
        }
        s[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int rand = StdRandom.uniform(0, n);
        Item item = s[rand];
        if (rand == n - 1) {
            s[rand] = null;
        }
        else {
            s[rand] = s[n - 1];
            s[n - 1] = null;
        }
        n--;

        if (n > 0 && n == s.length / 4) {
            resize(s.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return s[StdRandom.uniform(0, n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private int i = n;
        private int[] indices = StdRandom.permutation(n);

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            i--;
            return s[indices[i]];


        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> tester = new RandomizedQueue<Integer>();

        System.out.println(tester.isEmpty());
        System.out.println("true");

        System.out.println(tester.size());
        System.out.println("0");

        tester.enqueue(3);
        System.out.println(tester.size());
        System.out.println("1");

        tester.enqueue(2);
        tester.enqueue(1);
        System.out.println(tester.size());
        System.out.println("3");

        tester.enqueue(4);
        System.out.println(tester.size());
        System.out.println("4");

        System.out.println("Sample:");
        System.out.println(tester.sample());

        System.out.println("testing iterator");
        for (Object num : tester) {
            System.out.println(num);
        }
        System.out.println("1,2,3,4 in random order");

        System.out.println(tester.sample());

        System.out.println(tester.n);

        System.out.println("Random:");
        System.out.println(tester.size());

        System.out.println(tester.sample());
        System.out.println(tester.sample());
        System.out.println(tester.dequeue());
        System.out.println(tester.size());
        System.out.println(tester.dequeue());
        System.out.println(tester.size());


    }

}
