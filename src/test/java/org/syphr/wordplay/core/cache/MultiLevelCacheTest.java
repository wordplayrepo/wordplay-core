/*
 * Copyright © 2012-2023 Gregory P. Moyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.syphr.wordplay.core.cache;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.syphr.wordplay.core.cache.MultiLevelCache.MultiLevelCacheLoader;

import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.UncheckedExecutionException;

import lombok.Data;

public class MultiLevelCacheTest implements WithAssertions
{
    @ParameterizedTest
    @CsvSource({ "1,8", "2,8", "3,5" })
    public void getUnchecked_CacheHitsAndMisses(int cacheSize, int missCount)
    {
        // given
        var cacheMiss = new Counter();

        var cacheBuilder = CacheBuilder.newBuilder().maximumSize(cacheSize);
        var loader = new MultiLevelCacheLoader<Integer, String>()
        {
            @Override
            public String load(List<Integer> keys) throws Exception
            {
                cacheMiss.increment();
                return keys.get(0) + "|" + keys.get(1) + "|" + keys.get(2);
            }
        };
        var cache = new MultiLevelCache<Integer, String>(cacheBuilder, loader);

        // when
        var results = new ArrayList<String>();
        for (int i = 1; i <= 2; i++) {
            results.add(cache.getUnchecked(i, i + 1, i + 2));
            results.add(cache.getUnchecked(i + 1, i + 2, i + 3));
            results.add(cache.getUnchecked(i + 2, i + 3, i + 4));
            results.add(cache.getUnchecked(i + 3, i + 4, i + 5));
        }

        // then
        assertAll(() -> assertThat(cacheMiss.getCount()).isEqualTo(missCount),
                  () -> assertThat(results).hasSize(8)
                                           .containsExactly("1|2|3",
                                                            "2|3|4",
                                                            "3|4|5",
                                                            "4|5|6",
                                                            "2|3|4",
                                                            "3|4|5",
                                                            "4|5|6",
                                                            "5|6|7"));
    }

    @Test
    public void getUnchecked_Error()
    {
        // given
        var cacheBuilder = CacheBuilder.newBuilder().maximumSize(2);
        var loader = new MultiLevelCacheLoader<Integer, String>()
        {
            @Override
            public String load(List<Integer> keys) throws Exception
            {
                throw new Exception();
            }
        };
        var cache = new MultiLevelCache<Integer, String>(cacheBuilder, loader);

        // when
        Executable exec = () -> cache.getUnchecked(1, 2, 3);

        // then
        assertThrows(UncheckedExecutionException.class, exec);
    }

    @Data
    private class Counter
    {
        int count;

        public int increment()
        {
            return count++;
        }
    }
}
