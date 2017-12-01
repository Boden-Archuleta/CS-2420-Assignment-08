package assignment08;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * @author Boden Archuleta
 *
 */
public class DEPQueue {
	
	/*
	 * Node class used to store data values in both heaps
	 */
	private class Node {
		private String data;
		private int minHeapIndex;
		private int maxHeapIndex;
		
		Node(String d){
			data = d;
			minHeapIndex = 0;
			maxHeapIndex = 0;
		}
		
		public void updateMinHeapIndex(int index){
			minHeapIndex = index;
		}
		
		public void updateMaxHeapIndex(int index){
			maxHeapIndex = index;
		}
	}
	
	private long comparisonCount;
	private long swapCount;
	private int size;
	private ArrayList<Node> minHeap;
	private ArrayList<Node> maxHeap;
	
	
	DEPQueue(){
		comparisonCount = 0;
		swapCount = 0;
		size = 0;
		minHeap = new ArrayList<Node>();
		maxHeap = new ArrayList<Node>();
	}
	
	/**
	 * Inserts a string into both heaps
	 * 
	 * @param data 
	 * 			The string that will be inserted into the heaps
	 */
	public  void  insert (String data){
		Node newNode = new Node(data);
		this.size++;
		
		minHeap.add(newNode);
		newNode.updateMinHeapIndex(minHeap.size() - 1);
		minRecurseUp(newNode.minHeapIndex);
		
		maxHeap.add(newNode);
		newNode.updateMaxHeapIndex(maxHeap.size() - 1);
		maxRecurseUp(newNode.maxHeapIndex);		
	}
	
	/**
	 * Helper method that switches the nodes at the given indices
	 * in the minHeap and updates the index fields of both nodes
	 * @param greaterIndex
	 * @param lesserIndex
	 */
	private void swapMin(int greaterIndex, int lesserIndex){
		Node temp = minHeap.get(lesserIndex);
		minHeap.set(lesserIndex, minHeap.get(greaterIndex));
		minHeap.set(greaterIndex, temp);
		
		minHeap.get(greaterIndex).updateMinHeapIndex(greaterIndex);
		minHeap.get(lesserIndex).updateMinHeapIndex(lesserIndex);
		this.swapCount++;
	}
	
	/**
	 * Helper function that switches the nodes at the given indices 
	 * in the maxHeap. Also updates the index fields in both nodes. 
	 * @param greaterIndex
	 * @param lesserIndex
	 */
	private void swapMax(int greaterIndex, int lesserIndex){
		Node temp = maxHeap.get(lesserIndex);
		maxHeap.set(lesserIndex, maxHeap.get(greaterIndex));
		maxHeap.set(greaterIndex, temp);
		
		maxHeap.get(greaterIndex).updateMaxHeapIndex(greaterIndex);
		maxHeap.get(lesserIndex).updateMaxHeapIndex(lesserIndex);
		this.swapCount++;
	}
	
	/**
	 * Removes the highest priority string from the queue
	 * @return
	 * 			Returns the removed string
	 */
	public String removeMin(){
		if (maxHeap.isEmpty() || minHeap.isEmpty())
			throw new NoSuchElementException();
		
		this.size--;
		String returnData = minHeap.get(0).data;
		int index = minHeap.get(0).maxHeapIndex;
		
		//remove from min heap
		swapMin(0, minHeap.size() - 1);
		minHeap.remove(minHeap.size() - 1);
		fixMinHeap(0);
		
		//remove from max heap
		if (index == maxHeap.size() - 1){
			maxHeap.remove(index);
		}
		else {
			swapMax(index, maxHeap.size() - 1);
			maxHeap.remove(maxHeap.size() - 1);
			fixMaxHeap(index);
		}
		
		return returnData;
	}
	
	/**
	 * Removes the lowest priority string from the queue
	 * @return
	 * 			Returns the removed string
	 */
	public String removeMax(){
		if (maxHeap.isEmpty() || minHeap.isEmpty())
			throw new NoSuchElementException("Tried to remove an element from an empty list");
		
		this.size--;
		String returnData = maxHeap.get(0).data;
		int index = maxHeap.get(0).minHeapIndex;
		
		//remove from max heap
		swapMax(0, maxHeap.size() - 1);
		maxHeap.remove(maxHeap.size() - 1);
		fixMaxHeap(0);
		
		//remove from min heap
		if (index == minHeap.size() - 1){
			minHeap.remove(index);
		}
		else {
			swapMin(index, minHeap.size() - 1);
			minHeap.remove(minHeap.size() - 1);
			fixMinHeap(index);
		}
		
		return returnData;
	}
	
