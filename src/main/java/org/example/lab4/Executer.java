package org.example.lab4;

public class Executer{
    ICPU cpu;
    Executer(ICPU i){
        cpu = i;
    }

    void run(Program c, int i)
    {
        for(Command com : c) {
            cpu.exec(com);
        }
    }
}
