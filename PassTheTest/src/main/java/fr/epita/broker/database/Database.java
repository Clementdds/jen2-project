package fr.epita.broker.database;

import fr.epita.broker.consumer.Group;
import fr.epita.broker.consumer.Subscription;
import fr.epita.broker.message.Topic;

import java.util.ArrayList;
import java.util.List;

//In real life just create a real database and not this
public class Database {
    public List<Topic> topics;
    public List<Group> groups;
    public Subscription subscription;

    public Database() {
        this.topics = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.subscription = new Subscription();
    }

    public int getNbrConsumer(){
        return groups.stream().mapToInt(x -> x.consumers.size()).sum();
    }
}
