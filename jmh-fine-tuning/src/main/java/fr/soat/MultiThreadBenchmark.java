package fr.soat;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

/*
 * illustration of benchmarking with concurrency issues  
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations=10, time=100, timeUnit=TimeUnit.MILLISECONDS)
@Measurement(iterations=10, time=100, timeUnit=TimeUnit.MILLISECONDS)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class MultiThreadBenchmark {
	
	// lock monitor shared between threads
	static Object lock = new Object();
	
	private static int doSomething() {
		int count = 0;
		for (int i = 0; i < 100000; i++) {
			synchronized(lock) {
				count++;
			}
		}				
		return count;		
	}
	
	@Benchmark
	@Threads(1	)
	public int benchmark_on_1_thread() {
		return doSomething();
	}

	@Benchmark
	@Threads(2)
	public int benchmark_on_2_threads() {
		return doSomething();
	}

	@Benchmark
	@Threads(4)
	public int benchmark_on_4_threads() {
		return doSomething();
	}

	@Benchmark
	@Threads(8)
	public int benchmark_on_8_threads() {
		return doSomething();
	}
	

}
