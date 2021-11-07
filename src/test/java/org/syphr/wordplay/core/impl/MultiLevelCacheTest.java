package org.syphr.wordplay.core.impl;

import java.util.List;

import org.junit.Test;
import org.syphr.wordplay.core.impl.MultiLevelCache.MultiLevelCacheLoader;

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
