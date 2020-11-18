package com.mti.hivers.impl.provider;

import java.util.concurrent.Callable;

public class Prototype<T> {

    public T object;
    public Class<T> type;

    //called when the object is constructed
    public Prototype(Class<T> type, Object obj) {
        this.object = (type.cast(obj));
        this.type = type;
    }

    //called when calling the constructor function
    public Prototype(Class<T> type, Callable<T> obj) {
        try {
            this.object = obj.call();
            this.type = type;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
