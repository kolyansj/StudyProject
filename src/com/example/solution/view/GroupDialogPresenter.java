/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.view;

import com.example.solution.ViewMode;
import com.example.solution.ViewController;
import com.example.solution.model.Group;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private TextField txtFaculty;
    @FXML
    private TextField txtGroupNumber;
    
    private ViewMode mode;
    private Stage dialogStage;
    private Group group;
    private boolean okClicked = false;
    
    private ViewController ctrl;

    public GroupDialogPresenter() {
        this.mode = ViewMode.Create; // by Default
    }
    
    @FXML
    private void initialize() {}
    
    @FXML
    private void handleApprove() {
        if (isInputValid()) {            
            switch(mode) {
                case Create: {                                        
                    ctrl.createGroup(
                            Integer.parseInt(txtGroupNumber.getText()),
                            txtFaculty.getText());
                }
                break;
                case Edit: {                                       
                    ctrl.updateGroup(group.getId(), 
                            Integer.parseInt(txtGroupNumber.getText()), 
                            txtFaculty.getText());
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

    public void setMode(ViewMode mode) {
        this.mode = mode;
    }    

    public void setGroup(Group group) {
        this.group = group;
        txtGroupNumber.setText(String.valueOf(group.getNumber()));
        txtFaculty.setText(group.getFaculty());
    }

    public boolean isOkClicked() {
        return okClicked;
    }    

    private boolean isInputValid() {
        if (txtGroupNumber.getText() == null || txtGroupNumber.getText().isEmpty()) {
            return false;
        }
        if (txtFaculty.getText() == null || txtFaculty.getText().isEmpty()) {
            return false;
        }
        return true;
    }
    
    public void setController(ViewController ctrl) {
        this.ctrl = ctrl;
    }
}
