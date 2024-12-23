package org.example.lab4;

import java.util.ArrayList;
import java.util.HashMap;

public interface ICPU {
    public void exec(Command c);
    public HashMap<String, Integer> getR();
    public int[] getRAM();
    public void clear();
}
