package fr.epita.broker.message;

import java.util.ArrayList;
import java.util.List;

public class Topic {

    public final String name;
    private final List<Partition> partitions;

    public Topic(String name, int nbrPartition) {
        this.name = name;
        this.partitions = new ArrayList<>(nbrPartition);
    }

    public Topic(String name, List<Partition> partitions) {
        this.name = name;
        this.partitions = partitions;
    }
}
