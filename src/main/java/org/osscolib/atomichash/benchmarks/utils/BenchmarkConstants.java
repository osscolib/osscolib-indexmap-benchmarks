package org.osscolib.atomichash.benchmarks.utils;

/*
 * =============================================================================
 *
 *   Copyright (c) 2019, The OSSCOLIB team (http://www.osscolib.org)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
public final class BenchmarkConstants {


    public static final int SINGLE_THREAD_TEST_NUM_THREADS = 1;
    public static final int SINGLE_THREAD_TEST_NUM_EXECUTIONS_IN_BENCHMARK = 1000;

    public static final int CONCURRENT2_TEST_NUM_THREADS = 2;
    public static final int CONCURRENT2_TEST_NUM_EXECUTIONS_IN_BENCHMARK = 500; // times num_threads for each map

    public static final int CONCURRENT4_TEST_NUM_THREADS = 4;
    public static final int CONCURRENT4_TEST_NUM_EXECUTIONS_IN_BENCHMARK = 250; // times num_threads for each map




    private BenchmarkConstants() {
        super();
    }

}