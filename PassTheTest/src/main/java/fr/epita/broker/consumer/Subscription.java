package fr.epita.broker.consumer;

import fr.epita.broker.message.Topic;
import fr.epita.tfidf.vectorisation.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Subscription {
    public static int id = 0;
    public Map<Consumer, Pair<Group, Topic>> subscription;

    public Subscription() {
        subscription = new HashMap<>();
    }

    public int addSubscription(Group group, Topic topic){
        int currentId = id++;
        subscription.put(new Consumer(currentId), new Pair<>(group, topic));
        return currentId;
    }

    public void deleteSubscription(int id){
        Consumer cons = subscription.keySet().stream().filter(consumer -> consumer.id == id).collect(Collectors.toList()).get(0);
        subscription.remove(cons);
    }
}
