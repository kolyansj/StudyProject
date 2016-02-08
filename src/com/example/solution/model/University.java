/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.model;

import com.example.solution.visitor.Visitable;
import com.example.solution.visitor.Visitor;
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
public class University implements Visitable, Serializable {

    @XmlElementRef
    private List<Faculty> faculties;

    public University() {
        faculties = new ArrayList<>();
    }

    public University(University university) {
        this.faculties = university.getFaculties();
    }

    public boolean addFaculty(Faculty faculty) {
        return faculties.add(faculty);
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public Faculty getFaculty(int index) {
        return faculties.get(index);
    }

    public int size() {
        return faculties.size();
    }
    
    public boolean removeFaculty(Faculty faculty){
        return faculties.remove(faculty);
    }    

    @Override
    public void merge(Visitor visitor) {
        visitor.visit(this);
    }
    
}
