package ulb.gilles_mevel.quick_select;

import java.security.SecureRandom;
import java.util.Random;

public class QuickSelect<T> {

	private final Comparable<T>[] elements;

	private final Random random = new SecureRandom();

	public QuickSelect(final Comparable<T>[] elements) {
		this.elements = elements;
	}

	@SuppressWarnings("unchecked")
	public T findKElement(final int k) {
		if(elements.length < 1) {
			throw new IllegalArgumentException("There must be at least one element in the array");
		}
		if(elements.length == 1) {
			return (T) elements[0];
		}

		return findKElement(k, 0, elements.length);
	}

	@SuppressWarnings("unchecked")
	private T findKElement(final int k, final int left, final int right) {
		int upperBound = right;
		int lowerBound = left;

		while(upperBound != lowerBound) {
			int pivotIndex = random.nextInt(upperBound - lowerBound) + lowerBound;
			pivotIndex = partition(lowerBound, upperBound, pivotIndex);
			System.out.println("pivot: " + pivotIndex);
			System.out.println("pivot value: " + elements[pivotIndex]);
			System.out.println("left: " + lowerBound);
			System.out.println("right: " + upperBound);

			if(pivotIndex == k) {
				return (T) elements[pivotIndex];
			//If k is smaller than the pivot index, the value the algorithm is looking for is on the left of the pivot index
			} else if(k < pivotIndex) {
				upperBound = pivotIndex;
			//If k is bigger than the pivot index, the value the algorithm is looking for is on the right of the pivot index
			} else {
				lowerBound = pivotIndex;
			}
		}

		return (T) elements[left];
	}

	@SuppressWarnings("unchecked")
	private int partition(final int left, final int right, final int pivotIndex) {
		//Get value at pivotIndex
		final var pivotValue = elements[pivotIndex];
		//Push the pivotValue to the end of the partition
		swap(pivotIndex, right-1);

		int currentIndex = left;

		for(int i = left; i < right; i++) {
			if(elements[i].compareTo((T) pivotValue) < 0) {
				swap(i, currentIndex);
				currentIndex++;
			}
		}
		//Put back the index value at its 'final' place, i.e. leftValues <= pivotValue <= rightValues
		swap(currentIndex, right-1);

		//Return the index at which the value of pivotIndex (the parameter) is now positioned
		return currentIndex;
	}

	private void swap(final int i, final int j) {
		final var tempI = elements[i];
		elements[i] = elements[j];
		elements[j] = tempI;
	}

	private enum Mode {

		GREATEST,
		SMALLEST

	}

}
