/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.visitor;

import com.example.solution.model.Faculty;
import com.example.solution.model.Group;
import com.example.solution.model.Model;
import com.example.solution.model.Student;
import com.example.solution.model.University;
import java.util.List;

/**
 *
 * @author Nikolay
 */
public class MergeVisitor implements Visitor {

    private MergeListener listener;
    
    private final Model data;
    private Faculty currentFaculty;
    private Group currentGroup;
    
    public MergeVisitor(Model data) {
        this.data = data;
    }

    public Faculty getCurrentFaculty() {
        return currentFaculty;
    }

    public void setCurrentFaculty(Faculty currentFaculty) {
        this.currentFaculty = currentFaculty;
    }
    
    public Group getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(Group currentGroup) {
        this.currentGroup = currentGroup;
    }

    public MergeListener getListener() {
        return listener;
    }

    public void setListener(MergeListener listener) {
        this.listener = listener;
    }    
    
    @Override
    public void visit(University university) {
        for (Faculty faculty : university.getFaculties()) {   
            faculty.merge(this);
        }
    }

    @Override
    public void visit(Faculty faculty) {
        Faculty curFaculty = data.getFacultyById(faculty.getId());
        if (curFaculty != null) {
            if (listener != null) {
                listener.equalsFaculty(curFaculty);
            }
            setCurrentFaculty(curFaculty);
            for (Group group : faculty.getGroups()) {
                group.merge(this);
            }
        } else {
            data.createFaculty(faculty);
            if (listener != null) {
                listener.addFaculty(faculty);
            }
        }        
    }

    @Override
    public void visit(Group group) {
        if (currentFaculty == null) {
            throw new NullPointerException("Current faculty not set");
        } else {
            Group curGroup = data.getGroupById(group.getId());
            if (curGroup != null) {
                if (listener != null) {
                    listener.equalsGroup(curGroup);
                }
                setCurrentGroup(curGroup);
                for (Student student : group.getStudents()) {
                    student.merge(this);
                }
            } else {
                data.createGroup(group, currentFaculty);
                if (listener != null) {
                    listener.addGroup(group);
                }
            }
        }
    }

    @Override
    public void visit(Student student) {
        if (currentGroup == null) {
            throw new NullPointerException("Current group not set");
        } else {
            List<Student> students = currentGroup.getStudents();
            if (!students.contains(student)) {
                data.createStudent(student.getName(),
                        student.getMiddleName(),
                        student.getLastName(),
                        currentGroup,
                        student.getStartStudyDate());
                if (listener != null) {
                    listener.addStudent(student);
                }
            } else {
                if (listener != null) {
                    listener.equalsStudent(student);
                }
            }
        }
    }
    
}
