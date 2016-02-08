/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.model;

import com.example.solution.Util;
import com.example.solution.visitor.Visitable;
import com.example.solution.visitor.Visitor;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author Answer
 */
@XmlRootElement(name = "student")
public class Student implements Visitable, Serializable {
    
    private String id;
    private String name;
    private String middleName;
    private String lastName;
    private Group group;
    private Date startStudyDate;

    public Student() {
    }
    
    public Student(String id) {
        this.id = id;    
    }

    public Student(String id, String name, String middleName, String lastName, 
            Group group, Date startStudyDate) {
        this.id = id;
        this.name = name;
        this.middleName = middleName;
        this.lastName = lastName;
        this.group = group;
        this.startStudyDate = startStudyDate;
    }
    
    @XmlAttribute(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String Id) {
        this.id = Id;
    }
    
    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "middle_name")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @XmlAttribute(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlTransient
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @XmlAttribute(name = "date")
    public Date getStartStudyDate() {
        return startStudyDate;
    }

    public void setStartStudyDate(Date startStudyDate) {
        this.startStudyDate = startStudyDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + " " + middleName + " " + lastName;
    }
    
    

    @Override
    public void merge(Visitor visitor) {
        visitor.visit(this);
    }

}
