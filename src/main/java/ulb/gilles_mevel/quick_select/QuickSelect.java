package ulb.gilles_mevel.quick_select;

import java.security.SecureRandom;
import java.util.Random;

public class QuickSelect<T> {

	private final Comparable<T>[] elements;

	private final Random random = new SecureRandom();

	public QuickSelect(final Comparable<T>[] elements) {
		this.elements = elements;
	}

	/**
	 * Find the k-th smallest/greatest element in this instance's array, depending on findGreatest value.
	 *
	 * @param findGreatest whether this instance should look for the greatest k-th element instead of smallest
	 * @return the value of the k-th smallest/greatest element
	 */
	public T findKElement(final int k, final boolean findGreatest) {
		int effectiveK = k;

		//Asking for the greatest element is equivalent to asking for "the least great" element
		if(findGreatest) {
			effectiveK = elements.length - k - 1;
		}

		return findKElement(effectiveK);
	}

	/**
	 * Find the k-th smallest element in this instance's array.
	 *
	 * @return the value of the k-th smallest element
	 */
	public T findKElement(final int k) {
		if (elements.length < 1) {
			throw new IllegalArgumentException("There must be at least one element in the array");
		}
		if (elements.length == 1) {
			return get(0);
		}

		return findKElement(k, 0, elements.length-1);
	}

	/**
	 * Find the k-th smallest element in this instance's array, between the left and right index.
	 *
	 * @return the value of the k-th smallest element
	 */
	private T findKElement(final int k, final int left, final int right) {
		int pivotIndex = random.nextInt(right - left + 1) + left;
		pivotIndex = partition(left, right, pivotIndex);

		if(pivotIndex == k) {
			return get(pivotIndex);
		} else if(pivotIndex < k) {
			return findKElement(k, pivotIndex + 1, right);
		} else {
			return findKElement(k, left, pivotIndex - 1);
		}
	}

	/**
	 * Partition the values between left and right indexes where:
	 * <ul>
	 *   <li>all elements between left and the value at the pivot index are smaller or equal than said value</li>
	 *   <li>all elements between the value at the pivot index and right are greater or equal than said value</li>
	 * </ul>
	 * The method guarantees that the position of the value at the given pivot index will be at its right position
	 * in the array, i.e. if the array was completely sorted, that value would be at the same index.
	 *
	 * @param left the left index, i.e. lower bound
	 * @param right the right index, i.e. upper bound
	 * @param pivotIndex the index at which the value will be used as a pivot
	 * @return the final position/index of the pivot value
	 */
	private int partition(final int left, final int right, final int pivotIndex) {
		//Get value at pivotIndex
		final T pivotValue = get(pivotIndex);
		//Push the pivotValue to the end of the partition
		swap(pivotIndex, right);

		int currentIndex = left;

		for(int i = left; i <= right; i++) {
			if(elements[i].compareTo(pivotValue) < 0) {
				swap(i, currentIndex);
				currentIndex++;
			}
		}
		//Put back the index value at its 'final' place, i.e. leftValues <= pivotValue <= rightValues
		swap(currentIndex, right);

		//Return the index at which the value of pivotIndex (the parameter) is now positioned
		return currentIndex;
	}

	/**
	 * Swap the elements of the array
	 */
	private void swap(final int i, final int j) {
		final var tempI = elements[i];
		elements[i] = elements[j];
		elements[j] = tempI;
	}

	//Method used to avoid unchecked in the rest of the code
	@SuppressWarnings("unchecked")
	private T get(final int i) {
		return (T) elements[i];
	}

}
