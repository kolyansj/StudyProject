/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.view;

import com.example.solution.ViewMode;
import com.example.solution.ViewController;
import com.example.solution.Util;
import com.example.solution.model.Group;
import com.example.solution.model.Student;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Answer
 */
public class StudentDialogPresenter {
    
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtMiddleName;
    @FXML
    private TextField txtLastName;
    @FXML
    private ChoiceBox boxFaculty;
    @FXML
    private ChoiceBox boxGroupNumber;
    @FXML
    private TextField txtStartDate;

    private ViewMode mode;
    private Stage dialogStage;
    private Student student;
    private Group group;
    private boolean okClicked = false;

    private ViewController ctrl;

    public StudentDialogPresenter() {
        this.mode = ViewMode.Create; // by Default
    }
    
    @FXML
    private void initialize() { }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    @FXML
    private void handleApprove() throws ParseException {
        if (isInputValid()) {            
            switch(mode) {
                case Create: {
                    ctrl.createStudent(txtName.getText(),
                            txtMiddleName.getText(),
                            txtLastName.getText(), group,
                            Util.parseDate(txtStartDate.getText()));
                }
                break;
                case Edit: {                    
                    ctrl.updateStudent(student.getId(),
                            txtName.getText(),
                            txtMiddleName.getText(),
                            txtLastName.getText(), group,
                            Util.parseDate(txtStartDate.getText()));
                }
                break;
                default: throw new UnsupportedOperationException();
            }
            okClicked = true;
        }
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setController(ViewController ctrl) {
        this.ctrl = ctrl;
        List<Group> groups = ctrl.getData();
        Set<Integer> nums = new HashSet<>();
        Set<String> facs = new HashSet<>();
        for (Group group : groups) {
            nums.add(group.getNumber());
            facs.add(group.getFaculty());
        }
        boxGroupNumber.setItems(FXCollections.observableArrayList(nums));
        boxFaculty.setItems(FXCollections.observableArrayList(facs));
    }

    public ViewMode getMode() {
        return mode;
    }
    
    public void setMode(ViewMode mode) {
        this.mode = mode;
    }

    public void setStudent(Student student) {
        this.student = student;
        txtName.setText(student.getName());
        txtMiddleName.setText(student.getMiddleName());
        txtLastName.setText(student.getLastName());
        boxFaculty.setValue(student.getGroup().getFaculty());
        boxGroupNumber.setValue(student.getGroup().getNumber());
        txtStartDate.setText(Util.formatDate(student.getStartStudyDate()));
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    private boolean isInputValid() {
        if (txtName.getText() == null || txtName.getText().isEmpty()) {
            txtName.setText("ЗАДАНО ПУСТОЕ ИМЯ!");
            return false;
        }
        if (txtMiddleName.getText() == null || txtMiddleName.getText().isEmpty()) {
            return false;
        }
        if (txtLastName.getText() == null || txtLastName.getText().isEmpty()) {
            return false;
        }
        if (txtStartDate.getText() == null || txtStartDate.getText().isEmpty()) {
            return false;
        }
        
        String groupId = boxGroupNumber.getValue().toString() + 
                    boxFaculty.getValue().toString();
        switch (mode) {
            case Edit: {
                Group currentGroup = student.getGroup();
                if (currentGroup.getId().equals(groupId)) {
                    this.group = currentGroup;
                    return true;
                }
            }
            case Create: {
                Group newGroup = ctrl.getGroupById(groupId);
                if (newGroup == null) {
                    return false;
                } else {
                    group = newGroup;
                    return true;
                }
            }
            default: throw new UnsupportedOperationException();
        }
    }
}
