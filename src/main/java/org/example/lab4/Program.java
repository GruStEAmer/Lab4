package org.example.lab4;

import java.util.*;

public class Program implements Iterable<Command>{
    private ArrayList<Command> commandList = new ArrayList<>();
    private HashMap<String, Integer> counter = new HashMap<>();
    private ArrayList<Integer> dp = new ArrayList<>();

    ArrayList<IObserver> allObserver = new ArrayList<>();

    public void eventCall()
    {
        allObserver.forEach(action->action.event(this));
    }
    public void addObserver(IObserver e)
    {
        allObserver.add(e);
        eventCall();
    }

    public Program(){}

    public Program(Command c){
        commandList.add(c);

        if(c.info[0] == "st" || c.info[0] == "init"){
            dp.add(Integer.parseInt(c.info[1]));
        }

        if(counter.containsKey(c.info[0])){
            counter.put(c.info[0], 1);
        }
        else
            counter.put(c.info[0], counter.get(c.info[0]) + 1);
        eventCall();
    }

    public void remove(int i){
        commandList.remove(i);
        eventCall();
    }

    public void add(Command c){
        commandList.add(c);

        if(c.info[0] == "st" || c.info[0] == "init"){
            dp.add(Integer.parseInt(c.info[1]));
        }

        String key = c.info[0];
        if (counter.containsKey(key)) {
            counter.put(key, counter.get(key) + 1);
        } else {
            counter.put(key, 1);
        }
        eventCall();
    }

    public void upper(int a){
        if(a > 0){
            Command temp = commandList.get(a);
            commandList.set(a, commandList.get(a - 1));
            commandList.set(a - 1, temp);
        }
        eventCall();
    }
    public void downer(int a){
        if(a < commandList.size() - 1){
            Command temp = commandList.get(a);
            commandList.set(a, commandList.get(a + 1));
            commandList.set(a + 1, temp);
        }
        eventCall();
    }

    public ArrayList<HashMap.Entry<String, Integer>> frequencyCommands() {
        ArrayList<HashMap.Entry<String, Integer>> sortedArrayList = new ArrayList<>(counter.entrySet());
        sortedArrayList.sort(Comparator.comparing(Map.Entry::getValue));
        return sortedArrayList;
    }

    public Pair<Integer,Integer> diap(){
        Pair<Integer,Integer> a = new Pair(Collections.min(dp), Collections.max(dp));
        eventCall();
        return a;
    }


    @Override
    public Iterator<Command> iterator() {
        return new CommandIterator();
    }

    private class CommandIterator implements Iterator<Command> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < commandList.size();
        }

        @Override
        public Command next() {
            return commandList.get(currentIndex++);
        }
    }
}
