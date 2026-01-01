package graph;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    public final String name;
    public List<Agent> subs;
    public List<Agent> pubs;

    public Topic(String name){
        this.name=name;
        this.subs = new ArrayList<>();
        this.pubs = new ArrayList<>();
    }

    public void subscribe(Agent a){
        this.subs.add(a);
    }
    public void unsubscribe(Agent a){
        this.subs.remove(a);
    }

    public void publish(Message m){
        for (Agent agent : subs) {
            agent.callback(this.name, m);
        }
    }

    public void addPublisher(Agent a){
        pubs.add(a);
    }

    public void removePublisher(Agent a){
        pubs.remove(a);
    }
    public List<Agent> getSubscribers(){
        return subs;
    }
    public List<Agent> getPublishers(){
        return pubs;
    }
    public String getName(){
        return name;
    }
}
