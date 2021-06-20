package ohm.softa.a12.icndb;

import ohm.softa.a12.icndb.suppliers.RandomJokeSupplier;
import ohm.softa.a12.icndb.suppliers.AllJokesSupplierV2;
import ohm.softa.a12.model.JokeDto;
import ohm.softa.a12.model.ResponseWrapper;

import java.util.stream.Stream;

/**
 * Generator instance to provide streams of jokes
 * @author Peter Kurfer
 */

public final class JokeGenerator {

    /**
     * Generator for random jokes
     * @return stream of random jokes wrapped in ResponseWrapper objects
     */
    public Stream<ResponseWrapper<JokeDto>> randomJokesStream() {
        /* TODO create new Stream of random jokes */
		return Stream.generate(new RandomJokeSupplier());
    }

    /**
     * Generator for jokes ordered by their id
     * @return stream of jokes wrapped in ResponseWrapper objects
     */
    public Stream<ResponseWrapper<JokeDto>> jokesStream() {
        /* TODO create a new Stream of all jokes */
		return Stream.generate(new AllJokesSupplierV2());
    }
}
