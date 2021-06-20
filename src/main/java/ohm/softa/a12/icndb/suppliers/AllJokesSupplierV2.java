package ohm.softa.a12.icndb.suppliers;

import ohm.softa.a12.icndb.ICNDBApi;
import ohm.softa.a12.icndb.ICNDBService;
import ohm.softa.a12.model.JokeDto;
import ohm.softa.a12.model.ResponseWrapper;

import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class AllJokesSupplierV2 implements Supplier<ResponseWrapper<JokeDto>>{

	private final ICNDBApi icndbApi;

	private int jokeCount;

	private int retrievedJokes;

	private int latestId;

	public AllJokesSupplierV2() {
		latestId = 0;
		retrievedJokes = 0;
		this.icndbApi = ICNDBService.getInstance();
		try {
			jokeCount = this.icndbApi.getJokeCount().get().getValue();
		} catch (InterruptedException | ExecutionException e) {
			/* fallback - not the best kind of solution here */
			jokeCount = 0;
		}
	}

	@Override
	public ResponseWrapper<JokeDto> get() {
		ResponseWrapper<JokeDto> result = null;
		try {
			/* fetch joke with a blocking future */
			result = icndbApi.getJoke(increment()).get();
			/* increment counter only if a joke was retrieved successfully */
			retrievedJokes++;
		}catch (Exception e){
			/* ignore exception */
		}
		finally {
			/* return result or NULL in error case */
			return result;
		}
	}

	private int increment() {
		/* if all jokes were retrieved - reset counters */
		if(retrievedJokes >= jokeCount){
			retrievedJokes = 0;
			return latestId = 1;
		}
		return ++latestId;
	}
}
