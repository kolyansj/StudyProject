/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution;

import com.example.solution.FileSettings;
import com.example.solution.ViewMode;
import com.example.solution.model.Faculty;
import com.example.solution.model.Group;
import com.example.solution.model.Model;
import com.example.solution.model.Student;
import com.example.solution.model.University;
import com.example.solution.view.FacultyDialogPresenter;
import com.example.solution.view.GroupDialogPresenter;
import com.example.solution.view.OverviewPresenter;
import com.example.solution.view.StudentDialogPresenter;
import com.example.solution.visitor.MergeVisitor;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Answer
 */
public class ViewController {

    private Stage primaryStage;
    private final Model data;
    private final MergeVisitor visitor;

    public ViewController(Model data) {
        this.data = data;
        visitor = new MergeVisitor(data);
    }    
    
    public void init() {
        FileSettings settings = FileSettings.getInstance();
        settings.loadSettings("resources/config.properties");
        data.setDataProvider(settings.getProvider());
        data.loadData();
    }
    
    public void close() {
        data.saveData();
    }
    
    public List<Faculty> getData() {
        return data.getData().getFaculties();
    }
    
    public Group getGroupById(String groupId) {
        return data.getGroupById(groupId);
    }
    
    public void createFaculty(String name, String abbreviation) {
        data.createFaculty(name, abbreviation);
    }
    
    public void updateFaculty(String facultyId, String name, String abbreviation) {
        data.updateFaculty(facultyId, name, abbreviation);
    }
    
    public void deleteFaculty(String facultyId) {
        data.deleteFaculty(facultyId);
    }
    
    public void createGroup(int number, Faculty faculty) {
        data.createGroup(number, faculty);
    }
    
    public void updateGroup(String groupId, int number, Faculty faculty) {
        data.updateGroup(groupId, number, faculty);
    }
    
    public void deleteGroup(String groupId) {
        data.deleteGroup(groupId);
    }
    
    public void createStudent(String name, String middleName, String lastName,
            Group group, Date date) {
        data.createStudent(name, middleName, lastName, group, date);
    }
    
    public void updateStudent(String studentId, String name, String middleName,
            String lastName, Group group, Date date) {
        data.updateStudent(studentId, name, middleName, lastName, group, date);
    }
    
    public void deleteStudent(String studentId) {
        data.deleteStudent(studentId);
    }
    
    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }
    
    public void mergeFile(File file) {
        University univ = data.loadData(file);
        univ.merge(visitor);        
    }
    
    public void showRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewController.class.getResource("view/OverviewLayout.fxml"));
            TabPane rootLayout = (TabPane) loader.load();
            Scene scene = new Scene(rootLayout);

            OverviewPresenter controller = loader.getController();
            controller.setController(this);
            data.addObserver(controller);
            visitor.setListener(controller);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean showStudentEditDialog(Student student) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewController.class.getResource("view/StudentDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование информации о студенте");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            StudentDialogPresenter controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setController(this);
            controller.setMode(ViewMode.Edit);
            controller.setStudent(student);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            
            return controller.isOkClicked();            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean showStudentAddDialog() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewController.class.getResource("view/StudentDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить нового студента");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            StudentDialogPresenter controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setController(this);
            controller.setMode(ViewMode.Create);            
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean showGroupAddDialog() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewController.class.getResource("view/GroupDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить новую группу");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            GroupDialogPresenter controller = loader.getController();
            controller.setDialogStage(dialogStage);            
            controller.setController(this);
            controller.setMode(ViewMode.Create);
            
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean showGroupEditDialog(Group group) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewController.class.getResource("view/GroupDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование информации о группе");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            GroupDialogPresenter controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setController(this);
            controller.setMode(ViewMode.Edit);
            controller.setGroup(group);

            dialogStage.showAndWait();
            
            return controller.isOkClicked();            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean showFacultyEditDialog(Faculty faculty) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewController.class.getResource("view/FacultyDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование информации о факультете");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            FacultyDialogPresenter controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setController(this);
            controller.setMode(ViewMode.Edit);
            controller.setFaculty(faculty);

            dialogStage.showAndWait();
            
            return controller.isOkClicked();            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean showFacultyAddDialog() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewController.class.getResource("view/FacultyDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить новый факультет");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            FacultyDialogPresenter controller = loader.getController();
            controller.setDialogStage(dialogStage);            
            controller.setController(this);
            controller.setMode(ViewMode.Create);
            
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
