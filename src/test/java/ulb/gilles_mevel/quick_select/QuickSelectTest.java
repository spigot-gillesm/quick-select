package ulb.gilles_mevel.quick_select;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.*;

public class QuickSelectTest {

    private static final int SIZE = 1_000_000;

    //Some arbitrary small value
    private static final int LOWER_BOUND = -100_000;

    //Some arbitrary big value
    private static final int UPPER_BOUND = 100_000;

    @Test
    void given_random_array_then_find_k_element_returns_the_Kth_smallest_value() {
        final var random = new SecureRandom();
        //First, build a sorted array to use as a reference:
        final Integer[] sortedArray = new Integer[SIZE];
        //The unsorted array
        final Integer[] unsortedArray = new Integer[SIZE];

        //Fill the arrays
        for(int i = 0; i < SIZE; i++) {
            //generate a random value between the lower and upper bound. This value is guaranteed to always be bigger
            //than the previously generated value as the lower bound increases every time a new value is generated
            final int randomValue = random.nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND;

            //As the randomly generated values are generated in increasing order, sortedArray is sorted in increasing order
            sortedArray[i] = randomValue;
            //Randomly position the new value
            unsortedArray[i] = randomValue;
        }
        //Sort the array
        Arrays.sort(sortedArray);

        final var algorithm = new QuickSelect<>(unsortedArray);
        //Choose a random k
        final int randomK = random.nextInt(SIZE);
        //Use said k to find the k-th smallest element
        final int kValueFromAlgorithm = algorithm.findKElement(randomK);
        //use said k in the sorted array to get the k-th smallest element
        final int kValueFromSortedArray = sortedArray[randomK];

        //Check the quality of the values
        Assertions.assertEquals(kValueFromAlgorithm, kValueFromSortedArray);
    }

    @Test
    void given_random_array_then_find_k_element_returns_the_Kth_greatest_value() {
        final var random = new SecureRandom();
        //First, build a sorted array to use as a reference:
        final Integer[] sortedArray = new Integer[SIZE];
        //The unsorted array
        final Integer[] unsortedArray = new Integer[SIZE];

        //Fill the arrays
        for(int i = 0; i < SIZE; i++) {
            //generate a random value between the lower and upper bound. This value is guaranteed to always be bigger
            //than the previously generated value as the lower bound increases every time a new value is generated
            final int randomValue = random.nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND;

            //As the randomly generated values are generated in increasing order, sortedArray is sorted in increasing order
            sortedArray[i] = randomValue;
            //Randomly position the new value
            unsortedArray[i] = randomValue;
        }
        //Sort the array descending
        Arrays.sort(sortedArray, Collections.reverseOrder());

        final var algorithm = new QuickSelect<>(unsortedArray);
        //Choose a random k
        final int randomK = random.nextInt(SIZE);
        //Use said k to find the k-th greatest element
        final int kValueFromAlgorithm = algorithm.findKElement(randomK, true);
        //use said k in the sorted array to get the k-th smallest element
        final int kValueFromSortedArray = sortedArray[randomK];

        //Check the quality of the values
        Assertions.assertEquals(kValueFromAlgorithm, kValueFromSortedArray);
    }

}
