package punemetro;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A generic Min-Heap implementation.
 * T must implement Comparable<T> so elements can be compared using compareTo.
 * @param <T> - the type of object stored in the heap (e.g., StationPair)
 */
public class Heap<T extends Comparable<T>> {
    
    private final ArrayList<T> data = new ArrayList<>();
    private final HashMap<T, Integer> map = new HashMap<>();

    // Adds an item to the heap
    public void add(T item) {
        data.add(item);
        map.put(item, data.size() - 1);
        upheapify(data.size() - 1);
    }

    // Removes and returns the minimum element
    public T remove() {
        if (data.isEmpty()) {
            throw new IllegalStateException("Heap is empty, cannot remove.");
        }

        swap(0, data.size() - 1);
        T removed = data.remove(data.size() - 1);
        map.remove(removed);

        if (!data.isEmpty()) {
            downheapify(0);
        }

        return removed;
    }

    // Returns the minimum (root) element without removing
    public T peek() {
        if (data.isEmpty()) {
            throw new IllegalStateException("Heap is empty, cannot peek.");
        }
        return data.get(0);
    }

    // Updates priority of an element
    public void updatePriority(T item) {
        Integer index = map.get(item);
        if (index == null) return; // Item not in heap

        upheapify(index);
        downheapify(index);
    }

    // Heapify upwards
    private void upheapify(int childIndex) {
        if (childIndex == 0) return;
        int parentIndex = (childIndex - 1) / 2;

        if (data.get(childIndex).compareTo(data.get(parentIndex)) < 0) {
            swap(childIndex, parentIndex);
            upheapify(parentIndex);
        }
    }

    // Heapify downwards
    private void downheapify(int parentIndex) {
        int minIndex = parentIndex;
        int leftChild = 2 * parentIndex + 1;
        int rightChild = 2 * parentIndex + 2;

        if (leftChild < data.size() && data.get(leftChild).compareTo(data.get(minIndex)) < 0) {
            minIndex = leftChild;
        }

        if (rightChild < data.size() && data.get(rightChild).compareTo(data.get(minIndex)) < 0) {
            minIndex = rightChild;
        }

        if (minIndex != parentIndex) {
            swap(minIndex, parentIndex);
            downheapify(minIndex);
        }
    }

    // Swap elements
    private void swap(int i, int j) {
        T temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);

        map.put(data.get(i), i);
        map.put(data.get(j), j);
    }

    // Utility methods
    public boolean isEmpty() { return data.isEmpty(); }
    public int size() { return data.size(); }
    public void display() { System.out.println(data); }

    // Optional contains() method
    public boolean contains(T item) {
        return map.containsKey(item);
    }
}
