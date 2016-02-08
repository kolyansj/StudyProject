/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.visitor;

import com.example.solution.model.Faculty;
import com.example.solution.model.Group;
import com.example.solution.model.Student;
import com.example.solution.model.University;

/**
 *
 * @author Nikolay
 */
public interface Visitor {
    void visit(University university);
    void visit(Faculty faculty);
    void visit(Group group);    
    void visit(Student student);
}
