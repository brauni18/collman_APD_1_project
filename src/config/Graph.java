package config;

import java.util.ArrayList;
import java.util.HashMap;

import graph.Agent;
import graph.Topic;
import graph.TopicManagerSingleton;
import graph.TopicManagerSingleton.TopicManager;

public class Graph extends ArrayList<Node>{
    
    public boolean hasCycles() {
        for (Node node : this) {
            if (node.hasCycles()) {
                return true;
            }
        }
        return false;
    }
    public void createFromTopics() {
        this.clear();
        TopicManager tm = TopicManagerSingleton.get();
        HashMap<String, Node> nodeMap = new HashMap<>();
        ArrayList<String> topicNames = tm.getAllTopicNames();
        
        for (String topicName : topicNames) {
            Topic topic = tm.getTopic(topicName);
            
            // Create topic node
            Node topicNode = new Node("T" + topic.getName());
            nodeMap.put("T" + topic.getName(), topicNode);
            this.add(topicNode);

            // Handle publishers
            for (Agent publisher : topic.getPublishers()) {
                Node publisherNode = nodeMap.get(publisher.getName());
                if (publisherNode == null) {
                    publisherNode = new Node(publisher.getName());
                    nodeMap.put(publisher.getName(), publisherNode);
                    this.add(publisherNode);
               }
                publisherNode.addEdge(topicNode);
               
            }

            // Handle subscribers  
            for (Agent subscriber : topic.getSubscribers()) {
                
                Node subscriberNode = nodeMap.get(subscriber.getName());
                if (subscriberNode == null) {
                    subscriberNode = new Node(subscriber.getName());
                    nodeMap.put(subscriber.getName(), subscriberNode);
                    this.add(subscriberNode);
                   
                }
                topicNode.addEdge(subscriberNode);
                
            }
        }
        
        
    }
}





