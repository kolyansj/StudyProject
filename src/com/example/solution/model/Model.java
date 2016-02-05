/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.model;

import com.example.solution.Util;
import com.example.solution.model.observer.Observable;
import com.example.solution.model.observer.Observer;
import com.example.solution.model.provider.DataProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nikolay
 */
public class Model implements Observable {
    
    private final List<Observer> observers;
    private University university;
    private DataProvider provider;
    private boolean changed;

    public Model() {
        university = new University();
        observers = new ArrayList<>();
    }
    
    @Override
    public void addObserver(Observer o) {
        if (o != null) {
            //LOG.log(Level.INFO, "Observer class: " + o.getClass());
            if (!observers.contains(o)) {
                observers.add(o);
            }
        } else {
            //LOG.log(Level.ERROR, "Observer is null");
            throw new NullPointerException("Observer is null");
        }
    }

    @Override
    public void deleteObserver(Observer o) {
        if (o != null) {
            //LOG.log(Level.INFO, "Observer class: " + o.getClass());
            observers.remove(o);
        } else {
            //LOG.log(Level.WARN, "Observer is null");
            throw new NullPointerException("Observer is null");
        }
    }

    @Override
    public void notifyObservers() {
        //LOG.log(Level.INFO, "notifyObservers");
        List<Observer> observersLocal = null;
        if (!changed) {
            return;
        }
        observersLocal = new ArrayList<>(this.observers);
        changed = false;
        for (Observer o : observersLocal) {
            o.update(this, new ArrayList<>(university.getGroups()));
        }
    }
    
    public DataProvider getDataProvider() {
        return provider;
    }

    public void setDataProvider(DataProvider provider) {
        this.provider = provider;
    }
    
    public List<Group> getGroups() {
        return university.getGroups();
    }
    
    public Student getStudentById(String studentId) {
        //LOG.log(Level.INFO, "id: " + id);
        if (studentId != null) {
            for (Group group: university.getGroups()) {
                for (Student student : group.getStudents()) {
                    if (student.getId().equals(studentId)) {
                        return student;
                    }
                }
            }
        } else {
            //LOG.log(Level.WARN, "id is null");
            //throw new NullPointerException("id is null");
        }
        return null;
    }
    
    public Group getGroupById(String groupId) {
        for (Group g : getGroups()) {
            if (g.getId().equals(groupId)) {
                return g;
            }            
        }
        return null;
    }
    
    public Group getGroupByStudentId(String studentId) {
        for (Group g : getGroups()) {
            for (Student s : g.getStudents()) {
                if (s.getId().equals(studentId)) {
                    return g;
                }  
            }
        }
        return null;
    }
    
    public boolean createGroup(int number, String faculty) {
        String groupId = String.valueOf(number + faculty);
        Group group = getGroupById(groupId);
        if (group == null) {
            group = new Group(groupId, number, faculty);
            university.addGroup(group);
            
            changed = true;
            notifyObservers();
            
            return true;
        } else {
            return false;
        }        
    }
    
    public void updateGroup(String groupId, int number, String faculty) {
        Group group = getGroupById(groupId);
        if (group != null) {            
            group.setId(String.valueOf(number + faculty));
            group.setNumber(number);
            group.setFaculty(faculty);
            
            changed = true;
            notifyObservers();
        }
    }
    
    public void deleteGroup(String groupId) {
        Group group = getGroupById(groupId);
        if (group != null) {
            group.getStudents().clear();
            university.removeGroup(group);
            
            changed = true;
            notifyObservers();
        }
    }
    
    public void createStudent(String name, String middleName, String lastName,
            Group group, Date date) {
        
        Student newStudent = new Student(Util.createUnicueID());
        newStudent.setName(name);
        newStudent.setMiddleName(middleName);
        newStudent.setLastName(lastName);                    
        newStudent.setStartStudyDate(date);
        newStudent.setGroup(group);
        group.getStudents().add(newStudent);
        
        changed = true;
        notifyObservers();
    }
    
    public void updateStudent(String studentId, String name, String middleName,
            String lastName, Group group, Date date) {
        Student student = getStudentById(studentId);
        if (student != null) {
            student.setName(name);
            student.setMiddleName(middleName);
            student.setLastName(lastName);
            student.setStartStudyDate(date);
            student.getGroup().getStudents().remove(student);
            group.getStudents().add(student);
            student.setGroup(group);
            
            changed = true;
            notifyObservers();
        }
    }
    
    public void deleteStudent(String studentId) {
        Student student = getStudentById(studentId);
        if (student != null) {
            student.getGroup().getStudents().remove(student);
            
            changed = true;
            notifyObservers();
        }
    }

    public void loadTasks() {
        //LOG.log(Level.INFO, "load from XML or DB");
        if(provider != null) {
            university = provider.loadData();
            changed = true;
            notifyObservers();
        } else {
            //LOG.log(Level.WARN, "Provider is null");
        }
    }

    public void saveTasks() {
        //LOG.log(Level.INFO, "save to XML or DB");
        if(provider != null) {
            provider.saveData(university);
        } else {
            //LOG.log(Level.WARN, "Provider is null");
        }
    }
    
}
