package test;
import test.TopicManagerSingleton.TopicManager;

public class MathExampleConfig implements Config {

    @Override
    public void create() {
        TopicManager tm = TopicManagerSingleton.get();
        
        // Get topics
        Topic topicA = tm.getTopic("A");
        Topic topicB = tm.getTopic("B");
        Topic topicR1 = tm.getTopic("R1");
        Topic topicR2 = tm.getTopic("R2");
        Topic topicR3 = tm.getTopic("R3");
        
        // Create nodes with EXACT names MainTrain expects
        Node plusNode = new Node("Aplus");
        Node minusNode = new Node("Aminus");
        Node mulNode = new Node("Amul");
        
        // Create agents
        new BinOpAgent(plusNode, topicA, topicB, topicR1, (x,y) -> x+y);
        new BinOpAgent(minusNode, topicA, topicB, topicR2, (x,y) -> x-y);
        new BinOpAgent(mulNode, topicR1, topicR2, topicR3, (x,y) -> x*y);
    }

    @Override
    public String getName() {
        return "Math Example";
    }

    @Override
    public int getVersion() {
        return 1;
    }
    
}
