package com.mti.hivers.impl.provider;

import javax.management.InstanceNotFoundException;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hivers {

    //list providers
    private List<Provider> providers;

    public Hivers() {
        providers = new ArrayList<>();
    }

    public <T> void addProvider(Singleton<T> obj) {
        providers.add((Provider<T>)obj);
    }

    public <T> void addProvider(Prototype<T> obj) {
        providers.add((Provider<T>)obj);
    }

    public <T> T instanceOfOrThrow(Class<T> type) {
        for (var provider: providers) {
            if (provider.getType().getTypeName().equals(type.getTypeName())) {
                return (T)provider.getObject();
            }
        }
        throw new RuntimeException("Instance not found");
    }

    public <T> Optional<T> instanceOf(Class<T> type) {
        for (var provider: providers) {
            if (provider.getType().getTypeName().equals(type.getTypeName())) {
                return Optional.of((T)provider.getObject());
            }
        }
        return Optional.empty();
    }

}
