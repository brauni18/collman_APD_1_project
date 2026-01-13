package test;

import java.util.ArrayList;

import test.TopicManagerSingleton.TopicManager;

public class PlusAgent implements Agent {
 private String agentName = "PlusAgent";
 private String[] subs;
 private String[] pubs;
 private TopicManager tm;
 private Double x = null;
 private Double y = null;

 public PlusAgent(String[] subs, String[] pubs) {
    this.subs = subs;
    this.pubs = pubs;
    this.tm = TopicManagerSingleton.get();
    if(subs.length>=2){
        tm.getTopic(subs[0]).subscribe(this);
        tm.getTopic(subs[1]).subscribe(this);
    }
    if(pubs.length>=1){
        tm.getTopic(pubs[0]).addPublisher(this);
    }

 }
 @Override
    public String getName() {
        return agentName;
    }
 @Override 
    public void callback(String topic, Message msg) {
        if(topic.equals(subs[0])){
            x = msg.asDouble;
        } else if(topic.equals(subs[1])){
            y = msg.asDouble;
        }
        if (x != null && y != null) {
            double result = x + y;
            tm.getTopic(pubs[0]).publish(new Message(result));

            reset();
        }

    }

 @Override 
    public void reset(){
        x = null;
        y = null;
    }
 @Override
 public void close(){
    tm.getTopic(subs[0]).unsubscribe(this);
    tm.getTopic(subs[1]).unsubscribe(this);
    tm.getTopic(pubs[0]).removePublisher(this);
 }
}
