package test;

import java.util.ArrayList;

import test.TopicManagerSingleton.TopicManager;

public class PlusAgent implements Agent {
 private String agentName = "PlusAgent";
 private ArrayList<String> subs;
 private ArrayList<String> pubs;
 private TopicManager tm;
 private double x = 0;
 private double y = 0;

 public PlusAgent(ArrayList<String> subs, ArrayList<String> pubs) {
    this.subs = subs;
    this.pubs = pubs;
    this.tm = TopicManagerSingleton.get();
    if(subs.size()>=2){
        tm.getTopic(subs.get(0)).subscribe(this);
        tm.getTopic(subs.get(1)).subscribe(this);
    }
    if(pubs.size()>=1){
        tm.getTopic(pubs.get(0)).addPublisher(this);
    }

 }
 @Override
    public String getName() {
        return agentName;
    }
 @Override 
    public void callback(String topic, Message msg) {
        if(topic.equals(subs.get(0))){
            x = msg.asDouble;
        } else if(topic.equals(subs.get(1))){
            y = msg.asDouble;
        }
        if (x != 0 && y != 0) {
            double result = x + y;
            tm.getTopic(pubs.get(0)).publish(new Message(result));
        }
    }

 @Override 
    public void reset(){
        x = 0;
        y = 0;
    }
 @Override
 public void close(){
    tm.getTopic(subs.get(0)).unsubscribe(this);
    tm.getTopic(subs.get(1)).unsubscribe(this);
    tm.getTopic(pubs.get(0)).removePublisher(this);
 }
}
