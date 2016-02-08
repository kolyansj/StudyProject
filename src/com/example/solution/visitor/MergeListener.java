/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.visitor;

import com.example.solution.model.Faculty;
import com.example.solution.model.Group;
import com.example.solution.model.Student;

/**
 *
 * @author Nikolay
 */
public interface MergeListener {
    void addFaculty(Faculty faculty);
    void addGroup(Group group);
    void addStudent(Student student);
    void equalsFaculty(Faculty faculty);
    void equalsGroup(Group group);
    void equalsStudent(Student student);
}
