package config;

import java.util.function.BinaryOperator;

import graph.Agent;
import graph.Message;
import graph.Topic;




public class BinOpAgent implements Agent {
    private Node agentName;
    private Topic inputTopic1;
    private Topic inputTopic2;
    private Topic outputTopic;
    private BinaryOperator<Double> operation;
    
    private Message msg1 = null;
    private Message msg2 = null;

    public BinOpAgent(Node agentName, Topic inputTopic1, Topic inputTopic2, 
                     Topic outputTopic, BinaryOperator<Double> operation) {
        this.agentName = agentName;
        this.inputTopic1 = inputTopic1;
        this.inputTopic2 = inputTopic2;
        this.outputTopic = outputTopic;
        this.operation = operation;
        
        // Subscribe to input topics
        inputTopic1.subscribe(this);
        inputTopic2.subscribe(this);
    }

    @Override
    public String getName() {
        return agentName.getName();
    }

    @Override
    public void callback(String topic, Message msg) {
        // Store messages from each input topic
        if (topic.equals(inputTopic1.getName())) {
            msg1 = msg;
        } else if (topic.equals(inputTopic2.getName())) {
            msg2 = msg;
        }
        
        // If we have both messages, perform the operation
        if (msg1 != null && msg2 != null) {
            double result = operation.apply(msg1.asDouble, msg2.asDouble);
            outputTopic.publish(new Message(result));
        }
    }

    @Override
    public void reset() {
        msg1 = null;
        msg2 = null;
    }

    @Override
    public void close() {
        // Cleanup if needed
    }
    
    // Getter methods
    public String getAgentName() {
        return agentName.getName();
    }

    public Topic getInputTopic1() {
        return inputTopic1;
    }

    public Topic getInputTopic2() {
        return inputTopic2;
    }

    public Topic getOutputTopic() {
        return outputTopic;
    }

    public BinaryOperator<Double> getOperation() {
        return operation;
    }
}
