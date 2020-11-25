package fr.epita.broker.consumer;

import java.util.ArrayList;
import java.util.List;

public class Group {

    static public int id = 0;
    public int groupId;
    public List<Consumer> consumers;
    public int startingHead;

    public Group() {
        this.consumers = new ArrayList<>();
        this.startingHead = 0;
        this.groupId = id++;
    }

    public Group(List<Consumer> consumers, int startingHead) {
        this.consumers = consumers;
        this.startingHead = startingHead;
        this.groupId = id++;
    }
}
