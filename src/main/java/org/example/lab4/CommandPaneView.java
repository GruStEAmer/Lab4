package org.example.lab4;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class CommandPaneView {
    Program p = BProgram.build();
    @FXML
    Pane PaneCommand;

    @FXML
    Button exit;

    @FXML
    Button up;

    @FXML
    Button down;

    @FXML
    Label com;

    @FXML
    Label x;

    @FXML
    Label y;

    @FXML
    Label index;

    @FXML
    private void buttonExit(){
        p.remove(Integer.parseInt(index.getText()));
    }
    @FXML
    private void upper(){
        p.upper(Integer.parseInt(index.getText()));
    }
    @FXML
    private void downer(){
        p.downer(Integer.parseInt(index.getText()));
    }
}
