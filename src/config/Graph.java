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
            TopicManager tm = TopicManagerSingleton.get();
            HashMap<String, Node> nodeMap = new HashMap<>();
            this.clear();

           for (String topicName : tm.getAllTopicNames()) {
        Topic topic = tm.getTopic(topicName);
        Node topicNode = nodeMap.get("T" + topicName);
        
        // Step 3: Handle publishers (Agent -> Topic)
        for (Agent publisher : topic.getPublishers()) {
            Node agentNode = getOrCreateAgentNode(publisher.getName(), nodeMap);
            agentNode.addEdge(topicNode);
        }
        
        // Step 4: Handle subscribers (Topic -> Agent)
        for (Agent subscriber : topic.getSubscribers()) {
            Node agentNode = getOrCreateAgentNode(subscriber.getName(), nodeMap);
            topicNode.addEdge(agentNode);
        }
    }
}

private Node getOrCreateAgentNode(String agentName, HashMap<String, Node> nodeMap) {
    Node agentNode = nodeMap.get(agentName);
    if (agentNode == null) {
        agentNode = new Node(agentName);
        this.add(agentNode);
        nodeMap.put(agentName, agentNode);
    }
    return agentNode;
}
    }    

    

