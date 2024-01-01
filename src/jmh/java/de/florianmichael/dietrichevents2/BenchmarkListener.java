/*
 * This file is part of DietrichEvents2 - https://github.com/FlorianMichael/DietrichEvents2
 * Copyright (C) 2023-2024 FlorianMichael/EnZaXD <florian.michael07@gmail.com> and contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.florianmichael.dietrichevents2;

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
