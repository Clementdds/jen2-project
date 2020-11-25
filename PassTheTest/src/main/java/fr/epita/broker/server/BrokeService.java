package fr.epita.broker.server;

import fr.epita.broker.database.Database;
import fr.epita.broker.message.Topic;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BrokeService {

    public Database database;
    public BrokeService(Database database){
        this.database = database;
    }

    public PostMessageResponse postTopic(final String name, final int partitions) {
        Topic topic = new Topic(name, partitions);
        boolean result = database.topics.contains(topic);
        if (result){
            return new PostMessageResponse(200, "ok");
        }
        database.topics.add(topic);
        return new PostMessageResponse(409, "Topic already exist");
    }

    public List<Long> postMessages(final List<String> messages, final String topicName, final String groupId) {
        if (database.topics.contains(topicName)){
            Topic newTopic = new Topic(topicName, 42);
            database.topics.add(newTopic);
        }
        List<Topic> topics = database.topics.stream().filter(topic_ -> topic_.name.equals(topicName)).collect(Collectors.toList());
        if (topics.size() == 0) {
            return Collections.emptyList();
        }
        else {
            Topic topic = topics.get(0);
            return topic.addMessages(messages, Integer.parseInt(groupId));
        }
    }

    public static class PostMessageResponse {
        public final int StatusCode;
        public final String Message;

        public PostMessageResponse(int statusCode, String message) {
            StatusCode = statusCode;
            Message = message;
        }
    }
}