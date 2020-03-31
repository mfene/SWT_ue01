package at.jku.swtesting;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class RingBufferTest {

	RingBuffer<String> rb;

	private static final String ELEM = "elem";
	private static final String[] ELEMS = { "e1", "e2", "e3", "e4", "e5", "e6" };
	private static final int TESTCAPACITY = 5;

	@BeforeEach
	public void beforeEach() {
		rb = new RingBuffer<>(TESTCAPACITY);
	}

	@Test
	public void testCapacity() {
		assertEquals(TESTCAPACITY, rb.capacity());
	}

	@Test
	public void testConstructorIllegalArgException() {
		assertThrows(IllegalArgumentException.class, () -> new RingBuffer<>(0));
	}

	@Test
	public void testSizeEnqueueDequeue() {
		assertEquals(0, rb.size());
		rb.enqueue(ELEM);
		assertEquals(1, rb.size());
		assertEquals(ELEM, rb.dequeue());
		assertEquals(0, rb.size());
	}

	@Test
	public void testDequeueRuntimeException() {
		assertThrows(RuntimeException.class, () -> rb.dequeue());
	}

	@Test
	public void testEnqueueOverwrite() {
		for (String elem : ELEMS)
			rb.enqueue(elem);
		assertEquals(ELEMS[1], rb.peek());
	}

	@Test
	public void testPeek() {
		assertThrows(RuntimeException.class, () -> rb.peek());
		rb.enqueue(ELEM);
		assertEquals(ELEM, rb.peek());
		assertEquals(ELEM, rb.peek()); // Test if the item hasn't been removed by peek
	}

	@Test
	public void testIsFull() {
		assertFalse(rb.isFull());
		for (int i = 0; i < TESTCAPACITY; i++)
			rb.enqueue(ELEMS[i]);
		assertTrue(rb.isFull());
	}

	@Test
	public void testIsEmpty() {
		assertTrue(rb.isEmpty());
		rb.enqueue("");
		assertFalse(rb.isEmpty());
	}

	@Test
	public void testPartialFillAndEmpty() {
		String[] arrayIn = { "e1", "e2", "e3" };
		String[] arrayOutExpected = { "e1", "e2", "e3"};

		assertTrue(rb.isEmpty());
		assertFalse(rb.isFull());
		assertEquals(TESTCAPACITY, rb.capacity());
		assertEquals(0, rb.size());

		for(String elem : arrayIn) rb.enqueue(elem);

		assertFalse(rb.isEmpty());
		assertFalse(rb.isFull());
		assertEquals(TESTCAPACITY, rb.capacity());
		assertEquals(arrayIn.length, rb.size());

		for(String elem : arrayOutExpected) assertEquals(elem, rb.dequeue());

		assertTrue(rb.isEmpty());
		assertFalse(rb.isFull());
		assertEquals(TESTCAPACITY, rb.capacity());
		assertEquals(0, rb.size());
	}

	@Test
	public void testOverfillAndEmpty() {

		String[] arrayIn = { "e1", "e2", "e3", "e4", "e5", "e6" };
		String[] arrayOutExpected = { "e2", "e3", "e4", "e5", "e6" };

		assertTrue(rb.isEmpty());
		assertFalse(rb.isFull());
		assertEquals(TESTCAPACITY, rb.capacity());
		assertEquals(0, rb.size());

		for(String elem : arrayIn) rb.enqueue(elem);

		assertFalse(rb.isEmpty());
		assertTrue(rb.isFull());
		assertEquals(TESTCAPACITY, rb.capacity());
		assertEquals(arrayOutExpected.length, rb.size());

		for(String elem : arrayOutExpected) assertEquals(elem, rb.dequeue());

		assertTrue(rb.isEmpty());
		assertFalse(rb.isFull());
		assertEquals(TESTCAPACITY, rb.capacity());
		assertEquals(0, rb.size());
	}

	/**
	 * start of tests for task 2
	 */

	@Test
	public void testSetCapacity() {
		rb.setCapacity(6);
		assertEquals(6, rb.capacity());
	}

	@Test
	public void testSetZeroCapacity() {
		assertThrows(IndexOutOfBoundsException.class, () -> rb.setCapacity(0));
	}

	@Test
	public void testSetNegativeCapacity() {
		assertThrows(IndexOutOfBoundsException.class, () -> rb.setCapacity(-1));
	}

	@Test
	public void testSetCapacityHigherThenLower() {
		rb.setCapacity(TESTCAPACITY+1);
		assertEquals(TESTCAPACITY+1, rb.capacity());
		rb.setCapacity(TESTCAPACITY-1);
		assertEquals(TESTCAPACITY-1, rb.capacity());
	}

	@Test
	public void testSetCapacityLowerThenHigher() {
		rb.setCapacity(TESTCAPACITY-1);
		assertEquals(TESTCAPACITY-1, rb.capacity());
		rb.setCapacity(TESTCAPACITY+1);
		assertEquals(TESTCAPACITY+1, rb.capacity());
	}


	@Test
	public void testSetCapThenFillAndEmpty() {
		rb.setCapacity(ELEMS.length);
		assertTrue(rb.isEmpty());

		for(String elem : ELEMS) rb.enqueue(elem);

		assertFalse(rb.isEmpty());
		assertTrue(rb.isFull());

		for (String elem : ELEMS) assertEquals(elem, rb.dequeue());

		assertTrue(rb.isEmpty());
		assertFalse(rb.isFull());
	}

	@Test
	public void testSetCapacityEnqueueDequeue() {
		for (String elem : ELEMS)
			rb.enqueue(elem);
		assertEquals(ELEMS[1], rb.peek());
		assertTrue(rb.isFull());
		rb.setCapacity(6);
		assertFalse(rb.isFull());
		assertEquals(ELEMS[1], rb.peek());
		rb.enqueue("e7");
		assertEquals(ELEMS[1], rb.peek());
		assertEquals(ELEMS[1], rb.dequeue());
	}

	@Test
	public void testSetCapacityIsFull() {
		for (String elem : ELEMS)
			rb.enqueue(elem);
		rb.setCapacity(6);
		assertFalse(rb.isFull());
		rb.enqueue("e7");
		assertTrue(rb.isFull());
	}

	@Test
	public void testSetCapacityException() {
		assertThrows(IndexOutOfBoundsException.class, () -> rb.setCapacity(4));
	}

}
