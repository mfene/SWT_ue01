package at.jku.swtesting;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class RingBufferTest {

	RingBuffer<String> rb;

	private static final String ELEM = "elem";
	private static final String[] ELEMS = { "e1", "e2", "e3", "e4", "e5", "e6" };

	@BeforeEach
	public void beforeEach() {
		rb = new RingBuffer<>(5);
	}

	@Test
	public void testCapacity() {
		assertEquals(5, rb.capacity());
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
		for (int i = 0; i < ELEMS.length - 1; i++)
			rb.enqueue(ELEMS[i]);
		assertTrue(rb.isFull());
	}

	@Test
	public void testIsEmpty() {
		assertTrue(rb.isEmpty());
		rb.enqueue("");
		assertFalse(rb.isEmpty());
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
