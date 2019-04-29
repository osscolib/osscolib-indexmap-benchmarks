/*
 * =============================================================================
 *
 *   Copyright (c) 2019, The VEXPREL team (http://www.vexprel.org)
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
package org.osscolib.indexmap.benchmarks.benchmark04;


import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.osscolib.indexmap.benchmarks.BenchmarkUtils;

@Fork(2)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class BaseBenchmark04 {

    private static final int DEFAULT_ACCESS_ATTEMPTS = 10000;

    private static final int NODE_SIZE = 16;
    private static final int NODE_LEVELS = 6; // CANNOT BE CHANGED

    private final Node rootNode;
    private final long[] attempts;
    private final IndexedAttempt[] indexedAttempts;




    protected BaseBenchmark04() {
        this(DEFAULT_ACCESS_ATTEMPTS);
    }


    protected BaseBenchmark04(final int accessAttempts) {
        super();

        final long lowLimit = 0;
        final long highLimit = BigInteger.valueOf(NODE_SIZE).pow(NODE_LEVELS).longValue() - 1;
        final Node[] rootChildren = createChildren(lowLimit, highLimit, NODE_SIZE);
        this.rootNode = new Node(lowLimit, highLimit, NODE_SIZE, rootChildren, null);

        this.attempts = new long[accessAttempts];
        for (int i = 0; i < accessAttempts; i++) {
            this.attempts[i] = RandomUtils.nextLong(0, highLimit + 1);
        }


        this.indexedAttempts = new IndexedAttempt[accessAttempts];
        for (int i = 0; i < accessAttempts; i++) {

            final long n = attempts[i];

            final int posi = ((int)n >> (5 * 4)) & (NODE_SIZE - 1);
            final int posj = ((int)n >> (4 * 4)) & (NODE_SIZE - 1);
            final int posk = ((int)n >> (3 * 4)) & (NODE_SIZE - 1);
            final int posl = ((int)n >> (2 * 4)) & (NODE_SIZE - 1);
            final int posm = ((int)n >> (1 * 4)) & (NODE_SIZE - 1);
            final int posn = ((int)n >> (0 * 4)) & (NODE_SIZE - 1);

            this.indexedAttempts[i] = new IndexedAttempt(posi, posj, posk, posl, posm, posn);

        }

    }


    private final Node[] createChildren(final long lowLimit, final long highLimit, final int nodeSize) {

        final Node[] children = new Node[nodeSize];
        final long nodeRange = (highLimit - lowLimit) + 1;
        final long rangePerChild = nodeRange / nodeSize;

        if (nodeSize == nodeRange) { // rangePerChild == 0

            for (int i = 0; i < nodeSize; i++) {
                children[i] =
                        new Node(lowLimit + i, lowLimit + i, nodeSize, null, BenchmarkUtils.generateString(20));
            }
            return children;

        }

        for (int i = 0; i < nodeSize; i++) {
            final long childLow = lowLimit + (i * rangePerChild);
            final long childHigh = childLow + rangePerChild - 1;
            final Node[] childChildren = createChildren(childLow, childHigh, nodeSize);
            children[i] = new Node(childLow, childHigh, nodeSize, childChildren, null);
        }

        return children;

    }


    static class Node {

        final long lowLimit;
        final long highLimit;
        final long rangePerChild;
        final Node[] children;
        final String value;

        public Node(final long lowLimit, final long highLimit, final int nodeSize, final Node[] children, final String value) {
            this.lowLimit = lowLimit;
            this.highLimit = highLimit;
            this.rangePerChild = ((highLimit - lowLimit) + 1) / nodeSize;
            this.children = children;
            this.value = value;
        }


    }


    static class IndexedAttempt {
        final int i;
        final int j;
        final int k;
        final int l;
        final int m;
        final int n;
        IndexedAttempt(int i, int j, int k, int l, int m, int n) {
            super();
            this.i = i;
            this.j = j;
            this.k = k;
            this.l = l;
            this.m = m;
            this.n = n;
        }
    }


    public int getNodeSize() {
        return NODE_SIZE;
    }

    public int getNodeLevels() {
        return NODE_LEVELS;
    }

    public Node getRootNode() {
        return this.rootNode;
    }

    public long[] getAttempts() {
        return this.attempts;
    }

    public IndexedAttempt[] getIndexedAttempts() {
        return this.indexedAttempts;
    }



}
