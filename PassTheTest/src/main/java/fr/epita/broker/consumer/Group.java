package fr.epita.broker.consumer;

import java.util.ArrayList;
import java.util.List;

public class Group {

    static public int id = 0;
    public int groupId;
    public List<Consumer> consumers;
    public int startingHead;

    public Group(List<Integer> createdIdGroups) {
        this.consumers = new ArrayList<>();
        this.startingHead = 0;
        while (createdIdGroups.stream().anyMatch(listid -> listid >= id))
            id++;
        this.groupId = id++;
    }

    public Group(int groupId) {
        this.consumers = new ArrayList<>();
        this.startingHead = 0;
        this.groupId = groupId;
    }

    public Group(List<Consumer> consumers, int startingHead) {
        this.consumers = consumers;
        this.startingHead = startingHead;
        this.groupId = id++;
    }
}
