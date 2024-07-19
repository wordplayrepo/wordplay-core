/*
 * Copyright © 2012-2024 Gregory P. Moyer
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import javax.annotation.concurrent.ThreadSafe;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.UncheckedExecutionException;

/**
 * The multi-level cache is simply a collection of nested caches with each step
 * utilizing a different key to retrieve a lower cache until the inner most
 * cache which returns a value.
 *
 * @author Gregory P. Moyer
 *
 * @param <K> the type of the keys
 * @param <V> the type of the value
 */
@ThreadSafe
public class MultiLevelCache<K, V>
{
    /**
     * The cache that contains the nested caches and ultimately the values.
     */
    private final Cache<Object, Object> cache;

    /**
     * The builder that creates each cache level.
     */
    private final Callable<Cache<Object, Object>> builder;

    /**
     * The loader that generates a new value on a cache miss.
     */
    private final Callable<V> loader;

    /**
     * The list of keys used in the current get operation. This is required to allow
     * the loader to process the keys without creating a new loader on every cache
     * miss.
     */
    private final List<K> keys = new ArrayList<>();

    public MultiLevelCache(final CacheBuilder<Object, Object> builder, final MultiLevelCacheLoader<K, V> loader)
    {
        this.cache = builder.build();

        this.builder = new Callable<>()
        {
            @Override
            public Cache<Object, Object> call()
            {
                return builder.build();
            }
        };

        this.loader = new Callable<>()
        {
            @Override
            public V call() throws Exception
            {
                return loader.load(keys);
            }
        };
    }

    /**
     * Retrieve the value corresponding to the given keys.
     *
     * @param keys the keys to reference the desired value
     *
     * @return the value associated with the given keys
     *
     * @throws ExecutionException if an error occurs while loading a new value into
     *                            the cache on a miss
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public final synchronized V get(K... keys) throws ExecutionException
    {
        /*
         * Clear the keys list and initialize the first level cache.
         */
        this.keys.clear();
        Cache<Object, Object> nestedCache = cache;

        /*
         * Iterate over each key except the last to retrieve each nested cache level,
         * building new caches as necessary.
         */
        int i = 0;
        for (; i < keys.length - 1; i++) {
            K key = keys[i];
            this.keys.add(key);

            nestedCache = (Cache<Object, Object>) nestedCache.get(key, builder);
        }

        K key = keys[i];
        this.keys.add(key);

        /*
         * Retrieve or load the value from the innermost cache.
         */
        return (V) nestedCache.get(key, loader);
    }

    /**
     * Retrieve the value corresponding to the given keys.
     *
     * @param keys the keys to reference the desired value
     *
     * @return the value associated with the given keys
     *
     * @throws UncheckedExecutionException if an error occurs while loading a new
     *                                     value into the cache on a miss
     */
    @SafeVarargs
    public final V getUnchecked(K... keys) throws UncheckedExecutionException
    {
        try {
            return get(keys);
        } catch (ExecutionException e) {
            throw new UncheckedExecutionException(e.getCause());
        }
    }

    /**
     * The multi-level cache loader generates a value based on a set of keys for
     * insertion into a multi-level cache when a miss occurs.
     *
     * @author Gregory P. Moyer
     *
     * @param <K> the type of the keys
     * @param <V> the type of the value
     */
    public static interface MultiLevelCacheLoader<K, V>
    {
        /**
         * Create a new value based on the given list of keys.
         *
         * @param keys the keys to reference the new value
         *
         * @return the generated value
         *
         * @throws Exception if an error occurs while creating the value
         */
        public abstract V load(List<K> keys) throws Exception;
    }
}
