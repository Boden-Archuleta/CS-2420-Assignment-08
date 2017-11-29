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
		list.insert("8");
		list.insert("5");
		list.insert("3");
		list.insert("9");
		list.insert("1");
		list.insert("2");
		list.insert("4");
		list.insert("6");
		list.insert("7");

		//list.printList();
	}
	
	@Test
	public void testRemove(){
		DEPQueue list = new DEPQueue();
		list.insert("2");
		list.insert("3");
		list.insert("5");
		list.insert("7");
		list.insert("6");
		list.insert("1");
		list.insert("4");
		list.insert("9");
		list.insert("8");

		System.out.println(list.removeMax());
		list.printList();
	}

}
