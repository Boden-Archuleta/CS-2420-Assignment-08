package assignment08;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class DEPQueue {
	
	private class Node {
		private String data;
		private int minHeapIndex;
		private int maxHeapIndex;
		
		Node(String d){
			data = d;
			minHeapIndex = 0;
			maxHeapIndex = 0;
		}
		
		public void updateMin(int index){
			minHeapIndex = index;
		}
		
		public void updateMax(int index){
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
		minHeap = new ArrayList<>();
		maxHeap = new ArrayList<>();
	}
	
	public  void  insert (String data){
		Node newNode = new Node(data);
		this.size++;
		insertInMin(newNode);
		insertInMax(newNode);
	}
	
	private void insertInMin(Node newNode){
		minHeap.add(newNode);
		newNode.updateMin(minHeap.size() - 1);
		
		minRecurseUp(newNode.minHeapIndex);
		
//		while(newNode.minHeapIndex != 0){
//			this.comparisonCount++;
//			int parentIndex = (newNode.minHeapIndex - 1) / 2;
//			
//			if (newNode.data.compareTo(minHeap.get(parentIndex).data) < 0)
//				swapMin(parentIndex, newNode.minHeapIndex);
//			else 
//				break;
//		}
	}
	
	private void insertInMax(Node newNode){
		maxHeap.add(newNode);
		newNode.updateMax(maxHeap.size() - 1);
		
		maxRecurseUp(newNode.maxHeapIndex);
				
//		while(newNode.maxHeapIndex != 0){
//			this.comparisonCount++;
//			int parentIndex = (newNode.maxHeapIndex - 1) / 2;
//					
//			if (newNode.data.compareTo(maxHeap.get(parentIndex).data) > 0)
//				swapMax(parentIndex, newNode.maxHeapIndex);
//			else 
//				break;
//		}
	}
	
	private void swapMin(int greaterIndex, int lesserIndex){
		Node temp = minHeap.get(lesserIndex);
		minHeap.set(lesserIndex, minHeap.get(greaterIndex));
		minHeap.set(greaterIndex, temp);
		
		minHeap.get(greaterIndex).updateMin(greaterIndex);
		minHeap.get(lesserIndex).updateMin(lesserIndex);
		this.swapCount++;
	}
	
	private void swapMax(int greaterIndex, int lesserIndex){
		Node temp = maxHeap.get(lesserIndex);
		maxHeap.set(lesserIndex, maxHeap.get(greaterIndex));
		maxHeap.set(greaterIndex, temp);
		
		maxHeap.get(greaterIndex).updateMax(greaterIndex);
		maxHeap.get(lesserIndex).updateMax(lesserIndex);
		this.swapCount++;
	}
	
	public String removeMin(){
		if (maxHeap.isEmpty() || minHeap.isEmpty())
			throw new NoSuchElementException("Tried to remove an element from an empty list");
		
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
	
	public String removeMax(){
		if (maxHeap.isEmpty() || minHeap.isEmpty())
			throw new NoSuchElementException("Tried to remove an element from an empty list");
		
		this.size--;
		String returnData = maxHeap.get(0).data;
		int index = maxHeap.get(0).minHeapIndex;
		
		//remove from max heap
		swapMax(0, maxHeap.size() - 1);
		maxHeap.remove(minHeap.size() - 1);
		fixMaxHeap(0);
		
		//remove from min heap
		if (index == minHeap.size() - 1){
			minHeap.remove(index);
		}
		else {
			swapMin(index, minHeap.size() - 1);
			maxHeap.remove(maxHeap.size() - 1);
			fixMinHeap(index);
		}
		
		return returnData;
	}
	
	private void fixMinHeap(int index){
		if (index == 0)
			minRecurseDown(index);
		else
			minRecurseUp(index);
	}
	
	private void fixMaxHeap(int index){
		if (index == 0)
			maxRecurseDown(index);
		else
			maxRecurseUp(index);
	}
	
	private void minRecurseDown(int root){
		int left = root * 2 + 1;
		int right = root * 2 + 2;
		int min = root;
		
		if (left < this.size && minHeap.get(left).data.compareTo(minHeap.get(root).data) < 0){
			min = left;
			this.comparisonCount++;
		}
		if (right < this.size && minHeap.get(right).data.compareTo(minHeap.get(root).data) < 0){
			min = right;
			this.comparisonCount++;
		}
		
		if (min != root){
			swapMin(root, min);
			minRecurseDown(min);
		}
	}
	
	private void maxRecurseDown(int root){
		int left = root * 2 + 1;
		int right = root * 2 + 2;
		int min = root;
		
		if (left < this.size && maxHeap.get(left).data.compareTo(maxHeap.get(root).data) > 0){
			min = left;
			this.comparisonCount++;
		}
		if (right < this.size && maxHeap.get(right).data.compareTo(maxHeap.get(root).data) > 0){
			min = right;
			this.comparisonCount++;
		}
		
		if (min != root){
			swapMax(root, min);
			maxRecurseDown(min);
		}
	}
	
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
	
	public void printList(){
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
