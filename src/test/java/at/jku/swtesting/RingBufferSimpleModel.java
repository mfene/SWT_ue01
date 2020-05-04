package at.jku.swtesting;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;

public class RingBufferSimpleModel implements FsmModel {

	protected static final int CAPACITY = 3;
	protected int size;
	protected boolean empty;

	public Object getState() {
		if (size == 0) {
			return "EMPTY";
		} else if ((size > 0) && (size == CAPACITY)) {
			return "FULL";
		} else if ((size > 0) && (size < CAPACITY)) {
			return "FILLED";
		} else
			return "ERROR_UNEXPECTED_MODEL_STATE";
	}

	public void reset(boolean testing) {
		size = 0;
	}

	public boolean enqueueGuard() {
		return size < CAPACITY;
	}

	@Action
	public void enqueue() {
		size++;
	}

	public boolean dequeueGuard() {
		return size > 0;
	}

	@Action
	public void dequeue() {
		size--;
	}

	@Action
	public void peek() {
	}

	public boolean peekGuard() {
		return size > 0;
	}

	@Action
	public void dequeueFromEmptyBuffer() {
		size--;
	}

	public boolean dequeueFromEmptyBufferGuard() {
		return size == 0;
	}

	@Action
	public void peekOnEmptyBuffer() {

	}

	public boolean peekOnEmptyBufferGuard() {
		return size == 0;
	}
}
