package de.florianmichael.dietrichevents2;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 4, time = 5)
@Measurement(iterations = 4, time = 5)
public class BenchmarkCaller implements BenchmarkListener {
    private final static int ITERATIONS = 100_000;

    @Setup
    public void setup() {
        DietrichEvents2.global().subscribe(BenchmarkEvent.ID, this);
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1, warmups = 1)
    public void callBenchmarkListener(Blackhole blackhole) {
        for (int i = 0; i < ITERATIONS; i++) {
            DietrichEvents2.global().post(BenchmarkEvent.ID, new BenchmarkListener.BenchmarkEvent(blackhole));
        }
    }

    @Override
    public void onBenchmark(Blackhole blackhole) {
        blackhole.consume(Integer.bitCount(Integer.parseInt("123")));
    }
}
