package test;

import java.util.function.BinaryOperator;




public class BinOpAgent implements Agent {
    private String agentName;
    private Topic inputTopic1;
    private Topic inputTopic2;
    private Topic outputTopic;
    private BinaryOperator<Double> operation;
    
    private Message msg1 = null;
    private Message msg2 = null;

    public BinOpAgent( String agentName, String inputTopic1Name, String inputTopic2Name, 
                     String outputTopicName, BinaryOperator<Double> operation) {
        this.agentName = agentName;
        this.operation = operation;

        TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();
        inputTopic1 = tm.getTopic(inputTopic1Name);
        inputTopic2 = tm.getTopic(inputTopic2Name);
        outputTopic = tm.getTopic(outputTopicName);

        
        // Subscribe to input topics
        inputTopic1.subscribe(this);
        inputTopic2.subscribe(this);

        outputTopic.addPublisher(this);
    }

    @Override
    public String getName() {
        return agentName;
    }

    @Override
    public void callback(String topic, Message msg) {
        
        if (topic.equals(inputTopic1.getName())) {
            msg1 = msg;
        } else if (topic.equals(inputTopic2.getName())) {
            msg2 = msg;
        }
        
        
        if (msg1 != null && msg2 != null) {
            double result = operation.apply(msg1.asDouble, msg2.asDouble);
            outputTopic.publish(new Message(result));
            reset();
        }
    }

    @Override
    public void reset() {
        msg1 = null;
        msg2 = null;
    }

    @Override
    public void close() {
        
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
