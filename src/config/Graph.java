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
    public void createFromTopics(){ 
            this.clear();
            TopicManager tm = TopicManagerSingleton.get();
            HashMap<String, Node> nodeMap = new HashMap<>();
            ArrayList<String> topicNames = tm.getAllTopicNames();

            //  Creating nodes for each topic
            for (String topicName : topicNames) {
                Topic topic = tm.getTopic(topicName);
                Node topicNode = new Node("T" + topic.getName());
                nodeMap.put("T" + topic.getName(), topicNode);

                
                for (Agent publisher : topic.getPublishers()) {
                    Node agentNode = new Node(publisher.getName());
                    nodeMap.put(publisher.getName(), agentNode);

                }
                
                
                for (Agent subscriber : topic.getSubscribers()) {
                    Node agentNode = new Node(subscriber.getName());
                    nodeMap.put(subscriber.getName(), agentNode);
                }
            }
        
            // Adding all nodes to the graph
            for (Node node : nodeMap.values()) {
                this.add(node);
            }

           
    }
}

// private Node getOrCreateAgentNode(String agentName, HashMap<String, Node> nodeMap) {
//     Node agentNode = nodeMap.get(agentName);
//     if (agentNode == null) {
//         agentNode = new Node(agentName);
//         this.add(agentNode);
//         nodeMap.put(agentName, agentNode);
//     }
//     return agentNode;
// }
       

    

