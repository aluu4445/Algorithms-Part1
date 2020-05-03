/* *****************************************************************************
 *  @author Andy Luu
 *  @description Implements a double-ended queue (or deque) that supports adding and removing items from either end
 **************************************************************************** */


import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;

    private int length;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        length = 0;
    }


    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return length;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newNode = new Node();
        newNode.item = item;

        // if deque is empty
        if (first == null) {
            first = newNode;
            last = newNode;
            first.next = null;
            first.prev = null;

        }
        else {
            Node oldFirst = first;
            first = newNode;
            first.next = oldFirst;
            first.prev = null;
            oldFirst.prev = first;
        }

        length += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newNode = new Node();
        newNode.item = item;

        // if deque is empty
        if (first == null) {
            first = newNode;
            last = newNode;
            first.prev = null;
            last.next = null;

        }
        else {
            Node oldLast = last;
            last = newNode;
            oldLast.next = newNode;
            newNode.prev = oldLast;
            newNode.next = null;
        }

        length += 1;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        if (this.isEmpty()) last = null;
        length -= 1;

        return item;


    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.prev;

        length -= 1;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> tester = new Deque<Integer>();

        System.out.println(tester.isEmpty());
        System.out.println("true");

        System.out.println(tester.size());
        System.out.println("0");

        tester.addFirst(3);
        System.out.println(tester.size());
        System.out.println("1");

        tester.addFirst(2);
        tester.addFirst(1);
        System.out.println(tester.size());
        System.out.println("3");

        tester.addLast(4);
        System.out.println(tester.size());
        System.out.println("4");

        System.out.println(tester.size());

        for (Object num : tester) {
            System.out.println(num);
        }
        System.out.println("1,2,3,4");

        System.out.println(tester.removeFirst());
        System.out.println("1");
        System.out.println(tester.removeLast());
        System.out.println("4");
        System.out.println(tester.removeLast());
        System.out.println("3");

        System.out.println("Testing deque");
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        System.out.println(deque.removeLast());
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);
        deque.addFirst(7);
        deque.addFirst(8);
        System.out.println(deque.removeLast());


    }

}
