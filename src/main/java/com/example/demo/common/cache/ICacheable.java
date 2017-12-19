package com.example.demo.common.cache;

/**
 * Created by tito on 11/24/2017.
 */
public interface ICacheable<T> {
    boolean isExpired();
    String getKey();
    T getObject();
}
