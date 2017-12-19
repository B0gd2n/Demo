package com.example.demo.common.cache.impl;

import com.example.demo.common.cache.ICacheable;

/**
 * Created by tito on 11/24/2017.
 */
public class CachedObject<T> implements ICacheable {
    private java.util.Date dateofExpiration = null;
    private String key = null;
    private T object = null;

    public CachedObject(String key, T obj, int minutesToLive) {
        this.object = obj;
        this.key = key;
        //if 0 when cache stays forever.
        if (minutesToLive != 0) {
            dateofExpiration = new java.util.Date();
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(dateofExpiration);
            cal.add(cal.MINUTE, minutesToLive);
            dateofExpiration = cal.getTime();
        }
    }

    public boolean isExpired() {
        //if 0 when cache stays forever.
        if (dateofExpiration != null) {
            if (dateofExpiration.before(new java.util.Date())) {
                return true;
            } else {
                return false;
            }
        } else
            return false;
    }

    public T getObject() {
        return object;
    }

    public String getKey() {
        return key;
    }
}
