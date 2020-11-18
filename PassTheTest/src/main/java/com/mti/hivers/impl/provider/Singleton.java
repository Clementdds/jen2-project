package com.mti.hivers.impl.provider;

import java.util.concurrent.Callable;

public class Singleton<T> {

    public T object;
    public Class<T> type;

    //called when the object is constructed
    public Singleton(Class<T> type, Object obj) {
        this.object = (type.cast(obj));
        this.type = type;
    }

    //called when calling the constructor function
    public Singleton(Class<T> type, Callable<T> obj) {
        try {
            this.object = obj.call();
            this.type = type;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T getObject() {
        return object;
    }

    public Class<T> getType() {
        return type;
    }
}
