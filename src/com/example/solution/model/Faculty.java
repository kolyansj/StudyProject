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
import java.util.Objects;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nikolay
 */
@XmlRootElement(name = "faculty")
public class Faculty implements Visitable, Serializable {
    
    private String id;
    private String name;
    private String cutName;
    private List<Group> groups;
    
    public Faculty() {
        groups = new ArrayList<>();
    }

    public Faculty(String id, String name, String abbreviation) {
        this();
        this.id = id;
        this.name = name;
        this.cutName = abbreviation;
    }       

    @XmlAttribute(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @XmlAttribute(name = "abbreviation")
    public String getCutName() {
        return cutName;
    }

    public void setCutName(String cutName) {
        this.cutName = cutName;
    }

    @XmlElementWrapper(name="groups")
    @XmlElement(name = "group")
    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final Faculty other = (Faculty) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return cutName;
    }

    @Override
    public void merge(Visitor visitor) {
        visitor.visit(this);
    }

}
