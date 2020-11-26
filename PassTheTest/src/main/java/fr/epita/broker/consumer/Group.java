package fr.epita.broker.consumer;

public class Group {

    public int groupId;
    public int startingHead;

    public Group(int groupId) {
        this.startingHead = 0;
        this.groupId = groupId;
    }
}
