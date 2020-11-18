package com.mti.hivers.impl.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Hivers {

    //list providers
    private Map<Class, Singleton> singletons;
    private Map<Class, Prototype> prototypes;

    public Hivers() {
        singletons = new HashMap<>();
        prototypes = new HashMap<>();
    }

    public <T> void addProvider(Singleton<T> obj) {
        singletons.put(obj.getType(), (Singleton<T>)obj);
    }

    public <T> void addProvider(Prototype<T> obj) {
        prototypes.put(obj.getType(), (Prototype<T>)obj);
    }

    public <T> T instanceOfOrThrow(Class<T> type) {
        if (singletons.containsKey(type)) {
            return (T)singletons.get(type).getObject();
        }
        if (prototypes.containsKey(type))
        {
            return (T)singletons.get(type).getObject();
        }
        throw new RuntimeException("Instance not found");
    }

    public <T> Optional<T> instanceOf(Class<T> type) {
        if (singletons.containsKey(type)) {
            return Optional.of((T)singletons.get(type).getObject());
        }
        if (prototypes.containsKey(type))
        {
            return Optional.of((T)singletons.get(type).getObject());
        }
        return Optional.empty();
    }

}
