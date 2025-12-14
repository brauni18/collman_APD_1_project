package test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TopicManagerSingleton {

    public static class TopicManager {
        public static final TopicManager instance = new TopicManager();
        private Map<String, Topic> topics;

        private TopicManager() {
            this.topics = new HashMap<>();
        }
        
        public Topic getTopic(String name) {
            return topics.computeIfAbsent(name, topicName -> new Topic(topicName));
        }
        public Collection<Topic> getTopics() {
            return topics.values();
        }
        public void clear() {
            topics.clear();
        }
    }
    public static TopicManager get(){
        return TopicManager.instance;
    }

   
}
