/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.view;

import com.example.solution.ViewController;
import com.example.solution.ViewMode;
import com.example.solution.model.Faculty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Answer
 */
public class FacultyDialogPresenter {
    @FXML
    private Button btCancel;
    @FXML
    private Button btApprove;    
    @FXML
    private TextField txtFacultyId;
    @FXML
    private TextField txtFacultyName;
    @FXML
    private TextField txtFacultyCutName;
    @FXML
    private Label lbHeader;
    
    private ViewMode mode;
    private Stage dialogStage;
    private Faculty faculty;
    private boolean okClicked = false;
    
    private ViewController ctrl;

    public FacultyDialogPresenter() {
        this.mode = ViewMode.Create; // by Default
    }
    
    @FXML
    private void initialize() {}
    
    @FXML
    private void handleApprove() {
        if (isInputValid()) {            
            switch(mode) {
                case Create: {                                        
                    ctrl.createFaculty(
                            txtFacultyId.getText(),
                            txtFacultyName.getText(),
                            txtFacultyCutName.getText());
                }
                break;
                case Edit: {                                       
                    ctrl.updateFaculty(
                            txtFacultyId.getId(), 
                            txtFacultyName.getText(), 
                            txtFacultyCutName.getText());
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

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
        txtFacultyId.setText(faculty.getId());
        txtFacultyName.setText(faculty.getName());
        txtFacultyCutName.setText(faculty.getCutName());
    }

    public boolean isOkClicked() {
        return okClicked;
    }    

    private boolean isInputValid() {
        if (txtFacultyId.getText() == null || txtFacultyId.getText().isEmpty()) {
            return false;
        }
        if (txtFacultyName.getText() == null || txtFacultyName.getText().isEmpty()) {
            return false;
        }
        if (txtFacultyCutName.getText() == null || txtFacultyCutName.getText().isEmpty()) {
            return false;
        }
        return true;
    }
    
    public void setController(ViewController ctrl) {
        this.ctrl = ctrl;
    }
}
