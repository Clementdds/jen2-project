package fr.epita.broker.message;

import java.util.ArrayList;
import java.util.List;

public class Partition {

    public List<Message> messages;

    public Partition() {
        this.messages = new ArrayList<>();
    }

    public Partition(List<Message> messages) {
        this.messages = messages;
    }
}
