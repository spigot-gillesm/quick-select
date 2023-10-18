package ulb.gilles_mevel.quick_select;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.*;

public class QuickSelectTest {

    private static final int SIZE = 10_000;

    //Some arbitrary small value
    private static final int LOWER_BOUND = -100_000;

    //Some arbitrary big value
    private static final int UPPER_BOUND = 100_000;

    private static final int ROUNDS = 1_000;

    @Test
    void given_random_array_then_find_k_element_returns_the_Kth_smallest_value() {
        final var random = new SecureRandom();
        //First, build a sorted array to use as a reference:
        final Integer[] sortedArray = new Integer[SIZE];
        //The unsorted array
        final Integer[] unsortedArray = new Integer[SIZE];

        //Fill the arrays
        for(int i = 0; i < SIZE; i++) {
            final int randomValue = random.nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND;
            sortedArray[i] = randomValue;
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
            final int randomValue = random.nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND;
            sortedArray[i] = randomValue;
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

    @Test
    void given_a_random_k_then_the_expected_number_of_comparisons_can_be_expressed_as_a_function_of_a() {
        final Random random = new SecureRandom();
        final int k = random.nextInt(SIZE/2);
        System.out.println("k: " + k);

        final List<Integer> registeredComparisons = new ArrayList<>();

        for(int i = 0; i < ROUNDS; i++) {
            final Integer[] unsortedArray = new Integer[SIZE];

            //Fill the array
            for(int j = 0; j < SIZE; j++) {
                final int randomValue = random.nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND;
                unsortedArray[j] = randomValue;
            }
            //We assume the algorithm to work
            final var algorithm = new QuickSelect<>(unsortedArray);
            algorithm.findKElement(k, true);
            registeredComparisons.add(algorithm.getComparisons());
        }
        final double expected = computeFormula(k, SIZE);
        final double average = average(registeredComparisons);
        final double relDiff = Math.abs(1 - average / expected);
        System.out.println("%diff: " + relDiff);

        //Reject if the difference exceeds 5%, i.e. check the average converges to the expected value
        Assertions.assertTrue(relDiff <= 0.05);
    }

    private double computeFormula(final double k, final double n) {
        return 2*n + 2*k*Math.log((n - k) / k) + 2*n*Math.log(n/(n - k));
    }

    private double average(final Collection<Integer> collection) {
        double sum = 0;

        for(final int e : collection) {
            sum += e;
        }

        return sum/collection.size();
    }

}
