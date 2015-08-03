/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package fr.soat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

//@BenchmarkMode(Mode.AverageTime)
//@Warmup(iterations = 10, time=500, timeUnit=TimeUnit.MILLISECONDS)
//@Measurement(iterations = 10, time=500, timeUnit=TimeUnit.MILLISECONDS)
//@BenchmarkMode(Mode.SingleShotTime)
@BenchmarkMode(Mode.SampleTime)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class LambdaBenchmark {

	public static class Bean {
		private int value;
		public Bean(int value) {
			this.value = value;
		}		
		public int getValue() {
			return value;
		}
	}
	
	@State(Scope.Benchmark)
	public static class ArrayContainer {

		@Param({ "10", "15", "20", "25", "30", "40", "60", "80", "100", "200", "400", "800", "1000", "10000", "100000", "1000000", "10000000" })
		int arraySize;

		// initial unsorted array
		Bean [] suffledArray;
		// system date copy
		Bean [] arrayToSort;
		
		@Setup(Level.Trial)
		public void initArray() {
			// create a shuffled array of int
			suffledArray = new Bean[arraySize];
			for (int i = 0; i < arraySize; i++) {
				suffledArray[i] = new Bean(new Random().nextInt(1000));
			}
		}
		
		@Setup(Level.Invocation)
		public void makeArrayCopy() {
			// copy shuffled array to reinit the array to sort
			arrayToSort = suffledArray.clone();
		}
		
		Bean[] getArrayToSort() {
			return arrayToSort;
		}
	}
	
//	@Benchmark
//	public Object[] baseline(ArrayContainer d) {
//		return null;
//	}
	
	
	@Benchmark
	public Object[] benchmark_array_with_lambda(ArrayContainer d) {
		// parallel sort array
	    return Arrays.stream(d.getArrayToSort())
	    		.parallel()
	    		.sorted((x, y) -> (x.getValue() < y.getValue()) ? -1 : ((x.getValue() == y.getValue()) ? 0 : 1))
	    		.toArray();
	}

	
	@Benchmark
	public Object[] benchmark_array_with_anonymous_class(ArrayContainer d) {
		// parallel sort array
		return Arrays.stream(d.getArrayToSort())
				.parallel()
				.sorted(new Comparator<Bean>() {
							@Override
							public int compare(Bean x, Bean y) {
								return (x.getValue() < y.getValue()) ? -1 : ((x.getValue() == y.getValue()) ? 0 : 1);
							}
						})
				.toArray();
	}

}
