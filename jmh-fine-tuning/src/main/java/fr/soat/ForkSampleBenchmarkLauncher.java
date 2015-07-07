package fr.soat;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/*
 * a "launcher" to run benchmark in IDE
 */
public final class ForkSampleBenchmarkLauncher {

    private ForkSampleBenchmarkLauncher() {
    }

    public static void main(final String[] _args) throws RunnerException {
        Options opt = new OptionsBuilder()
                            .include("fr.soat.ForkSampleBenchmark")
                            .forks(0)
                            .build();
        new Runner(opt).run(); 
    }
}