package ohm.softa.a12.icndb;

public class Tuple2 <T1, T2> {

	private final T1 item1;
	private final T2 item2;

	private Tuple2(T1 item1, T2 item2) {
		this.item1 = item1;
		this.item2 = item2;
	}

	public T1 getItem1() {
		return item1;
	}

	public T2 getItem2() {
		return item2;
	}

	/**
	 * Factory method to create a tuple
	 * @param item1 value of item1
	 * @param item2 value of item2
	 * @param <T1> type of item1
	 * @param <T2> type of item2
	 * @return new Tuple2
	 */
	public static final <T1, T2> Tuple2<T1, T2> of(T1 item1, T2 item2) {
		return new Tuple2<>(item1, item2);
	}
}
