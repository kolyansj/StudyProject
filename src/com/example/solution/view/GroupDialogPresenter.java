/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.view;

import com.example.solution.ViewMode;
import com.example.solution.ViewController;
import com.example.solution.model.Faculty;
import com.example.solution.model.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Answer
 */
public class GroupDialogPresenter {
    
    @FXML
    private Button btCancel;
    @FXML
    private Button btApprove;    
    @FXML
    private TextField txtGroupNumber;
    @FXML
    private ChoiceBox<Faculty> boxFaculties;
    
    private ViewMode mode;
    private Stage dialogStage;
    private Group group;
    private boolean okClicked = false;
    
    private ViewController ctrl;

    public GroupDialogPresenter() {
        this.mode = ViewMode.Create; // by Default
    }
    
    @FXML
    private void initialize() {
        
    }
    
    @FXML
    private void handleApprove() {
        if (isInputValid()) {            
            switch(mode) {
                case Create: {                                        
                    ctrl.createGroup(
                            Integer.parseInt(txtGroupNumber.getText()),
                            boxFaculties.getValue());
                }
                break;
                case Edit: {                                       
                    ctrl.updateGroup(group.getId(), 
                            Integer.parseInt(txtGroupNumber.getText()), 
                            boxFaculties.getValue());
                }
                break;
                default: throw new UnsupportedOperationException();
            }
            okClicked = true;
        }
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public ViewMode getMode() {
        return mode;
    }

    @FXML
    private Label lbHeader;
    
    public void setMode(ViewMode mode) {
        this.mode = mode;
        switch(mode){
            case Create: {
                lbHeader.setText("Создание группы");
            }
            break;
            case Edit: {
                lbHeader.setText("Редактирование группы");
            }
            break;
            default: throw new UnsupportedOperationException();
        }
    }    

    public void setGroup(Group group) {
        this.group = group;
        txtGroupNumber.setText(String.valueOf(group.getNumber()));
    }

    public boolean isOkClicked() {
        return okClicked;
    }    

    private boolean isInputValid() {
        if (txtGroupNumber.getText() == null || txtGroupNumber.getText().isEmpty()) {
            return false;
        }
        if (boxFaculties.getSelectionModel().getSelectedItem().toString().isEmpty()) {
            return false;
        }
        return true;
    }
    
    public void setController(ViewController ctrl) {
        this.ctrl = ctrl;
        ObservableList<Faculty> facs = FXCollections.observableArrayList();
        facs.addAll(ctrl.getData());
        boxFaculties.setItems(facs);
        boxFaculties.getSelectionModel().selectFirst();
    }
}
