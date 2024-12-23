package org.example.lab4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class mainController implements IObserver{
    Program prog = BProgram.build();
    ICPU cpu = BCPU.build();
    Executer exec = new Executer(cpu);
    int currentCommand = 0;
    @FXML
    Pane pane;

    @FXML
    Button buttonAddCommand;

    @FXML
    TextField inputText;

    @FXML
    GridPane mainGrid;

    @FXML
    GridPane MemGridPane;

    @FXML
    Label labelA;
    @FXML
    Label labelB;
    @FXML
    Label labelC;
    @FXML
    Label labelD;

    @FXML
    GridPane frequencyPane;

    @FXML
    void initialize(){
        prog.addObserver(this);
        prog.add(new Command("init 10 20"));
        prog.add(new Command("init", "11", "25"));
        prog.add(new Command("ld", "a", "10"));
        prog.add(new Command("ld", "b", "11"));
        prog.add(new Command("ld", "c", "11"));
        prog.add(new Command("add"));
        prog.add(new Command("mult"));
        prog.add(new Command("add"));
        prog.add(new Command("print"));

        for(int i = 0; i < 5;i++){
            for(int j = 0;j < 10;j++) {
                Label a = new Label();
                a.setText((j)*5 + i + 1 + ":0");
                a.setPrefWidth(54);
                a.setPrefHeight(35);
                MemGridPane.add(a,i,j);
            }
        }
    }

    @FXML
    void addCommand(){
        if(!inputText.getText().isEmpty()){
            prog.add(new Command(inputText.getText()));
        }
    }

    @FXML
    void NextCommand(){
        if(currentCommand + 1 < prog.size()){
            currentCommand++;
            prog.eventCall();
        }
    }

    @FXML
    void refresh(){
        currentCommand = 0;
        prog.eventCall();
    }

    @Override
    public void event(Program prog){
        //Executer run
        cpu.clear();
        exec.run(prog,currentCommand);
        //
        //RAM print
        int[] ram = cpu.getRAM();
        MemGridPane.getChildren().clear();
        for(int i = 0; i < 5;i++){
            for(int j = 0 ; j < 10;j++){
                Label a = new Label();
                a.setText(j*5 + i + 1 + ":" + ram[j*5 + i + 1]);
                a.setPrefWidth(54);
                a.setPrefHeight(35);
                if(ram[j*5 + i  + 1] != 0){
                    a.setStyle("-fx-text-fill: red;");
                }
                MemGridPane.add(a,i,j);
            }
        }
        //
        //R print
        HashMap<String,Integer> r = cpu.getR();
        labelA.setText(String.valueOf(r.get("a")));
        labelB.setText(String.valueOf(r.get("b")));
        labelC.setText(String.valueOf(r.get("c")));
        labelD.setText(String.valueOf(r.get("d")));
        //
        //Вывод команд в таблицу
        mainGrid.getChildren().clear();

        int counter = 0;
        for(Command i : prog){
            FXMLLoader fxmlLoader = new FXMLLoader(
                    main.class.getResource("CommandPane-view.fxml")
            );
            try {
                Pane p = fxmlLoader.load();

                Label a = (Label)p.lookup("#com");
                a.setText(i.info[0]);
                if(!i.info[1].equals("0")) {
                    Label b = (Label) p.lookup("#x");
                    b.setText(i.info[1]);
                }
                if(!i.info[2].equals("0")) {
                    Label c = (Label) p.lookup("#y");
                    c.setText(i.info[2]);
                }
                Label d = (Label)p.lookup("#index");
                d.setText(String.valueOf(counter));
                if(counter == currentCommand){
                    p.setStyle("-fx-background-color: red;");
                }
                mainGrid.addColumn(0, p);
            } catch (IOException e) {
                throw new RuntimeException(e);
                }
            counter++;
        }
        //
        //Вывод частоту комманд
       frequencyPane.getChildren().clear();
        for(Map.Entry<String, Integer> i : prog.frequencyCommands().reversed()){
            Label a = new Label();
            a.setPrefWidth(200);
            a.setPrefHeight(40);
            a.setText(i.getKey() + ": " + i.getValue());
            a.setFont(Font.font(16));
            frequencyPane.addColumn(0,a);
        }
    }
}