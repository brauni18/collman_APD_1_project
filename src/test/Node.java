package test;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String name;
    private List<Node> edges;
    private Message msg;

public Node(String name){
    this.name = name;
    edges = new ArrayList<>();
}
//-------sets------------//
public void setMessage (Message newMessage){
    msg = newMessage;
}
public void setName(String newName){
    name = newName;
}
//--------gets------------//
public String getName(){
    return name;
}
public List<Node> getEdges(){
    return this.edges;
}
public Message getMessage(){
    return msg;
}
//--------other functions------------//
public void addEdge(Node newNode){
    edges.add(newNode);
}
public boolean hasCycles(){
    return hasCyclesHelper(this, new ArrayList<Node>());
}

private boolean hasCyclesHelper(Node current, List<Node> visited){
    if(visited.contains(current)){
        return true;
    }
    visited.add(current);
    for(Node neighbor : current.getEdges()){
        if(neighbor.hasCyclesHelper(neighbor, visited)){
            return true;
        }
    }
    visited.remove(current);
    return false;
}
}