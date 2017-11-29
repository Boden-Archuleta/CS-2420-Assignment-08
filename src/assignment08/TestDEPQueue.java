package assignment08;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDEPQueue {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAdding() {
		DEPQueue list = new DEPQueue();
		list.insert("b");
		list.insert("c");
		list.insert("a");
		
		list.printList();
	}

}
