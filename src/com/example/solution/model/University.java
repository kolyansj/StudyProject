/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nikolay
 */
@XmlRootElement(name = "university")
public class University implements Serializable {

    @XmlElementRef
    private List<Group> groups;
    @XmlElementRef
    private List<Faculty> faculties;

    public University() {
        groups = new ArrayList<>();
        faculties = new ArrayList<>();
    }

    public University(University university) {
        this.groups = university.getGroups();
        this.faculties = university.getFaculties();
    }

    public boolean addGroup(Group group) {
        return groups.add(group);
    }

    public List<Group> getGroups() {
        return groups;
    }

    public Group getGroup(int index) {
        return groups.get(index);
    }

    public int size() {
        return groups.size();
    }

    public boolean removeGroup(Group group) {
        return groups.remove(group);
    }
    
    public int getCountGroups(){
        return faculties.size();
    }
    
    public List<Faculty> getFaculties(){
        return faculties;
    }
    
    public void addFaculty(Faculty faculty){
        faculties.add(faculty);
    }
    
    public boolean removeFaculty(Faculty faculty){
        return faculties.remove(faculty);
    }
    
    public Faculty getFaculty(int index){
        return faculties.get(index);
    }
    
}
