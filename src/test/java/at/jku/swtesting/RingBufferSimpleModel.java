package at.jku.swtesting;

import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;

public class RingBufferSimpleModel implements FsmModel {

	protected static final int CAPACITY = 3;
	protected int size;

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

	public static void main(String[] args) {
		Tester tester = new RandomTester(new RingBufferSimpleModel());

		tester.buildGraph();
		tester.addListener(new VerboseListener());
		tester.addListener(new StopOnFailureListener());
		tester.addCoverageMetric(new ActionCoverage());
		tester.addCoverageMetric(new StateCoverage());
		tester.addCoverageMetric(new TransitionCoverage());

		tester.generate(10);

		tester.printCoverage();
	}

}
