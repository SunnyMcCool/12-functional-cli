package ohm.softa.a12.icndb;

import java.util.function.Consumer;

public class MultiConsumer<T> implements Consumer<T> {
	/* actual consumers aggregated into this MultiConsumer */
	private final Consumer<? super T>[] consumers;

	@SuppressWarnings("unchecked")
	private MultiConsumer(Consumer<? super T>[] consumers) {
		if (consumers == null) {
			this.consumers = (Consumer<? super T>[]) new Object[0];
		} else {
			this.consumers = consumers;
		}
	}

	/**
	 * Factory method to create MultiConsumer
	 *
	 * @param consumers varargs of actual consumers to apply
	 * @param <T>       type of consumed value
	 * @return new MultiConsumer
	 */
	public static <T> MultiConsumer<T> of(Consumer<? super T>... consumers) {
		return new MultiConsumer<>(consumers);
	}

	@Override
	public void accept(T t) {
		for (Consumer<? super T> c : consumers) {
			c.accept(t);
		}
}
