package fr.soat;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

/*
 * illustration of benchmark on Date rolling using fixtures
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 10, time=1000, timeUnit=TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time=1000, timeUnit=TimeUnit.MILLISECONDS)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SortArrayBenchmark {

	@State(Scope.Benchmark)
	public static class ArrayContainer {
  		// initial unsorted array
		int [] suffledArray = new int[10000000];
		// system date copy
		int [] arrayToSort = new int[10000000];
		
		@Setup(Level.Trial)
		public void initArray() {
			// create a shuffled array of int
			for (int i : suffledArray) {
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
		Arrays.sort(d.getArrayToSort());
		// array is now sorted !
		return d.getArrayToSort();
	}

	@Benchmark
	public int[] baseline(ArrayContainer d) {
		// empty benchmark check the Level.Invocation setup fixture 
		// does not impact significativly the measures on benchmark_array_sort()
		return null;
	}


}
