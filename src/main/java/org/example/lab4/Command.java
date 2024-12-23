package org.example.lab4;


public class Command {
    public String[] info = {"0","0","0"};

    Command(String s) {
        int j = 0;
        for(String i: s.split(" ")){
            info[j] = i;
            j++;
        }
    }
    Command(String s1,String s2, String s3){
        info[0] = s1;
        info[1] = s2;
        info[2] = s3;
    }
}
