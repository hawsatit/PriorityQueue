/**
 * A priority queue is a queue that maintains efficient access to the maximum
 * element of the queue.
 */
public class PriorityQueue<T extends Comparable<? super T>> {
    private static final int INITIAL_CAPACITY = 16;
    private T[] data;
    private int size;
  
    /**
     * Creates a new, empty priority queue.
     * @param capacity
     */
    @SuppressWarnings("unchecked")
    public PriorityQueue() {
        data = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            @SuppressWarnings("unchecked")
            T[] newData = (T[]) new Comparable[data.length * 2];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    // Part 1: Implement the following helper methods that help with
    // access parents and children in the heap.

    /** @return the index of the left child of the node at the given inex. */
    private static int getLeftChild(int index) {
        return index * 2 + 1;
    }

    /** @return the index of the right child of the node at the given inex. */
    private static int getRightChild(int index) {
        return index * 2 + 2;
    }

    /** @return the index of the parent of the node at the given index. */
    private static int getParent(int index) {
        if (index <= 0){
            return -1; 
        } else {
            return (index - 1) / 2;
        }
    }

    /** @return the number of elements in the queue */
    public int size() {
        return size;
    }

    /** @return the maximum element in the queue */
    public T peek() {
        if (size == 0) {
            throw new IllegalStateException("Priority queue is empty");
        } else {
            return data[0];
        }
    }

    // Part 2: Implement add(T v) that adds v to the queue, respecting the
    // heap property. You'll need to implement a "percolate up" helper method
    // along the way!

    /**
     * Adds value v to this priority queue.
     * @param v the value to add */
    public void add(T v) {
        ensureCapacity();

        data[size] = v;
        int curIndex = size;
        while (!isParent(curIndex) && v.compareTo(data[getParent(curIndex)]) > 0){
            swap(curIndex, getParent(curIndex), data);
            curIndex = getParent(curIndex);
        }

        this.size++;
    }

    private boolean isParent(int curIndex) {
        return getParent(curIndex) == -1;
    }

    private static <T> void swap(int ind1, int ind2, T[] data){
        T temp = data[ind1];
        data[ind1] = data[ind2];
        data[ind2] = temp;
    }

    // Part 3: Implement poll() that removes and returns the maximum element
    // in the queue, maintaining the heap property of the queue. You'll also
    // need to implement a "percolate down" helper, too.

    /** Removes and returns the maximum element of this priority queue.  */
    public T poll() {
        T retVal = this.data[0];
        swap(0, size - 1, data);
        int currentIndex = 0;
        size--;
        while(canPercolate(currentIndex)){
            if (leftChildIsValid(currentIndex) && rightChildIsValid(currentIndex)){
                if(data[getLeftChild(currentIndex)].compareTo(data[getRightChild(currentIndex)]) > 0){
                    swap(currentIndex, getLeftChild(currentIndex), data);
                    currentIndex = getLeftChild(currentIndex);
                } else {
                    swap(currentIndex, getRightChild(currentIndex), data);
                    currentIndex = getRightChild(currentIndex);
                }
            } else if (leftChildIsValid(currentIndex)){
                swap(currentIndex, getLeftChild(currentIndex), data);
                currentIndex = getLeftChild(currentIndex);
            } else { 
                swap(currentIndex, getRightChild(currentIndex), data);
                currentIndex = getRightChild(currentIndex);
            }
        }
        return retVal;
    }

/*
    private boolean canPercolate(int currentIndex) {
        if(!leftChildIsValid(currentIndex)&&!rightChildIsValid(currentIndex)){
            return false;
        }
        return data[currentIndex].compareTo(data[getLeftChild(currentIndex)] < 0 || data[currentIndex].compareTo(data[getRightChild(currentIndex)]) < 0;
    }
    */

    private boolean canPercolate(int currentIndex) {
        boolean canLeft = leftChildIsValid(currentIndex) &&
                        data[currentIndex].compareTo(data[getLeftChild(currentIndex)]) < 0;
        boolean canRight = rightChildIsValid(currentIndex) &&
                        data[currentIndex].compareTo(data[getRightChild(currentIndex)]) < 0;
        return canLeft || canRight;
    }


    private boolean rightChildIsValid(int currentIndex) {
        return getRightChild(currentIndex) < size;
    }

    private boolean leftChildIsValid(int currentIndex) {
        return getLeftChild(currentIndex) < size;
    }

    // Part 4: Write a main method that demonstrates that your priority
    // queue works! Try to cover a variety of use cases in your driver.
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        System.out.println("Adding elements: 10, 5, 6, 2, 4, 3");
        pq.add(10);
        pq.add(5);
        pq.add(6);
        pq.add(2);
        pq.add(4);
        pq.add(3);

        System.out.println("Peek max: " + pq.peek()); // Should print 10

        System.out.println("Polling elements in order:");
        while (pq.size() > 0) {
            System.out.print(pq.poll() + " "); // Should print: 10 6 5 4 3 2
        }
        System.out.println();
    }
}
