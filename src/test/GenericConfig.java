package test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GenericConfig implements Config{
    private String confFile;
    private Set<String> topics = new HashSet<String>();
    private List<ParallelAgent> activeAgents = new ArrayList<ParallelAgent>();

    public GenericConfig(){
        this.topics = new HashSet<String>();
        this.activeAgents = new ArrayList<ParallelAgent>();
    }

    
   public void setConfFile (String confFile){
       this.confFile = confFile;
   } 
@Override
    public void create() {
        if (confFile == null || confFile.isEmpty()) {
            return;
        }
        
        try (Stream<String> lines = Files.lines(Paths.get(confFile))) {
            // Read all lines
            List<String> allLines = lines
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .filter(line -> !line.startsWith("#"))
                .collect(Collectors.toList());
            
            // Validate divisible by 3
            if (allLines.size() % 3 != 0) {
                System.err.println("Error: Config file format invalid. Lines: " + allLines.size());
                return;
            }
            
            // Extract and create topics
            topics.clear();
            for (int i = 1; i < allLines.size(); i += 3) {
                // Process both subscriber and publisher lines
                Arrays.stream(allLines.get(i).split(","))
                    .map(String::trim)
                    .forEach(topics::add);
                
                Arrays.stream(allLines.get(i + 1).split(","))
                    .map(String::trim)
                    .forEach(topics::add);
            }
            
            // Create all topics in TopicManager
            TopicManagerSingleton.TopicManager tm = TopicManagerSingleton.get();
            topics.forEach(t -> tm.getTopic(t));
            
            // Create agents
            for (int i = 0; i < allLines.size(); i += 3) {
                String className = allLines.get(i);
                String subsLine = allLines.get(i + 1);
                String pubsLine = allLines.get(i + 2);
                
                createAgent(className, subsLine, pubsLine);
            }
            
        } catch (IOException e) {
            System.err.println("Error reading config: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createAgent(String className, String subsLine, String pubsLine) {
        try {
            if (className == null || subsLine == null || pubsLine == null) {
            System.err.println("Error: Null parameters in config");
            return;
        }
            // Parse subs and pubs
            ArrayList<String> subs = new ArrayList<>();
            for (String s : subsLine.split(",")) {
                subs.add(s.trim());
            }
            
            ArrayList<String> pubs = new ArrayList<>();
            for (String p : pubsLine.split(",")) {
                pubs.add(p.trim());
            }
              String fullClassName = className;
              
            if (!className.contains(".")) {
                fullClassName = "test." + className;
            }
        
        // Create agent using reflection
            Class<?> agentClass = Class.forName(fullClassName);
            Constructor<?> constructor = agentClass.getConstructor(String[].class, String[].class);
            Agent agent = (Agent) constructor.newInstance(subs.toArray(new String[0]), pubs.toArray(new String[0]));

            // Wrap in ParallelAgent for threading
            ParallelAgent pa = new ParallelAgent(agent);
            activeAgents.add(pa);
            
            
        } catch (Exception e) {
            System.err.println("Error creating agent: " + className);
            e.printStackTrace();
        }
    }
@Override
    public String getName() {
        return "GenericConfig";
    }
@Override
    public int getVersion() {
        return 1;
    }
@Override
    public void close() {
        // Stop all parallel agents
        for (ParallelAgent pa : activeAgents) {
            pa.close();
        }
        activeAgents.clear();
        topics.clear();
    }

}
