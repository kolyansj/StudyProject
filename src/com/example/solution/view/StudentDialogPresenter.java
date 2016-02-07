/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.view;

import com.example.solution.ViewMode;
import com.example.solution.ViewController;
import com.example.solution.Util;
import com.example.solution.model.Faculty;
import com.example.solution.model.Group;
import com.example.solution.model.Student;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
    private ChoiceBox<Faculty> boxFaculty;
    @FXML
    private ChoiceBox<Group> boxGroupNumber;
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
    private void initialize() { 
        
        boxFaculty.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Faculty>() {
            @Override
            public void changed(ObservableValue<? extends Faculty> observable,
                    Faculty oldValue, Faculty newValue) {
                boxGroupNumber.setItems(FXCollections.observableArrayList(newValue.getGroups()));
                boxGroupNumber.getSelectionModel().selectFirst();
            }
        });
    }
    
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
                            txtLastName.getText(), 
                            boxGroupNumber.getValue(),
                            Util.parseDate(txtStartDate.getText()));
                }
                break;
                case Edit: {                    
                    ctrl.updateStudent(student.getId(),
                            txtName.getText(),
                            txtMiddleName.getText(),
                            txtLastName.getText(), 
                            boxGroupNumber.getValue(),
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
        //List<Group> groups = ctrl.getData();
        //Set<Integer> nums = new HashSet<>();
        //Set<Faculty> facs = new HashSet<>();
        //for (Group group : groups) {
        //    nums.add(group.getNumber());
        //    facs.add(group.getFaculty());
        //}
        //boxGroupNumber.setItems(FXCollections.observableArrayList(nums));
        //boxFaculty.setItems(FXCollections.observableArrayList(facs));
        ObservableList<Faculty> facs = FXCollections.observableArrayList();
        facs.addAll(ctrl.getData());
        boxFaculty.setItems(facs);
        boxFaculty.getSelectionModel().selectFirst();
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
                lbHeader.setText("Добавление студента");
            }
            break;
            case Edit: {
                lbHeader.setText("Редактирование студента");
            }
            break;
            default: throw new UnsupportedOperationException();
        }
    }

    public void setStudent(Student student) {
        this.student = student;
        txtName.setText(student.getName());
        txtMiddleName.setText(student.getMiddleName());
        txtLastName.setText(student.getLastName());
        boxFaculty.setValue(student.getGroup().getFaculty());
        boxGroupNumber.setValue(student.getGroup());
        txtStartDate.setText(Util.formatDate(student.getStartStudyDate()));
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    private boolean isInputValid() {
        if (txtName.getText() == null || txtName.getText().isEmpty()) {
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
        
        return true;       
    }
}
