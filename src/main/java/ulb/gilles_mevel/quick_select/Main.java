package ulb.gilles_mevel.quick_select;

public class Main {

	public static void main(final String[] args) {
		final Integer[] numbers = {0, 8, 4, 15, 14, -4, 0, 78, -25, 14, 75, 2};
		final var quickSelect = new QuickSelect<>(numbers);
		System.out.println("k-th element: " + quickSelect.findKElement(0));
	}

}
