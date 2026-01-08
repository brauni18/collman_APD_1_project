package test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import test.TopicManagerSingleton.TopicManager;

public class IncAgent implements Agent {
    private String agentName = "IncAgent";
    private ArrayList<String> sub;
    private ArrayList<String> pub;
    private TopicManager tm;
    private double value = 0;

    public IncAgent(ArrayList<String> sub, ArrayList<String> pub) {
        this.sub = sub;
        this.pub = pub;
        this.tm = TopicManagerSingleton.get();
        tm.getTopic(sub.get(0)).subscribe(this);
        tm.getTopic(pub.get(0)).addPublisher(this);
    }

    @Override
    public String getName() {
        return agentName;
    }

    @Override
    public void callback(String topic, Message msg) {
        if (topic.equals(sub.get(0))) {
            value = msg.asDouble + 1;
            tm.getTopic(pub.get(0)).publish(new Message(value));
        }
    }

    @Override
    public void reset() {
        value = 0;
    }

    @Override
    public void close() {
        tm.getTopic(sub.get(0)).unsubscribe(this);
        tm.getTopic(pub.get(0)).removePublisher(this);
    }
     
}
