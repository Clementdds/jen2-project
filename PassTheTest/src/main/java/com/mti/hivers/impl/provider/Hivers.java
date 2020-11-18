package com.mti.hivers.impl.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Hivers {

    //list providers
    private Map<Class, Singleton> singletons;
    private Map<Class, Prototype> prototypes;

    public Hivers() {
        singletons = new HashMap<>();
        prototypes = new HashMap<>();
    }

    public <T> void addProvider(Singleton<T> obj) {
        singletons.put(obj.getType(), obj);
    }

    public <T> void addProvider(Prototype<T> obj) {
        prototypes.put(obj.getType(), obj);
    }

    public <T> T instanceOfOrThrow(Class<T> type) {
        if (singletons.containsKey(type)) {
            return (T)singletons.get(type).getObject();
        }
        if (prototypes.containsKey(type))
        {
            return (T)prototypes.get(type).getCallable();
        }
        throw new RuntimeException("Instance not found");
    }

    public <T> Optional<T> instanceOf(Class<T> type) {
        if (singletons.containsKey(type)) {
            return Optional.of((T)singletons.get(type).getObject());
        }
        if (prototypes.containsKey(type))
        {
            return Optional.of((T)prototypes.get(type).getCallable());
        }
        return Optional.empty();
    }

}
