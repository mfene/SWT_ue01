package at.jku.swtesting;

/**
 * The RingBuffer class represents a last-in-first-out (LIFO) circular queue of
 * elements. It has a maximum capacity of elements it can hold. If more elements
 * are added, the last element will overwrite the first one.
 *
 * Originally derived from
 * http://www.cs.princeton.edu/introcs/43stack/RingBuffer.java.html
 */
public class RingBuffer<Item> {

	private Item[] a; // queue elements
	private int N = 0; // number of elements on queue
	private int first = 0; // index of first element of queue
	private int last = 0; // index of next available slot

	/**
	 * Creates a new empty ring buffer.
	 * 
	 * @param capacity number of elements the buffer is able to hold.
	 * @throws IllegalArgumentException if the initial capacity is less than one.
	 */
	@SuppressWarnings("unchecked")
	public RingBuffer(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("Initial capacity is less than one");
		}
		// cast needed since no generic array creation in Java
		a = (Item[]) new Object[capacity];
	}

	/**
	 * Changes the capacity of the buffer.
	 * 
	 * @throws IndexOutOfBoundsException if new capacity is less than the size of
	 *                                   the buffer.
	 */
	@SuppressWarnings("unchecked")
	public void setCapacity(int newCapacity) throws IndexOutOfBoundsException {
		if (newCapacity < size()) {
			throw new IndexOutOfBoundsException("New capacity is too low.");
		}

		boolean isFull = isFull();

		Item[] aOld = a;
		a = (Item[]) new Object[newCapacity];

		if (isFull) {
			last = 0;
			for (int i = 0; i < aOld.length; i++) {
				a[i] = aOld[i];
				last++;
			}
		} else {
			for (int i = 0; i < N; i++) {
				a[i] = aOld[i];
			}
		}

	}

	/**
	 * Returns the number of elements the buffer can hold.
	 */
	public int capacity() {
		return a.length;
	}

	/**
	 * Returns the number of elements in the buffer.
	 */
	public int size() {
		return N;
	}

	/**
	 * Returns true if the buffer contains no elements.
	 */
	public boolean isEmpty() {
		return N == 0;
	}

	/**
	 * Returns true if the buffer has reached its capacity, which is the maximum
	 * number of elements it can hold, before overwriting elements.
	 */
	public boolean isFull() {
		return N == a.length;
	}

	/**
	 * Appends the specified element to the end of the buffer. If the buffer has
	 * already reached its capacity, appending overwrites the first element in the
	 * buffer.
	 * 
	 * @param item to be appended to the buffer.
	 */
	public void enqueue(Item item) {
		a[last] = item;
		last = (last + 1) % a.length; // wrap-around
		if (N < a.length) {
			N++;
		} else {
			first = (first + 1) % a.length;
		}
	}

	/**
	 * Removes the first element from the buffer.
	 * 
	 * @throws RuntimeException if the buffer is empty.
	 */
	public Item dequeue() throws RuntimeException {
		if (isEmpty()) {
			throw new RuntimeException("Empty ring buffer.");
		}
		Item item = a[first];
		a[first] = null;
		N--;
		first = (first + 1) % a.length; // wrap-around
		return item;
	}

	/**
	 * Returns the first element from the buffer without removing it.
	 * 
	 * @throws RuntimeException if the buffer is empty.
	 */
	public Item peek() {
		if (isEmpty()) {
			throw new RuntimeException("Empty ring buffer.");
		}
		return a[first];
	}

}
