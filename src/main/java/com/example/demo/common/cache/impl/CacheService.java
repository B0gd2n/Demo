package com.example.demo.common.cache.impl;

import com.example.demo.common.cache.ICacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tito on 11/24/2017.
 */
public class CacheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheService.class);
    private static ConcurrentHashMap cacheHashMap = new ConcurrentHashMap();

    static {
        try {
            //Thread to clean cache
            Thread threadCleanerUpper = new Thread(
                    new Runnable() {
                        int milliSecondSleepTime = 5000;

                        public void run() {
                            try {
                                while (true) {
                                    Set keySet = cacheHashMap.keySet();
                                    Iterator keys = keySet.iterator();
                                    while (keys.hasNext()) {
                                        Object key = keys.next();
                                        ICacheable value = (ICacheable) cacheHashMap.get(key);
                                        if (value.isExpired()) {
                                            cacheHashMap.remove(key);
                                        }
                                    }
                                    //COULD add logic for LRU or LFU
                                    Thread.sleep(this.milliSecondSleepTime);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                    });
            threadCleanerUpper.setPriority(Thread.MIN_PRIORITY);
            threadCleanerUpper.start();
        } catch (Exception ex) {
            LOGGER.error("CacheManager cleaner init.", ex);
        }
    }

    public static void putCache(ICacheable object) {
        cacheHashMap.putIfAbsent(object.getKey(), object);
    }

    public static ICacheable getCache(String key) {
        ICacheable object = (ICacheable) cacheHashMap.get(key);
        if (object == null) return null;
        if (object.isExpired()) {
            cacheHashMap.remove(key);
            return null;
        } else {
            return object;
        }
    }
}

