package de.florianmichael.dietrichevents2;

import de.florianmichael.dietrichevents2.core.AbstractEvent;
import org.openjdk.jmh.infra.Blackhole;

public interface BenchmarkListener {

    void onBenchmark(final Blackhole blackhole);

    class BenchmarkEvent extends AbstractEvent<BenchmarkListener> {

        public static final int ID = 0;

        private final Blackhole blackhole;

        public BenchmarkEvent(final Blackhole blackhole) {
            this.blackhole = blackhole;
        }

        @Override
        public void call(BenchmarkListener listener) {
            listener.onBenchmark(this.blackhole);
        }
    }
}
