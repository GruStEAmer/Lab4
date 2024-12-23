package org.example.lab4;

public class Executer{
    ICPU cpu;
    Executer(ICPU i){
        cpu = i;
    }

    void run(Program c,int i)
    {
        int counter = 0;
        for(Command com : c) {
            if(counter > i) break;
            cpu.exec(com);
            counter++;
        }
    }
}
