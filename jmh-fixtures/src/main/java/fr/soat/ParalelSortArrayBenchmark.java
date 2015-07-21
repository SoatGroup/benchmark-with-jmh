package fr.soat;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

/*
 * illustration of parameterized fixture usage on array sort benchmark 
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 10, time=500, timeUnit=TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time=500, timeUnit=TimeUnit.MILLISECONDS)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ParalelSortArrayBenchmark {

	@State(Scope.Benchmark)
	public static class ArrayContainer {

		@Param({ "10", "20", "40", "80", "100", "200", "400", "800", "1000", "10000", "100000", "1000000", "10000000" })
		int arraySize;

		// initial unsorted array
		int [] suffledArray;
		// system date copy
		int [] arrayToSort;
		
		@Setup(Level.Trial)
		public void initArray() {
			// create a shuffled array of int
			suffledArray = new int[arraySize];
			for (int i = 0; i < arraySize; i++) {
				suffledArray[i] = new Random().nextInt(1000);
			}
		}
		
		@Setup(Level.Invocation)
		public void makeArrayCopy() {
			// copy shuffled array to reinit the array to sort
			arrayToSort = suffledArray.clone();
		}
		
		int[] getArrayToSort() {
			return arrayToSort;
		}
	}
	
	@Benchmark
	public int[] benchmark_array_sort(ArrayContainer d) {
		// sort copy of shuffled array
	    return Arrays.stream(d.getArrayToSort()).parallel().sorted().toArray();
	}

	@Benchmark
	public int[] baseline(ArrayContainer d) {
        // sort copy of shuffled array
	    return Arrays.stream(d.getArrayToSort()).sorted().toArray();
	}


}
