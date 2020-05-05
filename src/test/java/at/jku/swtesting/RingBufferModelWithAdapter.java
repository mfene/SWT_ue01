package at.jku.swtesting;

import nz.ac.waikato.modeljunit.Action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RingBufferModelWithAdapter extends RingBufferSimpleModel {

    private final RingBuffer<String> buffer = new RingBuffer<>(CAPACITY);
    private int count = 0;
    private static final String insert = "Test";

    @Override
    @Action
    public void enqueue() {
        super.enqueue();
        buffer.enqueue(insert);
    }

    @Override
    @Action
    public void dequeue() {
        super.dequeue();
        assertEquals(insert, buffer.dequeue());
    }

    @Override
    @Action
    public void peek() {
        super.peek();
        assertEquals(insert, buffer.peek());
    }

    @Override
    @Action
    public void dequeueFromEmptyBuffer() {
        super.dequeueFromEmptyBuffer();
        assertThrows(RuntimeException.class, buffer::dequeue);
    }

    @Override
    @Action
    public void peekOnEmptyBuffer() {
        super.peekOnEmptyBuffer();
        assertThrows(RuntimeException.class, buffer::peek);
    }
}
