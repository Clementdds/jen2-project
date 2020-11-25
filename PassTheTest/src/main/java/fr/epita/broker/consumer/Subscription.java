package fr.epita.broker.consumer;

import fr.epita.broker.message.Topic;
import fr.epita.tfidf.vectorisation.Pair;

import java.util.HashMap;
import java.util.Map;

public class Subscription {
    public static int id = 0;
    public Map<Integer, Pair<Group, Topic>> subscription;

    public Subscription() {
        subscription = new HashMap<>();
    }

    public void addSubscription(Group group, Topic topic){
        subscription.put(id++, new Pair<>(group, topic));
    }

    public void deleteSubscription(int id){
        subscription.remove(id);
    }
}
