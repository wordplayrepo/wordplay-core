/*
 * Copyright © 2012-2022 Gregory P. Moyer
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

import java.util.List;

import org.junit.Test;
import org.syphr.wordplay.core.cache.MultiLevelCache.MultiLevelCacheLoader;

import com.google.common.cache.CacheBuilder;

public class MultiLevelCacheTest
{
    @Test
    public void testGetUnchecked()
    {
        MultiLevelCache<Integer, String> cache = new MultiLevelCache<Integer, String>(CacheBuilder.newBuilder()
                                                                                                  .maximumSize(10),
                                                                                      new MultiLevelCacheLoader<Integer, String>()
                                                                                      {
                                                                                          @Override
                                                                                          public String load(List<Integer> keys) throws Exception
                                                                                          {
                                                                                              System.out.println("Build value for keys: " +
                                                                                                                 keys);
                                                                                              return keys.get(0) + "|" +
                                                                                                     keys.get(1) +
                                                                                                     "|" +
                                                                                                     keys.get(2);
                                                                                          }
                                                                                      });

        for (int i = 0; i < 1000; i++) {
            System.out.println(cache.getUnchecked(1, 2, 3));
        }
    }
}
