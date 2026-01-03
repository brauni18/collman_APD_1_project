package test;

import java.util.ArrayList;
import java.util.HashMap;

import test.TopicManagerSingleton.TopicManager;

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
            Node topicNode = new Node("T" + topicName);
            nodeMap.put("T" + topicName, topicNode);
            this.add(topicNode);

            // Handle publishers
            for (Agent publisher : topic.getPublishers()) {
                Node publisherNode = nodeMap.get("A" + publisher.getName());
                if (publisherNode == null) {
                    publisherNode = new Node("A" + publisher.getName());
                    nodeMap.put("A" + publisher.getName(), publisherNode);
                    this.add(publisherNode);
               }
                publisherNode.addEdge(topicNode);
               
            }

            // Handle subscribers  
            for (Agent subscriber : topic.getSubscribers()) {

                Node subscriberNode = nodeMap.get("A" + subscriber.getName());
                if (subscriberNode == null) {
                    subscriberNode = new Node("A" + subscriber.getName());
                    nodeMap.put("A" + subscriber.getName(), subscriberNode);
                    this.add(subscriberNode);
                   
                }
                topicNode.addEdge(subscriberNode);
                
            }
        }
        
        
    }
}





