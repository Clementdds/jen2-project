package com.mti.hivers.impl.provider;

import java.util.concurrent.Callable;

public class Prototype<T> {

    private Callable<T> call;
    private Class<T> type;

    //called when calling the constructor function
    public Prototype(Class<T> type, Callable<T> call) {
        try {
            this.call = call;
            this.type = type;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T getCallable() {
        try {
            return call.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class<T> getType() {
        return type;
    }
}
