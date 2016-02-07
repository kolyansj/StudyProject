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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nikolay
 */
public class Model implements Observable {
    
    private final List<Observer> observers;
    private University university;
    private DataProvider provider;
    private boolean changed;
    
    private Map<String, Object> cache;

    public Model() {
        this.cache = new HashMap<>();
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
            o.update(this, new ArrayList<>(university.getFaculties()));
        }
    }
    
    public DataProvider getDataProvider() {
        return provider;
    }

    public void setDataProvider(DataProvider provider) {
        this.provider = provider;
    }
    
    public List<Faculty> getFaculties() {
        return university.getFaculties();
    }
    
    public Student getStudentById(String studentId) {
        //LOG.log(Level.INFO, "id: " + id);
        if (studentId != null) {
            if (cache.containsKey(studentId)) {
                return (Student) cache.get(studentId);
            }
            for (Faculty faculty : university.getFaculties()) {
                for (Group group : faculty.getGroups()) {
                    for (Student student : group.getStudents()) {
                        if (student.getId().equals(studentId)) {
                            cache.put(studentId, student);
                            return student;
                        }
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
        if (groupId != null) {
            if (cache.containsKey(groupId)) {
                return (Group) cache.get(groupId);
            }
            for (Faculty faculty : university.getFaculties()) {
                for (Group group : faculty.getGroups()) {
                    if (group.getId().equals(groupId)) {
                        cache.put(groupId, group);
                        return group;
                    }            
                }
            }
        } else {
            
        }
        return null;
    }
    
    public Group getGroupByStudentId(String studentId) {
        if (studentId != null) {
            Student student = getStudentById(studentId);
            if (student != null) {
                Group group = student.getGroup();
                cache.put(group.getId(), group);
                return group;
            }
        } else {
            
        }
        return null;
    }
    
    public Faculty getFacultyById(String facultyId) {
        if (facultyId != null) {
            if (cache.containsKey(facultyId)) {
                return (Faculty) cache.get(facultyId);
            }
            for (Faculty faculty : university.getFaculties()) {
                if (faculty.getId().equals(facultyId)) {
                    cache.put(facultyId, faculty);
                    return faculty;
                }
            }
        } else {
            
        }
        return null;
    }
    
    public void createFaculty(String name, String abbreviation) {
        String id = Util.createUnicueID();
        Faculty faculty = new Faculty(id, name, abbreviation);
        university.addFaculty(faculty);
        
        cache.put(id, faculty);
        changed = true;
        notifyObservers(); 
    }
    
    public void updateFaculty(String facultyId, String name, String abbreviation) {
        Faculty faculty = getFacultyById(facultyId);
        if (faculty != null) {
            faculty.setName(name);
            faculty.setAbbreviation(abbreviation);
            
            changed = true;
            notifyObservers();
        }
    }
    
    public void deleteFaculty(String facultyId) {
        Faculty faculty = getFacultyById(facultyId);
        if (faculty != null) {                        
            university.removeFaculty(faculty);
            
            cache.remove(facultyId);
            changed = true;
            notifyObservers();
        }
    }
    
    public void createGroup(int number, Faculty faculty) {
        String id = Util.createUnicueID();
        Group group = new Group(id, number, faculty);
        faculty.getGroups().add(group);
        
        cache.put(id, group);
        changed = true;
        notifyObservers();      
    }
    
    public void updateGroup(String groupId, int number, Faculty faculty) {
        Group group = getGroupById(groupId);
        if (group != null) {      
            group.getFaculty().getGroups().remove(group);
            faculty.getGroups().add(group);
            
            group.setNumber(number);
            group.setFaculty(faculty);
            
            changed = true;
            notifyObservers();
        }
    }
    
    public void deleteGroup(String groupId) {
        Group group = getGroupById(groupId);
        if (group != null) {
            group.getFaculty().getGroups().remove(group);
            group.getStudents().clear();
            
            cache.remove(groupId);            
            changed = true;
            notifyObservers();
        }
    }
    
    public void createStudent(String name, String middleName, String lastName,
            Group group, Date date) {
        
        String id = Util.createUnicueID();
        Student newStudent = new Student(id);
        newStudent.setName(name);
        newStudent.setMiddleName(middleName);
        newStudent.setLastName(lastName);                    
        newStudent.setStartStudyDate(date);
        newStudent.setGroup(group);
        group.getStudents().add(newStudent);
        
        cache.put(id, newStudent);
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
            
            cache.remove(studentId); 
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
