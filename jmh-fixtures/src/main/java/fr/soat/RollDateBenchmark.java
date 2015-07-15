package fr.soat;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

/*
 * illustration of benchmark on Date rolling  
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 10, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class RollDateBenchmark {

	@Benchmark
	public Calendar benchmark_roll_new_Date() {
		// create a calendar set to "now"
		Calendar c = Calendar.getInstance();
		
		// roll "now" of 1 day
		c.roll(Calendar.DAY_OF_YEAR, 1);
		return c;
	}
	
	@State(Scope.Benchmark)
	public static class DateContainer {
        // an object DateContainer will be instanciatedd
        // out of benchmark time by JMH Runner
		
		// get system date
		Calendar now = Calendar.getInstance();
		
		// get a copy of "now" calendar
		Calendar getCalendarSetToNow() {
			return (Calendar) now.clone();
		}
	}
	
	@Benchmark
	public Calendar benchmark_roll_date_in_argument(DateContainer d) {
		// get calendar for "now", created before benchmark
		Calendar c =  d.getCalendarSetToNow();
		
		// roll date
		c.roll(Calendar.DAY_OF_YEAR, 1);
		return c;
	}

	@Benchmark
	public Calendar benchmark_sysdate() {
		return Calendar.getInstance();
	}


}
