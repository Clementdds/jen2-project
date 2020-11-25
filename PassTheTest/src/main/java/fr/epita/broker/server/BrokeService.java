package fr.epita.broker.server;

import fr.epita.broker.consumer.Consumer;
import fr.epita.broker.consumer.Group;
import fr.epita.broker.database.Database;
import fr.epita.broker.message.Message;
import fr.epita.broker.message.Topic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BrokeService {

    public Database database;

    public BrokeService(Database database) {
        this.database = database;
    }

    /*
     * Create topic
     */
    public PostMessageResponse postTopic(final String name, final int partitions) {
        Topic topic = new Topic(name, partitions);
        boolean result = database.topics.contains(topic);
        if (result) {
            return new PostMessageResponse(409, "Topic already exist");
        }
        database.topics.add(topic);
        return new PostMessageResponse(200, "ok");
    }

    /*
     * Post messages
     */
    public List<Long> postMessages(final List<String> messages, final String topicName, final String groupId) {
        if (!database.topics.contains(topicName)) {
            Topic newTopic = new Topic(topicName, 42);
            database.topics.add(newTopic);
        }
        List<Topic> topics = database.topics.stream().filter(topic_ -> topic_.name.equals(topicName)).collect(Collectors.toList());
        if (topics.size() == 0) {
            return Collections.emptyList();
        } else {
            Topic topic = topics.get(0);
            return topic.addMessages(messages, Integer.parseInt(groupId));
        }
    }

    /*
     * Get messages
     */
    public GetMessageResponse fetchMessages(final String subscriptionId, final String upTo, final String wait){
        Date date = new Date();
        long startTimeMilli = date.getTime();

        Consumer consumer = new Consumer(Integer.parseInt(subscriptionId));
        if (!database.subscription.subscription.containsKey(consumer)){
            return new GetMessageResponse(404, null);
        }
        var groupTopicPair = database.subscription.subscription.get(consumer);
        List<Message> messages = groupTopicPair.right.getMessages(groupTopicPair.left);

        long waitTimeNb = Long.parseLong(wait);
        var currentHead = groupTopicPair.left.startingHead;
        List<String> result = new ArrayList<>();
        for (int i = currentHead; currentHead < Integer.parseInt(upTo); currentHead++) {
            Date date2 = new Date();
            long currentTimeMilli = date2.getTime();
            if ((currentTimeMilli - startTimeMilli) >=  waitTimeNb) {
                break;
            }

            result.add(messages.get(currentHead).text);
        }
        groupTopicPair.left.startingHead = currentHead;
        return new GetMessageResponse(200, result);
    }

    /*
     * Create consumer to a group
     */
    public PostSubscriptionResponse postSubscription(final String topicName, final String groupId) {
        if (!database.topics.contains(topicName)) {
            return new PostSubscriptionResponse(404, null, null, "Topic does not exist");
        }
        List<Topic> topics = database.topics.stream().filter(topic_ -> topic_.name.equals(topicName)).collect(Collectors.toList());
        if (topics.size() == 0) {
            return new PostSubscriptionResponse(404, null, null, "Topic does not exist");
        } else {
            Topic topic = topics.get(0);
            List<Group> groups = database.groups.stream().filter(group -> group.groupId == Integer.parseInt(groupId)).collect(Collectors.toList());
            Group group;
            if (groups.size() != 0){
                group = groups.get(0);
            }
            else {
                group = new Group();
            }
            int result = database.subscription.addSubscription(group, topic);
            return new PostSubscriptionResponse(200, groupId, result, null);
        }
    }

    /*
     * Delete consumer
     */
    public DeleteSubscriptionResponse deleteSubscription(final String subscriptionId) {
        int subId = Integer.parseInt(subscriptionId);
        if (!database.subscription.subscription.containsKey(subId)) {
            return new DeleteSubscriptionResponse(404, "Not found");
        }
        database.subscription.deleteSubscription(subId);
        return new DeleteSubscriptionResponse(200, "ok");
    }


    /*
     * Different responses
     */
    public static class PostSubscriptionResponse {
        public final int StatusCode;
        public final String groupId;
        public final Integer subscriptionId;
        public final String errorMessage;

        public PostSubscriptionResponse(int statusCode, String groupId, Integer subscriptionId, String errorMessage) {
            StatusCode = statusCode;
            this.groupId = groupId;
            this.subscriptionId = subscriptionId;
            this.errorMessage = errorMessage;
        }
    }

    public static class DeleteSubscriptionResponse {
        public final int StatusCode;
        public final String Message;

        public DeleteSubscriptionResponse(int statusCode, String message) {
            StatusCode = statusCode;
            Message = message;
        }
    }

    public static class GetMessageResponse {
        public final int StatusCode;
        public final List<String> Messages;

        public GetMessageResponse(int statusCode, List<String> messages) {
            StatusCode = statusCode;
            Messages = messages;
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