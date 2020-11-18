package com.mti.hivers.impl.provider;

public interface Provider<T> {
    public T getObject();
    public Class<T> getType();
}
