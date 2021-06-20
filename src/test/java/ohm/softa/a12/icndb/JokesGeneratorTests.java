package ohm.softa.a12.icndb;

import ohm.softa.a12.icndb.suppliers.AllJokesSupplier;
import ohm.softa.a12.icndb.suppliers.AllJokesSupplierV2;
import ohm.softa.a12.model.JokeDto;
import ohm.softa.a12.model.ResponseWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.Assertions;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Consumer;
import static de.thro.inf.prg3.a12.util.FunctionalUtils.zip;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Peter Kurfer
 * Created on 12/28/17.
 */
class JokesGeneratorTests {

	private static final Logger logger = LogManager.getLogger();
    private JokeGenerator jokeGenerator = new JokeGenerator();

	@Test
	@SuppressWarnings("unchecked")
	void testRandomStream() {
		/* create consumer to ensure that elements are not null and print them for amusement */
		Consumer<String> consumer = MultiConsumer.of(Assertions::assertNotNull, logger::info);
		/* ensure termination to avoid infinite stream error */
		assertTimeout(Duration.ofSeconds(5L), () -> jokeGenerator.randomJokesStream()
			/* filter null objects */
			.filter(Objects::nonNull)
			/* skip some elements */
			.skip(3)
			/* limit to 5 elements due to infinite stream */
			.limit(5)
			/* unwrap ResponseWrapper */
			.map(ResponseWrapper::getValue)
			/* unwrap actual joke String */
			.map(JokeDto::getJoke)
			/* apply consumer */
			.forEach(consumer)
		);
	}


    @Test
	@SuppressWarnings("unchecked")
	void testJokesStream() {
		/* create consumer to ensure that elements are not null and print them for amusement */
		Consumer<String> consumer = MultiConsumer.of(Assertions::assertNotNull, logger::info);

		/* ensure termination to avoid infinite stream error */
		assertTimeout(Duration.ofMinutes(1L), () -> jokeGenerator.jokesStream()
			/* filter null objects */
			.filter(Objects::nonNull)
			/* unwrap ResponseWrapper */
			.map(ResponseWrapper::getValue)
			/* skip 200 elements to force overflow */
			.skip(200L)
			/* limit to 400 elements due to infinite stream */
			.limit(400L)
			/* unwrap actual joke String */
			.map(JokeDto::getJoke)
			/* apply consumer */
			.forEach(consumer));
	}

	@Test
	@SuppressWarnings("unchecked")
	void testGetNerdyJokes() {
		/* create consumer to ensure that elements are not null and print them for amusement */
		Consumer<String> consumer = MultiConsumer.of(Assertions::assertNotNull, logger::info);

		/* ensure termination to avoid infinite stream error */
		assertTimeout(Duration.ofMinutes(1L), () -> jokeGenerator.jokesStream()
			/* filter null objects */
			.filter(Objects::nonNull)
			/* unwrap ResponseWrapper */
			.map(ResponseWrapper::getValue)
			/* filter for jokes containing the category 'nerdy' */
			.filter(j -> j.getCategories().stream().anyMatch(c -> c.startsWith("nerdy")))
			/* limit to 10 elements */
			.limit(10)
			/* unwrap actual joke String */
			.map(JokeDto::getJoke)
			/* apply consumer */
			.forEach(consumer));
	}

	@Test
	@SuppressWarnings("unchecked")
	void testCompareAllJokesSuppliers() {
		final int itemCount = 600;

		/* create consumer to ensure elements are not null and are equal */
		Consumer<Tuple2<String, String>> consumer = MultiConsumer.of(
			t -> {
				/* null check */
				assertNotNull(t.getItem1());
				assertNotNull(t.getItem2());
			},
			/* equals check */
			t -> assertEquals(t.getItem1(), t.getItem2()),
			/* print to STDOUT for amusement */
			t -> logger.info(String.format("Stream1: %s,\nStream2: %s", t.getItem1(), t.getItem2()))
		);

		/* create stream with basic supplier */
		Stream<String> stream1 = Stream.generate(new AllJokesSupplier())
			.filter(Objects::nonNull)
			.limit(itemCount)
			.map(ResponseWrapper::getValue)
			.map(JokeDto::getJoke);

		/* create stream with advanced supplier */
		Stream<String> stream2 = Stream.generate(new AllJokesSupplierV2())
			.filter(Objects::nonNull)
			.limit(itemCount)
			.map(ResponseWrapper::getValue)
			.map(JokeDto::getJoke);

		/* zip streams to tuples and apply consumer */
		zip(stream1, stream2, Tuple2::of)
			.forEach(consumer);
	}

}
