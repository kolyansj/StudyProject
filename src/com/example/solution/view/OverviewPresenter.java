/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.view;

import com.example.solution.ViewController;
import com.example.solution.Util;
import com.example.solution.model.Group;
import com.example.solution.model.Student;
import com.example.solution.model.observer.Observable;
import com.example.solution.model.observer.Observer;
import java.io.IOException;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 *
 * @author Answer
 */
public class OverviewPresenter implements Observer {

    @FXML
    private TableView<Student> studentsTable;
    @FXML
    private TableColumn<Student, String> colName;
    @FXML
    private TableColumn<Student, String> colMiddleName;
    @FXML
    private TableColumn<Student, String> colLastName;
    //@FXML
    //private TableColumn<Student, String> colGroup;
    @FXML
    private TableColumn<Student, String> colStartDate;

    @FXML
    private Label lbName;
    @FXML
    private Label lbMiddleName;
    @FXML
    private Label lbLastName;
    @FXML
    private Label lbFaculty;
    @FXML
    private Label lbGroupNumber;
    @FXML
    private Label lbStartDate;

    @FXML
    private TableView<Group> groupsTable;
    @FXML
    private TableColumn<Group, Integer> colGroupNumber;
    @FXML
    private TableColumn<Group, String> colFaculty;

    @FXML
    private Label lbGroupNumber2;
    @FXML
    private Label lbCount;
    @FXML
    private Label lbFaculty2;

    private ViewController ctrl;

    @FXML
    private void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        colMiddleName.setCellValueFactory(new PropertyValueFactory<Student, String>("middleName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        //colGroup.setCellValueFactory(new PropertyValueFactory<Student, String>("group"));
        colStartDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> p) {
               SimpleStringProperty property = new SimpleStringProperty();
               property.setValue(Util.formatDate(p.getValue().getStartStudyDate()));
               return property;
            }
            
         });

        colGroupNumber.setCellValueFactory(new PropertyValueFactory<Group, Integer>("number"));
        colFaculty.setCellValueFactory(new PropertyValueFactory<Group, String>("faculty"));

        showStudentDetails(null);
        showGroupDetails(null);

        studentsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable,
                    Student oldValue, Student newValue) {
                showStudentDetails(newValue);
            }
        });

        groupsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Group>() {
            @Override
            public void changed(ObservableValue<? extends Group> observable,
                    Group oldValue, Group newValue) {
                showGroupDetails(newValue);
            }
        });

    }
    
    @Override
    public void update(Observable o, Object args) {
        ObservableList<Group> groupList = FXCollections.observableArrayList();
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        List<Group> groups = (List<Group>) args;        
        groupList.addAll(groups);
        groupsTable.setItems(groupList);
        for (Group group : groups) {
            studentList.addAll(group.getStudents());
        }
        studentsTable.setItems(studentList);
    }

    public void setController(ViewController ctrl) {
        this.ctrl = ctrl;        
    }

    private void showStudentDetails(Student student) {
        if (student != null) {
            lbName.setText(student.getName());
            lbMiddleName.setText(student.getMiddleName());
            lbLastName.setText(student.getLastName());
            lbGroupNumber.setText(Integer.toString(student.getGroup().getNumber()));
            lbFaculty.setText(student.getGroup().getFaculty());
            lbStartDate.setText(Util.formatDate(student.getStartStudyDate()));
        }
    }

    private void showGroupDetails(Group group) {
        if (group != null) {
            lbGroupNumber2.setText(String.valueOf(group.getNumber()));
            lbFaculty2.setText(group.getFaculty());
            lbCount.setText(String.valueOf(group.getStudents().size()));
        }
    }

    @FXML
    private void handleDeleteStudent() {
        int selectedIndex = studentsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Student remove = studentsTable.getItems().remove(selectedIndex);
            ctrl.deleteStudent(remove.getId());
        }
    }

    @FXML
    private void handleEditStudent() {
        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            boolean okClicked = ctrl.showStudentEditDialog(selectedStudent);
            if (okClicked) {
                showStudentDetails(selectedStudent);
            }
        }
    }

    @FXML
    private void handleAddStudent() throws IOException {
        ctrl.showStudentAddDialog();
    }

    @FXML
    private void handleDeleteGroup() {
        int selectedIndex = groupsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Group remove = groupsTable.getItems().remove(selectedIndex);
            ctrl.deleteGroup(remove.getId());
        }
    }

    @FXML
    private void handleAddGroup() throws IOException {
        ctrl.showGroupAddDialog();        
    }

    @FXML
    private void handleEditGroup() {
        Group selectedGroup = groupsTable.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            boolean okClicked = ctrl.showGroupEditDialog(selectedGroup);
            if (okClicked) {
                showGroupDetails(selectedGroup);
            }
        }       
    }
}