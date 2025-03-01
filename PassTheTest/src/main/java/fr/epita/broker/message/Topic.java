package fr.epita.broker.message;

import fr.epita.broker.consumer.Group;

import java.util.ArrayList;
import java.util.List;

public class Topic {

    public final String name;
    private final List<Partition> partitions;
    private final int nbrPartition;

    public Topic(String name, int nbrPartition) {
        this.name = name;
        this.nbrPartition = nbrPartition;
        this.partitions = new ArrayList<>();
        for (int i = 0; i < nbrPartition; i++) {
            this.partitions.add(new Partition());
        }
    }

    public Topic(String name, List<Partition> partitions) {
        this.name = name;
        this.partitions = partitions;
        this.nbrPartition = partitions.size();
    }

    public void addPartition(){
        this.partitions.add(new Partition());
    }

    public int getNbrPartition(){
        return this.partitions.size();
    }

    public int addMessage(Message message, int groupId){
        int hash = groupId % nbrPartition;

        partitions.get(hash).messages.add(message);
        return partitions.get(hash).messages.size() - 1;
    }

    public List<Long> addMessages(List<String> messages, int groupId){
        List<Long> result = new ArrayList<>();
        messages.forEach((x) -> result.add((long)this.addMessage(new Message(x), groupId)));
        return result;
    }

    public List<Message> getMessages(Group group){
        int hash = group.groupId % partitions.size();

        return partitions.get(hash).messages;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String){
            return this.name.equals(obj);
        }
        if (obj instanceof Topic){
            Topic topic = (Topic) obj;
            return this.name.equals(topic.name);
        }
        return false;
    }
}