	/**
	 * If the given index is the root, the recurse down method is called
	 * if the given index is a leaf node, the recurse up method is called
	 * @param index
	 * 			index of a node in the minHeap
	 */
	private void fixMinHeap(int index){
		if (index == 0)
			minRecurseDown(index);
		else
			minRecurseUp(index);
	}
	
	/**
	 * If the given index is the root, the recurse down method is called
	 * if the given index is a leaf node, the recurse up method is called
	 * @param index
	 * 			index of a node in the minHeap
	 */
	private void fixMaxHeap(int index){
		if (index == 0)
			maxRecurseDown(index);
		else
			maxRecurseUp(index);
	}
	
	/**
	 * Recursively updates the minHeap starting at a root node and moving down
	 * @param root
	 * 			A root of a subtree
	 */
	private void minRecurseDown(int root){
		int left = root * 2 + 1;
		int right = root * 2 + 2;
		int min = root;
		
		this.comparisonCount += 2;
		if (left < minHeap.size() && minHeap.get(left).data.compareTo(minHeap.get(root).data) < 0){
			min = left;
		}
		if (right < minHeap.size() && minHeap.get(right).data.compareTo(minHeap.get(min).data) < 0){
			min = right;
		}
		
		if (min != root){
			swapMin(root, min);
			minRecurseDown(min);
		}
	}
	
	/**
	 * Recursively updates the maxHeap starting at a root and moving down
	 * @param root
	 * 			A root of a subtree
	 */
	private void maxRecurseDown(int root){
		int left = root * 2 + 1;
		int right = root * 2 + 2;
		int min = root;
		
		this.comparisonCount += 2;
		if (left < maxHeap.size() && maxHeap.get(left).data.compareTo(maxHeap.get(root).data) > 0){
			min = left;
		}
		if (right < maxHeap.size() && maxHeap.get(right).data.compareTo(maxHeap.get(min).data) > 0){
			min = right;
		}
		
		if (min != root){
			swapMax(root, min);
			maxRecurseDown(min);
		}
	}
	
	/**
	 * Recursively updates the maxHeap starting from a leaf node and moving up
	 * @param current
	 * 			Index of the current node
	 */
	private void maxRecurseUp(int current){
		int parent = (current - 1) / 2;
		if (parent < 0)
			return;
		
		this.comparisonCount++;
		if (maxHeap.get(current).data.compareTo(maxHeap.get(parent).data) > 0){
			swapMax(parent, current);
			maxRecurseUp(parent);
		}
	}
	
	/**
	 * Recursively updates the maxHeap starting from a leaf node and moving up
	 * @param current
	 * 			Index of the current node
	 */
	private void minRecurseUp(int current){
		int parent = (current - 1) / 2;
		if (parent < 0)
			return;
		
		this.comparisonCount++;
		if (minHeap.get(current).data.compareTo(minHeap.get(parent).data) < 0){
			swapMin(parent, current);
			minRecurseUp(parent);
		}
	}
	
	public int size(){		
		return this.size;
	}
	
	public long getComparisonCount (){
		return this.comparisonCount;
	}
	
	public long getSwapCount(){
		return this.swapCount;
	}
	
	/**
	 * helper method for debugging. Prints the string and index fields of 
	 * every node in both heaps. Make public to use in testing.
	 */
	private void printList(){
		System.out.println("MinHeap:");
		for (Node n : minHeap){
			System.out.println("data: " + n.data + " minIndex: " + n.minHeapIndex + " maxIndex " + n.maxHeapIndex);
		}
		
		System.out.println("\nMaxHeap");
		for (Node n : maxHeap){
			System.out.println("data: " + n.data + " minIndex: " + n.minHeapIndex + " maxIndex " + n.maxHeapIndex);
		}
	}
}
